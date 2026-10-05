package com.v2retail.dotvik.dc.cluster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Cluster picking: one picker, a cart of up to 4 HUs (one per store delivery), one walk.
 * Plain Java (no Android) so the walk logic is unit tested.
 *
 * Input: Z_PICK_CLUSTER_GET EX_JSON for one cluster (its LINES are in walk order, CSEQ), then
 * ZWM_DELIVERY_GET_DETAILS_PLP2 for each slot's delivery. One scan field drives the walk:
 * the bin, then EANs up to the line quantity, then the slot's HU to confirm the put (a wrong-store
 * put is the main risk of picking for 4 stores at once). Each slot's save is the paperless
 * ZWM_CREATE_HU_AND_ASSIGN_TVS payload: one IT_DATA row per EAN scan, IT_BIN_EMPTY for shorted bins.
 *
 * RFC tables arrive with a field-label row at index 0 (same as the paperless screens), so every
 * table loop starts at 1.
 */
public class ClusterPick {

    public enum Expect { BIN, EAN, HU, DONE }

    public static class Slot {
        public int slot;
        public String store = "", vbeln = "", exidv = "";
        public JSONObject likp;
        JSONArray binMc = new JSONArray(), ean = new JSONArray(), lips = new JSONArray();
        public final JSONArray itData = new JSONArray();
        final List<String> emptyBins = new ArrayList<>();
        public String saveState = "";   // "", SAVING, SAVED, ERROR, NOTHING
        public String saveMsg = "";
    }

    public static class Task {
        public int cseq, slot, stop, qty, picked;
        public String bin = "", matnr = "", lineNo = "";
        public boolean done, shorted;
    }

    public static class Result {
        public final boolean ok;
        public final String msg;
        public final int slotDone;      // > 0: this slot has no open task left, save it now

        Result(boolean ok, String msg, int slotDone) {
            this.ok = ok;
            this.msg = msg;
            this.slotDone = slotDone;
        }

        static Result ok(int slotDone) { return new Result(true, "", slotDone); }

        static Result err(String msg) { return new Result(false, msg, 0); }
    }

    public final String runId;
    public final int clusterNo;
    public final Map<Integer, Slot> slots = new LinkedHashMap<>();
    public final List<Task> tasks = new ArrayList<>();
    private final String packMat;
    private int cur = -1;
    private Expect expect = Expect.BIN;
    private String atBin = "";

    /** detailJson = Z_PICK_CLUSTER_GET EX_JSON called with IM_CLUSTER (one cluster plus its LINES). */
    public ClusterPick(String detailJson, String packMat) throws JSONException {
        this.packMat = packMat;
        JSONObject d = new JSONObject(detailJson);
        runId = d.optString("RUN_ID");
        JSONObject c = d.getJSONArray("CLUSTERS").getJSONObject(0);
        clusterNo = c.optInt("CLUSTER_NO");
        JSONArray ss = c.getJSONArray("SLOTS");
        for (int i = 0; i < ss.length(); i++) {
            JSONObject o = ss.getJSONObject(i);
            Slot s = new Slot();
            s.slot = o.optInt("SLOT");
            s.store = o.optString("STORE");
            s.vbeln = o.optString("VBELN");
            slots.put(s.slot, s);
        }
        JSONArray ls = d.getJSONArray("LINES");
        for (int i = 0; i < ls.length(); i++) {
            JSONObject o = ls.getJSONObject(i);
            Task t = new Task();
            t.cseq = o.optInt("CSEQ");
            t.slot = o.optInt("SLOT");
            t.stop = o.optInt("STOP");
            t.bin = o.optString("LGPLA");
            t.matnr = o.optString("MATNR");
            t.lineNo = o.optString("LINE_NO");
            t.qty = (int) Math.round(o.optDouble("QTY", 0));
            tasks.add(t);
        }
        Collections.sort(tasks, (a, b) -> Integer.compare(a.cseq, b.cseq));
    }

    /** One cluster of the list-mode EX_JSON as a spinner label. */
    public static String label(JSONObject c) {
        StringBuilder stores = new StringBuilder();
        JSONArray ss = c.optJSONArray("SLOTS");
        for (int i = 0; ss != null && i < ss.length(); i++) {
            stores.append(i == 0 ? "" : ",").append(ss.optJSONObject(i).optString("STORE"));
        }
        return "C" + c.optInt("CLUSTER_NO") + " | " + c.optInt("HUS") + " HU | " + stores + " | "
                + c.optInt("STOPS") + " bins | " + (int) Math.round(c.optDouble("PCS", 0)) + " pcs"
                + (c.optBoolean("READY") ? "" : " | WAIT: TO not created");
    }

