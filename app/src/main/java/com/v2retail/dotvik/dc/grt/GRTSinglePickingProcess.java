package com.v2retail.dotvik.dc.grt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.v2retail.dotvik.R;
import com.v2retail.dotvik.dc.Process_Selection_Activity;
import com.v2retail.util.AlertBox;


public class GRTSinglePickingProcess extends Fragment implements View.OnClickListener {

    private static final String TAG = GRTSinglePickingProcess.class.getName();
    private GRTSinglePickingProcess.OnFragmentInteractionListener mListener;

    Button btn_hu_zone_store,btn_crate_scan,btn_crate_picking,btn_de_tag_hu,btn_sm_crate_sorting,btn_hu_pick;
    FragmentManager fm;
    Context con;
    View rootView;

    public GRTSinglePickingProcess() {

    }
    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("GRT Single Process");
    }
    public static GRTSinglePickingProcess newInstance(String param1, String param2) {
        GRTSinglePickingProcess fragment = new GRTSinglePickingProcess();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grt_single_picking_process, container, false);
        con = getContext();

        btn_hu_zone_store = rootView.findViewById(R.id.btn_hu_zone_store);
        btn_crate_scan = rootView.findViewById(R.id.btn_crate_scan);
        btn_crate_picking = rootView.findViewById(R.id.grt_single_pick_crate_picking);
        btn_de_tag_hu = rootView.findViewById(R.id.btn_grt_single_pick_de_tag_hu);
        btn_sm_crate_sorting = rootView.findViewById(R.id.btn_grt_single_crate_sorting);
        btn_hu_pick = rootView.findViewById(R.id.btn_grt_single_hu_pick);

        btn_hu_zone_store.setOnClickListener(this);
        btn_crate_scan.setOnClickListener(this);
        btn_crate_picking.setOnClickListener(this);
        btn_de_tag_hu.setOnClickListener(this);
        btn_sm_crate_sorting.setOnClickListener(this);
        btn_hu_pick.setOnClickListener(this);
        return rootView;
    }
    @Override
    public void onClick(View view) {
        AlertBox box = new AlertBox(con);
        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (view.getId()) {

            case R.id.btn_hu_zone_store:
                fragment=new GRTTagHUStoreZone();
                break;
            case R.id.btn_crate_scan:
                fragment=new GRTSinglePickCrateScan();
                break;
            case R.id.grt_single_pick_crate_picking:
                fragment=new GRTCratePickingProcess();
                break;
            case R.id.btn_grt_single_crate_sorting:
                fragment=new GRTSingleMixCrateSorting();
                args.putString("sortmode","single");
                fragment.setArguments(args);
                break;
            case R.id.btn_grt_single_pick_de_tag_hu:
                fragment=new GRTSingleDeTagHuProcess();
                break;
            case R.id.btn_grt_single_hu_pick:
                fragment=new GRTSingleComboHUPick();
                args.putString("singleOrCombo","single");
                fragment.setArguments(args);
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "grt_single_picking_process_menu");
            ft.addToBackStack("grt_single_picking_process_menu");
            ft.commit();
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}