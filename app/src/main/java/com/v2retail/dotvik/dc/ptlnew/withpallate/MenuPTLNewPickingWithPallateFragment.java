package com.v2retail.dotvik.dc.ptlnew.withpallate;

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

public class MenuPTLNewPickingWithPallateFragment extends Fragment implements View.OnClickListener {

    FragmentManager fm;
    Context con;
    String TAG = MenuPTLNewPickingWithPallateFragment.class.getName();
    private OnFragmentInteractionListener mListener;

    Button ptl_picking, flr_staging, receive_at_floor, zone_sorting, receive_at_zone, hu_zone_store_mapping, hu_close, hu_print, article_putway_storewise;

    public MenuPTLNewPickingWithPallateFragment() {
    }

    public static MenuPTLNewPickingWithPallateFragment newInstance() {
        return new MenuPTLNewPickingWithPallateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getParentFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_ptl_new_picking_with_pallate_fragment, container, false);
        con = getContext();

        ptl_picking = view.findViewById(R.id.ptl_new_ptl_picking);
        flr_staging = view.findViewById(R.id.ptl_new_flr_staging);
        receive_at_floor = view.findViewById(R.id.ptl_new_crate_receive_at_floor);
        zone_sorting = view.findViewById(R.id.ptl_new_ready_for_zone_sorting);
        receive_at_zone = view.findViewById(R.id.ptl_new_receive_at_zone);
        hu_zone_store_mapping = view.findViewById(R.id.ptl_new_hu_zone_store_mapping);
        hu_close = view.findViewById(R.id.ptl_new_hu_close);
        hu_print = view.findViewById(R.id.ptl_new_hu_print);
        article_putway_storewise = view.findViewById(R.id.ptl_new_article_putway_storewise);

        ptl_picking.setOnClickListener(this);
        flr_staging.setOnClickListener(this);
        receive_at_zone.setOnClickListener(this);
        receive_at_floor.setOnClickListener(this);
        zone_sorting.setOnClickListener(this);
        hu_zone_store_mapping.setOnClickListener(this);
        hu_close.setOnClickListener(this);
        hu_print.setOnClickListener(this);
        article_putway_storewise.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("Picking With Pallate Process");
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        fm.popBackStack();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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


    @Override
    public void onClick(View view) {
        AlertBox box = new AlertBox(con);
        Fragment fragment = null;
        switch (view.getId()) {

            case R.id.ptl_new_ptl_picking:
                fragment = new FragmentPTLNewPTLPicking();
                break;
            case R.id.ptl_new_flr_staging:
                fragment = FragmentPTLNewFloorModule.newInstance("Floor Staging");
                break;
            case R.id.ptl_new_crate_receive_at_floor:
                fragment = FragmentPTLNewFloorModule.newInstance("Receive at Floor");
                break;
            case R.id.ptl_new_ready_for_zone_sorting:
                fragment = FragmentPTLNewFloorModule.newInstance("Transfer To Zone Sorting");
                break;
            case R.id.ptl_new_receive_at_zone:
                fragment = FragmentPTLNewReceiveAtZone.newInstance();
                break;
            case R.id.ptl_new_hu_zone_store_mapping:
                fragment = FragmentPTLNewHUZoneStoreMapping.newInstance(null);
                break;
            case R.id.ptl_new_hu_close:
                fragment = FragmentPTLNewHUCloseAndPrint.newInstance("HU Close");
                break;
            case R.id.ptl_new_hu_print:
                fragment = FragmentPTLNewHUCloseAndPrint.newInstance("HU Print");
                break;
            case R.id.ptl_new_article_putway_storewise:
                fragment = FragmentPTLNewArticlePutwayStorewise.newInstance();
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.home, fragment, "ptlnew_with_pallate");
            ft.addToBackStack("ptlnew_with_pallate");
            ft.commit();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}