    /**
     * Slot's PLP2 response. A line's quantity is capped by the TO's open REMAIN_QTY for its bin and
     * article; a line with nothing open (already picked, or the TO took another bin) is skipped.
     * Returns one warning per skipped or reduced line.
     */
    public List<String> setPlp2(int slotNo, JSONObject r) {
        List<String> warn = new ArrayList<>();
        Slot s = slots.get(slotNo);
        s.likp = r.optJSONObject("EX_LIKP");
        s.binMc = arr(r, "ET_BIN_MC");
        s.ean = arr(r, "ET_EAN_DATA");
        s.lips = arr(r, "ET_LIPS");
        for (Task t : tasks) {
            if (t.slot != slotNo) continue;
            int open = 0;
            for (int i = 1; i < s.binMc.length(); i++) {
                JSONObject b = s.binMc.optJSONObject(i);
                if (b != null && bin(b.optString("VLPLA")).equals(bin(t.bin))
                        && mat(b.optString("MATNR")).equals(mat(t.matnr))) {
                    open += (int) Math.round(num(b.optString("REMAIN_QTY")));
                }
            }
            if (open < t.qty) {
                warn.add("Slot " + slotNo + " " + t.bin + " " + mat(t.matnr) + ": plan " + t.qty + ", open " + open);
                t.qty = open;
            }
            if (t.qty <= 0) t.done = true;
        }
        return warn;
    }

    /** A slot needs an HU only if it still has something to pick. */
    public boolean needsHu(int slotNo) {
        for (Task t : tasks) if (t.slot == slotNo && !t.done) return true;
        return false;
    }

    /** Next slot that needs an HU scanned, or 0. */
    public int nextSlotForHu() {
        for (Slot s : slots.values()) if (s.exidv.isEmpty() && needsHu(s.slot)) return s.slot;
        return 0;
    }

    /** Local check before ZWM_TVS_VAL_EXTERNAL_HU: one HU per slot, never the same HU twice. */
    public String checkHu(int slotNo, String exidv) {
        if (exidv == null || exidv.trim().isEmpty()) return "Scan the HU";
        for (Slot s : slots.values()) {
            if (s.slot != slotNo && hu(s.exidv).equals(hu(exidv))) return "HU already on slot " + s.slot;
        }
        return null;
    }

    public void setHu(int slotNo, String exidv) { slots.get(slotNo).exidv = exidv.trim(); }

    /** Walk starts at the first open line. */
    public void start() {
        cur = -1;
        atBin = "";
        advance();
    }

    public Expect expect() { return expect; }

    public Task current() { return cur >= 0 && cur < tasks.size() ? tasks.get(cur) : null; }

    public String prompt() {
        Task t = current();
        if (t == null) return "Walk complete";
        Slot s = slots.get(t.slot);
        switch (expect) {
            case BIN: return "Go to bin " + t.bin;
            case EAN: return "Pick " + mat(t.matnr) + "  " + t.picked + "/" + t.qty + "  for SLOT " + t.slot;
            case HU: return "Put " + t.picked + " pcs in SLOT " + t.slot + " (" + s.store + ") - scan its HU";
            default: return "Walk complete";
        }
    }

