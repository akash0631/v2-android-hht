package com.v2retail.dotvik.dc.cluster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Fixtures are real S4D 210 responses through hht-api /dev (5-Oct): run ZPT_P5_E2E3, cluster 1.
 * Slot 1 = HB28 / 8000004041: A0-0203-A4 x1, A0-0206-A1 x2, A0-0206-B1 x2, A0-0206-B4 x2.
 * Slot 2 = HB12 / 8000004040: A0-0206-A1 x1, A0-0206-B1 x1 (the bins it shares with slot 1).
 * EANs: 463002=5040100, 186001=5078097, 199002=5078820, 206002=5078899.
 */
public class ClusterPickTest {

    private ClusterPick cp;

    private static String res(String name) {
        InputStream in = ClusterPickTest.class.getResourceAsStream("/cluster/" + name);
        assertNotNull(name, in);
        return new Scanner(in, "UTF-8").useDelimiter("\\A").next();
    }

    @Before
    public void load() throws Exception {
        cp = new ClusterPick(res("get_detail.json"), "PACK01");
        assertTrue(cp.setPlp2(1, new JSONObject(res("plp2_8000004041.json"))).isEmpty());
        assertTrue(cp.setPlp2(2, new JSONObject(res("plp2_8000004040.json"))).isEmpty());
        cp.setHu(1, "HU0001");
        cp.setHu(2, "HU0002");
        cp.start();
    }

    private void ok(String code) {
        ClusterPick.Result r = cp.scan(code);
        assertTrue(code + ": " + r.msg, r.ok);
    }

    private int put(String hu) {
        ClusterPick.Result r = cp.scan(hu);
        assertTrue(hu + ": " + r.msg, r.ok);
        return r.slotDone;
    }

    @Test
    public void walkFillsBothHusInOneWalkAndSavesEachSlotWhenItsLastLineIsPut() throws Exception {
        assertEquals("Go to bin A0-0203-A4", cp.prompt());
        ok("A0-0203-A4"); ok("5040100");
        assertEquals(0, put("HU0001"));

        ok("a0-0206-a1 ");                                  // bin scan is case/space tolerant
        ok("5078097"); ok("5078097");
        assertEquals(0, put("HU0001"));
        assertEquals(ClusterPick.Expect.EAN, cp.expect()); // same bin, next slot: no bin rescan
        ok("5078097");
        assertEquals(0, put("HU0002"));

        ok("A0-0206-B1");
        ok("5078820"); ok("5078820");
        assertEquals(0, put("HU0001"));
        ok("5078820");
        assertEquals(2, put("HU0002"));                     // slot 2 complete: save now, mid-walk

        ok("A0-0206-B4"); ok("5078899"); ok("5078899");
        assertEquals(1, put("HU0001"));
        assertEquals(ClusterPick.Expect.DONE, cp.expect());
        assertEquals(0, cp.openLines());

        JSONObject p1 = cp.savePayload(1, "PICKER1");
        assertEquals("8000004041", p1.getString("IM_VBELN"));
        assertEquals("HU0001", p1.getString("IM_EXIDV"));
        assertEquals("PICKER1", p1.getString("IM_USER"));
        JSONArray it = p1.getJSONArray("IT_DATA");
        assertEquals(7, it.length());                       // one row per EAN scan, 7 pcs
        JSONObject r0 = it.getJSONObject(0);
        assertEquals("000001110002463002", r0.getString("MATNR"));
        assertEquals("DH24", r0.getString("WERKS"));
        assertEquals("0001", r0.getString("LGORT"));
        assertEquals("EA", r0.getString("VRKME"));
        assertEquals("PACK01", r0.getString("P_MATERIAL"));
        assertEquals("1", r0.getString("TMENG"));
        assertEquals("A0-0203-A4", r0.getString("RFBEL"));
        assertEquals(0, p1.getJSONArray("IT_BIN_EMPTY").length());
        assertEquals(2, cp.savePayload(2, "PICKER1").getJSONArray("IT_DATA").length());
    }

    @Test
    public void wrongBinArticleQuantityAndHuAreRefused() {
        assertFalse(cp.scan("A0-0206-A1").ok);              // first stop is A0-0203-A4
        ok("A0-0203-A4");
        ClusterPick.Result r = cp.scan("5078097");          // on the delivery, not this line
        assertFalse(r.ok);
        assertTrue(r.msg, r.msg.startsWith("Wrong article"));
        assertFalse(cp.scan("9999999").ok);                 // not on the delivery
        ok("5040100");
        assertFalse(cp.scan("5040100").ok);                 // line needs 1, already 1
        r = cp.scan("HU0002");                              // slot 2's HU for a slot 1 put
        assertFalse(r.ok);
        assertTrue(r.msg, r.msg.startsWith("That is SLOT 2"));
        assertFalse(cp.scan("HUXXXX").ok);
        assertEquals(0, put("hu0001"));
    }

    @Test
    public void shortLineMarksTheBinEmptyForThatSlotOnly() throws Exception {
        ok("A0-0203-A4");
        assertTrue(cp.shortLine().ok);                      // nothing picked: line closed, no HU scan
        assertEquals(ClusterPick.Expect.BIN, cp.expect());
        ok("A0-0206-A1"); ok("5078097");
        assertTrue(cp.shortLine().ok);                      // 1 of 2 picked: confirm the put first
        assertEquals(ClusterPick.Expect.HU, cp.expect());
        put("HU0001");
        JSONArray empty = cp.savePayload(1, "U").getJSONArray("IT_BIN_EMPTY");
        assertEquals(2, empty.length());
        assertEquals("X", empty.getJSONObject(0).getString("BIN_EMPTY_I"));
        assertEquals(0, cp.savePayload(2, "U").getJSONArray("IT_BIN_EMPTY").length());
    }

    @Test
    public void lineWithNothingOpenInTheToIsSkippedWithAWarning() throws Exception {
        ClusterPick c = new ClusterPick(res("get_detail.json"), "PACK01");
        JSONObject plp2 = new JSONObject(res("plp2_8000004040.json"));
        plp2.getJSONArray("ET_BIN_MC").getJSONObject(1).put("REMAIN_QTY", "0.000");   // A0-0206-A1 picked
        List<String> warn = c.setPlp2(2, plp2);
        assertEquals(1, warn.size());
        assertTrue(c.needsHu(2));
        plp2.getJSONArray("ET_BIN_MC").getJSONObject(2).put("REMAIN_QTY", "0.000");
        c.setPlp2(2, plp2);
        assertFalse(c.needsHu(2));                          // nothing left: no HU for slot 2
    }

    @Test
    public void huChecks() {
        assertNull(cp.checkHu(1, "HU0001"));
        assertEquals("HU already on slot 2", cp.checkHu(1, "HU0002"));
        assertEquals("Scan the HU", cp.checkHu(1, " "));
    }

    @Test
    public void listLabel() throws Exception {
        JSONObject c = new JSONObject(res("get_detail.json")).getJSONArray("CLUSTERS").getJSONObject(0);
        assertEquals("C1 | 2 HU | HB28,HB12 | 4 bins | 9 pcs", ClusterPick.label(c));
        c.put("READY", false);
        assertTrue(ClusterPick.label(c).endsWith("WAIT: TO not created"));
    }
}
