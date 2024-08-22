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
import com.v2retail.dotvik.dc.InwardFragment;
import com.v2retail.dotvik.dc.Process_Selection_Activity;
import com.v2retail.util.AlertBox;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GRTProcessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GRTProcessFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = GRTProcessFragment.class.getName();
    private GRTProcessFragment.OnFragmentInteractionListener mListener;

    Button zone_sorting,crate_picking, store_putwall,hu_creation,return_putaway,crate_to_msa,rev_putway_crate,btn_sm_crate_sorting;
    FragmentManager fm;
    Context con;
    View rootView;

    public GRTProcessFragment() {
    }

    public static GRTProcessFragment newInstance(String param1, String param2) {
        GRTProcessFragment fragment = new GRTProcessFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grt_process, container, false);
        con = getContext();
        zone_sorting = rootView.findViewById(R.id.grt_crate_zone_sorting);
        crate_picking = rootView.findViewById(R.id.grt_crate_picking);
        store_putwall = rootView.findViewById(R.id.btn_grt_store_putwall);
        hu_creation = rootView.findViewById(R.id.hu_creation_process);
        return_putaway = rootView.findViewById(R.id.grt_return_order_putaway);
        crate_to_msa = rootView.findViewById(R.id.grt_crate_to_msa_bin);
        rev_putway_crate = rootView.findViewById(R.id.grt_rev_putway_crate);
        btn_sm_crate_sorting = rootView.findViewById(R.id.btn_grt_mix_crate_sorting);


        zone_sorting.setOnClickListener(this);
        crate_picking.setOnClickListener(this);
        store_putwall.setOnClickListener(this);
        hu_creation.setOnClickListener(this);
        return_putaway.setOnClickListener(this);
        crate_to_msa.setOnClickListener(this);
        rev_putway_crate.setOnClickListener(this);
        btn_sm_crate_sorting.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("GRT Mix Process");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InwardFragment.OnFragmentInteractionListener) {
            mListener = (GRTProcessFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        fm.popBackStack();
    }

    @Override
    public void onClick(View view) {
        AlertBox box = new AlertBox(con);
        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (view.getId()) {

            case R.id.grt_crate_zone_sorting:
                fragment = new GRTZoneSorting();
                args.putString("crate","");
                args.putString("zone","");
                args.putString("mode","grt");
                fragment.setArguments(args);
                break;
            case R.id.grt_rev_putway_crate:
                fragment=new GRTRevPutwayCrate();
                break;
            case R.id.grt_crate_picking:
                fragment=new GRTCratePickingProcess();
                break;
            case R.id.btn_grt_mix_crate_sorting:
                fragment=new GRTSingleMixCrateSorting();
                args.putString("sortmode","mix");
                fragment.setArguments(args);
                break;
            case R.id.btn_grt_store_putwall:
                fragment=new StorePutwallProcess();
                break;
            case R.id.hu_creation_process:
                fragment=new HUCreationProcess();
                break;
            case R.id.grt_return_order_putaway:
                fragment=new GRTReturnPutAway();
                break;
            case R.id.grt_crate_to_msa_bin:
                fragment=new GRTCrateToMSABin();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "grt_process_list");
            ft.addToBackStack("grt_process_list");
            ft.commit();
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}