    public Result scan(String raw) {
        String code = raw == null ? "" : raw.trim();
        Task t = current();
        if (t == null) return Result.err("Walk complete");
        if (code.isEmpty()) return Result.err("Nothing scanned");
        Slot s = slots.get(t.slot);
        switch (expect) {
            case BIN:
                if (!bin(code).equals(bin(t.bin))) return Result.err("Wrong bin. Go to " + t.bin);
                atBin = t.bin;
                expect = Expect.EAN;
                return Result.ok(0);
            case EAN: {
                JSONObject e = row(s.ean, "EAN11", code, false);
                if (e == null) return Result.err("Barcode not on delivery " + s.vbeln);
                if (!mat(e.optString("MATNR")).equals(mat(t.matnr))) {
                    return Result.err("Wrong article. Pick " + mat(t.matnr) + " here");
                }
                int n = Math.max(1, (int) Math.round(num(e.optString("UMREZ"))));
                if (t.picked + n > t.qty) return Result.err("More than needed: " + t.picked + "/" + t.qty);
                JSONObject l = row(s.lips, "MATNR", t.matnr, true);
                if (l == null) return Result.err("Article not on delivery " + s.vbeln);
                try {
                    JSONObject it = new JSONObject();
                    it.put("MATNR", e.optString("MATNR"));
                    it.put("CHARG", l.optString("CHARG"));
                    it.put("WERKS", l.optString("WERKS"));
                    it.put("LGORT", l.optString("LGORT"));
                    it.put("P_MATERIAL", packMat);
                    it.put("TMENG", e.optString("UMREZ"));
                    it.put("VRKME", l.optString("VRKME"));
                    it.put("RFBEL", t.bin);
                    s.itData.put(it);
                } catch (JSONException je) {
                    return Result.err(je.getMessage());
                }
                t.picked += n;
                if (t.picked == t.qty) expect = Expect.HU;
                return Result.ok(0);
            }
            case HU:
                if (!hu(code).equals(hu(s.exidv))) {
                    for (Slot o : slots.values()) {
                        if (hu(o.exidv).equals(hu(code))) {
                            return Result.err("That is SLOT " + o.slot + " (" + o.store + "). Put in SLOT " + t.slot + " (" + s.store + ")");
                        }
                    }
                    return Result.err("Not a cart HU. Scan SLOT " + t.slot + " HU");
                }
                t.done = true;
                return advance();
            default:
                return Result.err("Walk complete");
        }
    }

    /**
     * Bin short for the current line: the bin goes into IT_BIN_EMPTY of this slot's save.
     * Pieces already picked still need the HU scan to confirm the put.
     */
    public Result shortLine() {
        Task t = current();
        if (t == null) return Result.err("Walk complete");
        Slot s = slots.get(t.slot);
        t.shorted = true;
        if (!s.emptyBins.contains(t.bin)) s.emptyBins.add(t.bin);
        if (t.picked > 0) {
            expect = Expect.HU;
            return Result.ok(0);
        }
        t.done = true;
        return advance();
    }

    private Result advance() {
        Task prev = current();
        while (++cur < tasks.size() && tasks.get(cur).done) { }
        Task t = current();
        if (t == null) {
            expect = Expect.DONE;
        } else {
            expect = bin(t.bin).equals(bin(atBin)) ? Expect.EAN : Expect.BIN;
        }
        if (prev != null && prev.done && slotComplete(prev.slot)) return Result.ok(prev.slot);
        return Result.ok(0);
    }

    public boolean slotComplete(int slotNo) {
        for (Task t : tasks) if (t.slot == slotNo && !t.done) return false;
        return true;
    }

    public int openLines() {
        int n = 0;
        for (Task t : tasks) if (!t.done) n++;
        return n;
    }

    /** ZWM_CREATE_HU_AND_ASSIGN_TVS parameters for one slot, the same shape the paperless screen sends. */
    public JSONObject savePayload(int slotNo, String user) throws JSONException {
        Slot s = slots.get(slotNo);
        JSONObject p = new JSONObject();
        p.put("IM_VBELN", s.vbeln);
        p.put("IM_USER", user);
        p.put("IM_EXIDV", s.exidv);
        p.put("IT_DATA", s.itData);
        JSONArray empty = new JSONArray();
        for (int i = 1; i < s.binMc.length(); i++) {
            JSONObject b = s.binMc.optJSONObject(i);
            if (b != null && s.emptyBins.contains(b.optString("VLPLA"))) {
                JSONObject e = new JSONObject(b.toString());
                e.put("BIN_EMPTY_I", "X");
                empty.put(e);
            }
        }
        p.put("IT_BIN_EMPTY", empty);
        return p;
    }

    private static JSONArray arr(JSONObject r, String k) {
        JSONArray a = r.optJSONArray(k);
        return a == null ? new JSONArray() : a;
    }

    private static JSONObject row(JSONArray a, String key, String val, boolean matnr) {
        String want = matnr ? mat(val) : val.trim();
        for (int i = 1; i < a.length(); i++) {
            JSONObject o = a.optJSONObject(i);
            if (o == null) continue;
            String v = o.optString(key);
            if ((matnr ? mat(v) : v.trim()).equals(want)) return o;
        }
        return null;
    }

    static String mat(String m) { return m == null ? "" : m.trim().replaceFirst("^0+(?!$)", ""); }

    static String bin(String b) { return b == null ? "" : b.trim().toUpperCase(); }

    static String hu(String h) { return mat(h).toUpperCase(); }

    static double num(String s) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
