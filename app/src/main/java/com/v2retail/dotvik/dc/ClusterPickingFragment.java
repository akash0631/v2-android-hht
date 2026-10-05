package com.v2retail.dotvik.dc;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.v2retail.ApplicationController;
import com.v2retail.dotvik.R;
import com.v2retail.dotvik.dc.cluster.ClusterPick;
import com.v2retail.util.AlertBox;
import com.v2retail.util.AppConstants;
import com.v2retail.util.SharedPreferencesData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * DC Outward > Cluster Picking: one picker fills up to 4 store HUs in one walk.
 *
 * SAP: Z_PICK_CLUSTER_GET gives the cart and the walk (off unless TVARVC ZWM_PICK_CLUSTER_ON = X).
 * Each slot then uses the TVS paperless calls unchanged: ZWM_DELIVERY_GET_DETAILS_PLP2,
 * ZWM_TVS_VAL_EXTERNAL_HU and ZWM_CREATE_HU_AND_ASSIGN_TVS. This is the live DC pair; the non-TVS
 * ZWM_CREATE_HU_AND_ASSIGN packs only one row per HU and fails its qty check.
 * A slot is saved as soon as its last line is put. The save waits about 10 s inside SAP,
 * so it runs in the background while the picker walks on.
 * The walk itself (scan rules, IT_DATA) is ClusterPick.
 */
public class ClusterPickingFragment extends Fragment {

    private enum Phase { LIST, LOADING, HU, WALK, FINISH }

    private interface Ok { void on(JSONObject r) throws JSONException; }

    private interface Fail { void on(String msg); }

    private String requestUrl = "", user = "", werks = "", runId = "";
    private final List<JSONObject> clusters = new ArrayList<>();
    private final List<String> clusterLabels = new ArrayList<>();
    private final List<String> packs = new ArrayList<>();
    private ArrayAdapter<String> clusterAdapter, packAdapter;
    private Spinner spCluster, spPack;
    private View setup;
    private Button btnShort, btnRetry;
    private TextView tvPrompt, tvInfo, tvSlots;
    private EditText etScan;
    private AlertBox box;
    private ProgressDialog dialog;
    private ClusterPick cp;
    private Phase phase = Phase.LIST;
    private int huSlot;
    private boolean busy;

    public static ClusterPickingFragment newInstance() {
        return new ClusterPickingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cluster_picking, container, false);
        SharedPreferencesData data = new SharedPreferencesData(getContext());
        requestUrl = data.read("URL");
        user = data.read("USER");
        werks = data.read("WERKS");
        box = new AlertBox(getContext());

        setup = v.findViewById(R.id.cluster_setup);
        spCluster = v.findViewById(R.id.cluster_select);
        spPack = v.findViewById(R.id.cluster_pack);
        tvPrompt = v.findViewById(R.id.cluster_prompt);
        tvInfo = v.findViewById(R.id.cluster_info);
        tvSlots = v.findViewById(R.id.cluster_slots);
        etScan = v.findViewById(R.id.cluster_scan);
        btnShort = v.findViewById(R.id.cluster_short);
        btnRetry = v.findViewById(R.id.cluster_retry);

        clusterAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, clusterLabels);
        clusterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCluster.setAdapter(clusterAdapter);
        packAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, packs);
        packAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPack.setAdapter(packAdapter);

        v.findViewById(R.id.cluster_load).setOnClickListener(x -> loadCluster());
        v.findViewById(R.id.cluster_back).setOnClickListener(x -> back());
        btnShort.setOnClickListener(x -> confirmShort());
        btnRetry.setOnClickListener(x -> retrySaves());

        etScan.setOnEditorActionListener((tv, id, ev) -> {
            boolean enter = ev != null && ev.getKeyCode() == KeyEvent.KEYCODE_ENTER && ev.getAction() == KeyEvent.ACTION_DOWN;
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_ACTION_NEXT || id == EditorInfo.IME_ACTION_GO || enter) {
                onScan();
                return true;
            }
            return false;
        });
        // A hardware scanner without an Enter suffix types the whole code at once (same rule as paperless)
        etScan.addTextChangedListener(new TextWatcher() {
            boolean burst;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                burst = before == 0 && start == 0 && count > 6;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (burst) {
                    burst = false;
                    etScan.post(ClusterPickingFragment.this::onScan);
                }
            }
        });

        loadLists();
        render();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof Process_Selection_Activity) {
            ((Process_Selection_Activity) getActivity()).setActionBarTitle("Cluster Picking");
        }
    }

    private void loadLists() {
        rfc("ZWM_GET_PACKING_MATERIAL", new JSONObject(), false, r -> {
            packs.clear();
            packs.add("Select packing material");
            JSONArray a = r.optJSONArray("ET_PACK_MAT");
            for (int i = 1; a != null && i < a.length(); i++) packs.add(a.getJSONObject(i).optString("MATNR"));
            packAdapter.notifyDataSetChanged();
        }, this::alert);
        rfc("Z_PICK_CLUSTER_GET", put(new JSONObject(), "IM_WERKS", werks), true, r -> {
            if (r.optInt("EX_RC", -1) != 0) {
                tvPrompt.setText(r.optString("EX_MSG"));
                return;
            }
            JSONObject d = new JSONObject(r.optString("EX_JSON"));
            runId = d.optString("RUN_ID");
            clusters.clear();
            clusterLabels.clear();
            clusterLabels.add("Select cluster (run " + runId + ")");
            JSONArray cs = d.getJSONArray("CLUSTERS");
            for (int i = 0; i < cs.length(); i++) {
                clusters.add(cs.getJSONObject(i));
                clusterLabels.add(ClusterPick.label(cs.getJSONObject(i)));
            }
            clusterAdapter.notifyDataSetChanged();
        }, this::alert);
    }

    private void loadCluster() {
        int ci = spCluster.getSelectedItemPosition();
        int pi = spPack.getSelectedItemPosition();
        if (ci <= 0) { alert("Choose a cluster"); return; }
        if (pi <= 0) { alert("Choose the packing material"); return; }
        JSONObject c = clusters.get(ci - 1);
        if (!c.optBoolean("READY")) { alert("Not ready: the TO is not created for every line yet"); return; }
        String pack = packs.get(pi);
        JSONObject p = put(put(new JSONObject(), "IM_RUN_ID", runId), "IM_CLUSTER", String.valueOf(c.optInt("CLUSTER_NO")));
        rfc("Z_PICK_CLUSTER_GET", p, true, r -> {
            if (r.optInt("EX_RC", -1) != 0) { alert(r.optString("EX_MSG")); return; }
            cp = new ClusterPick(r.optString("EX_JSON"), pack);
            phase = Phase.LOADING;
            render();
            loadPlp2(new ArrayList<>(cp.slots.keySet()), 0, new ArrayList<>());
        }, this::alert);
    }

    /** PLP2 for each slot's delivery, one after the other. */
    private void loadPlp2(List<Integer> slots, int i, List<String> warn) {
        if (i == slots.size()) {
            for (ClusterPick.Slot s : cp.slots.values()) {
                if (!cp.needsHu(s.slot)) { s.saveState = "NOTHING"; s.saveMsg = "nothing open"; }
            }
            huSlot = cp.nextSlotForHu();
            if (huSlot == 0) {
                reset();
                alert("Nothing open to pick in this cluster");
                return;
            }
            phase = Phase.HU;
            render();
            if (!warn.isEmpty()) alert("Lines changed by the TO:\n" + android.text.TextUtils.join("\n", warn));
            return;
        }
        int k = slots.get(i);
        JSONObject p = put(put(put(new JSONObject(), "IM_VBELN", cp.slots.get(k).vbeln),
                "IM_READ_DELV_ALL", "X"), "IM_READ_EAN", "X");
        rfc("ZWM_DELIVERY_GET_DETAILS_PLP2", p, true, r -> {
            if (sapError(r)) {
                reset();
                alert("Slot " + k + ": " + sapMsg(r));
                return;
            }
            warn.addAll(cp.setPlp2(k, r));
            loadPlp2(slots, i + 1, warn);
        }, m -> { reset(); alert(m); });
    }

    private void onScan() {
        String code = etScan.getText().toString().trim();
        etScan.setText("");
        if (code.isEmpty() || busy || cp == null) return;
        if (phase == Phase.HU) {
            scanHu(code);
        } else if (phase == Phase.WALK) {
            after(cp.scan(code));
        }
    }

    private void scanHu(String code) {
        String err = cp.checkHu(huSlot, code);
        if (err != null) { alert(err); return; }
        ClusterPick.Slot s = cp.slots.get(huSlot);
        JSONObject p = put(put(new JSONObject(), "IM_WERKS", werks), "IM_EXIDV", code);
        if (s.likp != null) {
            put(p, "IM_DWERKS", s.likp.optString("KUNNR"));
            put(p, "IM_VBELN", s.likp.optString("VBELN"));
        }
        int slot = huSlot;
        rfc("ZWM_TVS_VAL_EXTERNAL_HU", p, true, r -> {
            if (sapError(r)) { alert("HU " + code + ": " + sapMsg(r)); return; }
            cp.setHu(slot, code);
            huSlot = cp.nextSlotForHu();
            if (huSlot == 0) {
                phase = Phase.WALK;
                cp.start();
            }
            render();
        }, this::alert);
    }

    private void confirmShort() {
        ClusterPick.Task t = cp == null ? null : cp.current();
        if (t == null) return;
        box.getBox("Bin short", "Bin " + t.bin + " has no more of this article for SLOT " + t.slot + "?",
                (d, w) -> after(cp.shortLine()), (d, w) -> { });
    }

    private void after(ClusterPick.Result r) {
        if (!r.ok) alert(r.msg);
        if (r.slotDone > 0) save(r.slotDone);
        if (cp.expect() == ClusterPick.Expect.DONE) phase = Phase.FINISH;
        render();
    }

    /** ZWM_CREATE_HU_AND_ASSIGN_TVS for one slot, in the background. A repeat for a saved HU returns S again. */
    private void save(int k) {
        ClusterPick.Slot s = cp.slots.get(k);
        if (s.itData.length() == 0) {
            s.saveState = "NOTHING";
            s.saveMsg = "nothing picked";
            render();
            return;
        }
        JSONObject p;
        try {
            p = cp.savePayload(k, user);
        } catch (JSONException e) {
            s.saveState = "ERROR";
            s.saveMsg = e.getMessage();
            render();
            return;
        }
        s.saveState = "SAVING";
        s.saveMsg = "";
        rfc("ZWM_CREATE_HU_AND_ASSIGN_TVS", p, false, r -> {
            s.saveState = sapError(r) ? "ERROR" : "SAVED";
            s.saveMsg = sapMsg(r);
            render();
        }, m -> {
            s.saveState = "ERROR";
            s.saveMsg = m;
            render();
        });
        render();
    }

    private void retrySaves() {
        if (cp == null) return;
        for (ClusterPick.Slot s : cp.slots.values()) {
            if ("ERROR".equals(s.saveState) && cp.slotComplete(s.slot)) save(s.slot);
        }
    }

    private void back() {
        if (cp == null || phase == Phase.LIST) {
            getParentFragmentManager().popBackStack();
            return;
        }
        StringBuilder unsaved = new StringBuilder();
        for (ClusterPick.Slot s : cp.slots.values()) {
            if ("SAVING".equals(s.saveState)) { alert("Wait: SLOT " + s.slot + " is saving"); return; }
            if (!"SAVED".equals(s.saveState) && !"NOTHING".equals(s.saveState)) unsaved.append(" ").append(s.slot);
        }
        if (unsaved.length() == 0) {
            getParentFragmentManager().popBackStack();
            return;
        }
        box.getBox("Leave cluster?", "Not saved: SLOT" + unsaved + ". What is scanned for these slots is lost.",
                (d, w) -> getParentFragmentManager().popBackStack(), (d, w) -> { });
    }

    private void reset() {
        cp = null;
        phase = Phase.LIST;
        render();
    }

    private void render() {
        if (!isAdded() || tvPrompt == null) return;
        setup.setVisibility(phase == Phase.LIST ? View.VISIBLE : View.GONE);
        boolean canShort = phase == Phase.WALK && cp.current() != null && cp.expect() != ClusterPick.Expect.HU;
        btnShort.setVisibility(canShort ? View.VISIBLE : View.GONE);
        boolean failed = false;
        boolean allSaved = true;
        StringBuilder sb = new StringBuilder();
        if (cp != null) {
            for (ClusterPick.Slot s : cp.slots.values()) {
                int lines = 0, open = 0;
                for (ClusterPick.Task t : cp.tasks) {
                    if (t.slot != s.slot) continue;
                    lines++;
                    if (!t.done) open++;
                }
                sb.append("S").append(s.slot).append(' ').append(s.store).append(' ').append(s.vbeln)
                        .append(" HU ").append(s.exidv.isEmpty() ? "-" : s.exidv)
                        .append(' ').append(lines - open).append('/').append(lines)
                        .append(s.saveState.isEmpty() ? "" : " " + s.saveState)
                        .append(s.saveMsg.isEmpty() ? "" : " " + s.saveMsg).append('\n');
                failed |= "ERROR".equals(s.saveState);
                allSaved &= "SAVED".equals(s.saveState) || "NOTHING".equals(s.saveState);
            }
        }
        btnRetry.setVisibility(failed ? View.VISIBLE : View.GONE);
        tvSlots.setText(sb);
        switch (phase) {
            case LIST:
                tvPrompt.setText("Choose a cluster");
                tvInfo.setText("");
                break;
            case LOADING:
                tvPrompt.setText("Loading deliveries...");
                break;
            case HU: {
                ClusterPick.Slot s = cp.slots.get(huSlot);
                tvPrompt.setText("Scan HU for SLOT " + huSlot + " (" + s.store + ")");
                tvInfo.setText("Delivery " + s.vbeln);
                break;
            }
            case WALK: {
                ClusterPick.Task t = cp.current();
                tvPrompt.setText(cp.prompt());
                tvInfo.setText(t == null ? "" : "Bin " + t.bin + " | stop " + t.stop + " | " + cp.openLines() + " lines open");
                break;
            }
            case FINISH:
                tvPrompt.setText(allSaved ? "Cluster done: all HUs saved" : "Walk done: waiting for saves");
                tvInfo.setText("");
                break;
        }
        etScan.setEnabled(phase == Phase.HU || phase == Phase.WALK);
        if (etScan.isEnabled()) etScan.requestFocus();
    }

    private void rfc(String fm, JSONObject params, boolean modal, Ok ok, Fail fail) {
        String url = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/noacljsonrfcadaptor?bapiname=" + fm + "&aclclientid=android";
        put(params, "bapiname", fm);
        if (modal) {
            busy = true;
            if (dialog == null) {
                dialog = new ProgressDialog(getContext());
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);
                dialog.show();
            }
        }
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, params, r -> {
            if (modal) idle();
            if (!isAdded()) return;
            try {
                ok.on(r);
            } catch (Exception e) {
                fail.on(fm + ": " + e.getMessage());
            }
        }, e -> {
            if (modal) idle();
            if (!isAdded()) return;
            fail.on(e instanceof TimeoutError || e instanceof NoConnectionError ? "Communication Error!"
                    : e instanceof ServerError ? "Server Side Error!" : e.toString());
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() {
                return params.toString().getBytes();
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(AppConstants.VOLLEY_TIMEOUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().getRequestQueue().add(req);
    }

    private void idle() {
        busy = false;
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private static boolean sapError(JSONObject r) {
        JSONObject e = r.optJSONObject("EX_RETURN");
        return e != null && "E".equals(e.optString("TYPE"));
    }

    private static String sapMsg(JSONObject r) {
        JSONObject e = r.optJSONObject("EX_RETURN");
        return e == null ? "" : e.optString("MESSAGE");
    }

    private static JSONObject put(JSONObject o, String k, String v) {
        try {
            o.put(k, v == null ? "" : v);
        } catch (JSONException ignored) {
            // only null keys throw
        }
        return o;
    }

    private void alert(String msg) {
        if (isAdded()) box.getBox("Err", msg);
    }
}
