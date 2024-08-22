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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GRTProcessMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GRTProcessMenu extends Fragment implements View.OnClickListener {

    private static final String TAG = GRTProcessMenu.class.getName();
    private DC_DashBoard.OnFragmentInteractionListener mListener;

    Button btn_mix_picking,btn_single_picking,btn_combo_picking;
    FragmentManager fm;
    Context con;
    View rootView;

    public GRTProcessMenu() {
        // Required empty public constructor
    }

    public static GRTProcessMenu newInstance(String param1, String param2) {
        GRTProcessMenu fragment = new GRTProcessMenu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();
    }
    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("GRT Process");
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grt_process_menu, container, false);
        btn_mix_picking = rootView.findViewById(R.id.btn_mix_picking);
        btn_single_picking = rootView.findViewById(R.id.btn_single_picking);
        btn_combo_picking = rootView.findViewById(R.id.btn_combo_picking);
        con = getContext();

        btn_mix_picking.setOnClickListener(this);
        btn_single_picking.setOnClickListener(this);
        btn_combo_picking.setOnClickListener(this);

        return rootView;
    }
    @Override
    public void onClick(View view) {
        AlertBox box = new AlertBox(con);
        Fragment fragment = null;
        switch (view.getId()) {

            case R.id.btn_mix_picking:
                fragment=new GRTProcessFragment();
                break;
            case R.id.btn_single_picking:
                fragment=new GRTSinglePickingProcess();
                break;
            case R.id.btn_combo_picking:
                fragment=new GRTComboPickingMenu();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "grt_process_menu");
            ft.addToBackStack("grt_process_menu");
            ft.commit();
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}