package com.v2retail.dotvik.dc.cluster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assume.assumeTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Opt-in end-to-end run of one cluster against a real SAP system through the HHT cloud proxy,
 * making the same calls in the same order as ClusterPickingFragment. It WRITES (validates and binds
 * the external HUs, creates and packs the SAP HUs), so it is skipped unless CLUSTER_E2E_URL is set,
 * and it is meant for S4D only:
 *   CLUSTER_E2E_URL=https://hht-api.v2retail.net/dev CLUSTER_E2E_WERKS=DH24 CLUSTER_E2E_CLUSTER=1 \
 *   CLUSTER_E2E_HUS=2000130729,2000130730 sh ./gradlew testDebugUnitTest --tests '*ClusterPickLiveTest*'
 * HUs are given in slot order and must be free ZWM_EXREF rows of the plant.
 */
public class ClusterPickLiveTest {

    private static String base;

    private static JSONObject rfc(String fm, JSONObject p) throws Exception {
        p.put("bapiname", fm);
        HttpURLConnection c = (HttpURLConnection) new URL(base + "/noacljsonrfcadaptor?bapiname=" + fm + "&aclclientid=android").openConnection();
        c.setRequestMethod("POST");
        c.setDoOutput(true);
        c.setConnectTimeout(30000);
        c.setReadTimeout(120000);
        c.setRequestProperty("Content-Type", "application/json");
        try (OutputStream o = c.getOutputStream()) {
            o.write(p.toString().getBytes(StandardCharsets.UTF_8));
        }
        try (InputStream in = c.getInputStream()) {
            JSONObject r = new JSONObject(new Scanner(in, "UTF-8").useDelimiter("\\A").next());
            System.out.println(fm + " -> " + (r.has("EX_RETURN") ? r.getJSONObject("EX_RETURN").optString("TYPE") + " "
                    + r.getJSONObject("EX_RETURN").optString("MESSAGE") : "RC " + r.optString("EX_RC") + " " + r.optString("EX_MSG")));
            // proxy echo: what it fed SAP (diagnoses a table or FM silently dropped/rerouted)
            System.out.println("  proxy " + r.optString("_RFC_NAME") + " " + r.optString("_RFC_REQUESTED")
                    + " " + r.opt("_PARAMS_APPLIED") + " " + r.opt("_PARAM_ERRORS"));
            return r;
        }
    }

    private static String eanFor(ClusterPick.Slot s, String matnr) {
        for (int i = 1; i < s.ean.length(); i++) {
            JSONObject e = s.ean.optJSONObject(i);
            if (ClusterPick.mat(e.optString("MATNR")).equals(ClusterPick.mat(matnr))) return e.optString("EAN11");
        }
        throw new AssertionError("no EAN for " + matnr);
    }

    private static void scan(ClusterPick cp, String code) {
        ClusterPick.Result r = cp.scan(code);
        if (!r.ok) throw new AssertionError(code + ": " + r.msg);
        if (r.slotDone > 0) save(cp, r.slotDone);
    }

    private static void save(ClusterPick cp, int k) {
        try {
            JSONObject r = rfc("ZWM_CREATE_HU_AND_ASSIGN_TVS", cp.savePayload(k, "CLUSTER_E2E"));
            assertNotEquals("slot " + k + " save: " + r.getJSONObject("EX_RETURN").optString("MESSAGE"),
                    "E", r.getJSONObject("EX_RETURN").optString("TYPE"));
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    @Test
    public void oneClusterEndToEnd() throws Exception {
        base = System.getenv("CLUSTER_E2E_URL");
        assumeTrue("set CLUSTER_E2E_URL to run", base != null && !base.isEmpty());
        String werks = System.getenv("CLUSTER_E2E_WERKS");
        String[] hus = System.getenv("CLUSTER_E2E_HUS").split(",");

        JSONObject list = rfc("Z_PICK_CLUSTER_GET", new JSONObject().put("IM_WERKS", werks));
        assertEquals(0, list.optInt("EX_RC", -1));
        String runId = new JSONObject(list.getString("EX_JSON")).getString("RUN_ID");
        JSONObject det = rfc("Z_PICK_CLUSTER_GET", new JSONObject().put("IM_RUN_ID", runId)
                .put("IM_CLUSTER", System.getenv("CLUSTER_E2E_CLUSTER")));
        assertEquals(0, det.optInt("EX_RC", -1));
        JSONArray pm = rfc("ZWM_GET_PACKING_MATERIAL", new JSONObject()).getJSONArray("ET_PACK_MAT");
        ClusterPick cp = new ClusterPick(det.getString("EX_JSON"), pm.getJSONObject(1).getString("MATNR"));

        int h = 0;
        for (ClusterPick.Slot s : cp.slots.values()) {
            JSONObject plp2 = rfc("ZWM_DELIVERY_GET_DETAILS_PLP2", new JSONObject().put("IM_VBELN", s.vbeln)
                    .put("IM_READ_DELV_ALL", "X").put("IM_READ_EAN", "X"));
            System.out.println("slot " + s.slot + " warnings " + cp.setPlp2(s.slot, plp2));
            if (!cp.needsHu(s.slot)) continue;
            String hu = hus[h++].trim();
            assertEquals(null, cp.checkHu(s.slot, hu));
            JSONObject v = rfc("ZWM_TVS_VAL_EXTERNAL_HU", new JSONObject().put("IM_WERKS", werks).put("IM_EXIDV", hu)
                    .put("IM_DWERKS", s.likp.optString("KUNNR")).put("IM_VBELN", s.likp.optString("VBELN")));
            assertNotEquals(v.getJSONObject("EX_RETURN").optString("MESSAGE"), "E", v.getJSONObject("EX_RETURN").optString("TYPE"));
            cp.setHu(s.slot, hu);
        }

        cp.start();
        while (cp.expect() != ClusterPick.Expect.DONE) {
            ClusterPick.Task t = cp.current();
            ClusterPick.Slot s = cp.slots.get(t.slot);
            System.out.println(cp.prompt());
            if (cp.expect() == ClusterPick.Expect.BIN) scan(cp, t.bin);
            else if (cp.expect() == ClusterPick.Expect.EAN) scan(cp, eanFor(s, t.matnr));
            else scan(cp, s.exidv);
        }
    }
}
