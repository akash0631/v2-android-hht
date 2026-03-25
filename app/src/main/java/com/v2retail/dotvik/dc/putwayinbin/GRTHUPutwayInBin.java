package com.v2retail.dotvik.dc.putwayinbin;

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

public class GRTHUPutwayInBin extends Fragment implements View.OnClickListener {

    FragmentManager fm;
    Context con;
    View view;

    String TAG = GRTHUPutwayInBin.class.getName();
    private OnFragmentInteractionListener mListener;

    Button btn_direct_bin, btn_hu_to_pallet, btn_pallet_to_bin;

    public GRTHUPutwayInBin() {
        // Required empty public constructor
    }

    public static GRTHUPutwayInBin newInstance() {
        return new GRTHUPutwayInBin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getParentFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_grt_hu_putway_in_bin, container, false);
        con = getContext();

        btn_direct_bin = view.findViewById(R.id.btn_hu_in_bin_direct_bin);
        btn_hu_to_pallet = view.findViewById(R.id.btn_hu_in_bin_hu_to_pallet);
        btn_pallet_to_bin = view.findViewById(R.id.btn_hu_in_bin_pallet_to_bin);

        btn_direct_bin.setOnClickListener(this);
        btn_hu_to_pallet.setOnClickListener(this);
        btn_pallet_to_bin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity()).setActionBarTitle("GRT HU Putway In Bin");
    }

    // TODO: Rename method, update argument and hook method into UI event
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

            case R.id.btn_hu_in_bin_direct_bin:
                fragment = new FragmentDirectBin();
                break;

            case R.id.btn_hu_in_bin_hu_to_pallet:
                fragment = new FragmentHUToPallet();
                break;

            case R.id.btn_hu_in_bin_pallet_to_bin:
                fragment = new FragmentPalletToBin();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "putway_in_bin");
            ft.addToBackStack("putway_in_bin");
            ft.commit();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}