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
import com.v2retail.dotvik.dc.DC_DashBoard;
import com.v2retail.dotvik.dc.Process_Selection_Activity;
import com.v2retail.util.AlertBox;


public class GRTSingleDeTagHuProcess extends Fragment implements View.OnClickListener {

    private static final String TAG = GRTSingleDeTagHuProcess.class.getName();
    private DC_DashBoard.OnFragmentInteractionListener mListener;

    Button btn_hu_close;
    FragmentManager fm;
    Context con;
    View rootView;

    public GRTSingleDeTagHuProcess() {
        // Required empty public constructor
    }

    public static GRTSingleDeTagHuProcess newInstance(String param1, String param2) {
        GRTSingleDeTagHuProcess fragment = new GRTSingleDeTagHuProcess();
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("GRT De-Tag HU Process");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DC_DashBoard.OnFragmentInteractionListener) {
            mListener = (DC_DashBoard.OnFragmentInteractionListener) context;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_grt_single_de_tag_hu, container, false);
        btn_hu_close = rootView.findViewById(R.id.btn_grt_single_de_tag_hu_close);
        con = getContext();
        btn_hu_close.setOnClickListener(this);
        return rootView;
    }
    @Override
    public void onClick(View view) {
        AlertBox box = new AlertBox(con);
        Fragment fragment = null;
        switch (view.getId()) {

            case R.id.btn_grt_single_de_tag_hu_close:
                fragment=new StorePutwallHUClose();
                Bundle args = new Bundle();
                args.putString("IM_HU_CLOSE","N");
                fragment.setArguments(args);
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "grt_single_de_tag_hu_process_menu");
            ft.addToBackStack("grt_single_de_tag_hu_process_menu");
            ft.commit();
        }
    }
}