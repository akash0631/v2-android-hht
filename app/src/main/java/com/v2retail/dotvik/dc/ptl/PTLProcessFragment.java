package com.v2retail.dotvik.dc.ptl;

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
 * Use the {@link PTLProcessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PTLProcessFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = PTLProcessFragment.class.getName();
    private PTLProcessFragment.OnFragmentInteractionListener mListener;

    Button ptl_picking,ptl_return_putaway,ptl_crate_to_msa,ptl_crate_send;
    FragmentManager fm;
    Context con;
    View rootView;

    public PTLProcessFragment() {
    }
    public static PTLProcessFragment newInstance(String param1, String param2) {
        PTLProcessFragment fragment = new PTLProcessFragment();
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
        rootView = inflater.inflate(R.layout.fragment_ptl_process, container, false);
        ptl_picking = rootView.findViewById(R.id.ptl_picking);
        ptl_return_putaway = rootView.findViewById(R.id.ptl_return_order_putaway);
        ptl_crate_to_msa = rootView.findViewById(R.id.ptl_crate_to_msa_bin);
        ptl_crate_send = rootView.findViewById(R.id.ptl_crate_send);
        ptl_picking.setOnClickListener(this);
        ptl_return_putaway.setOnClickListener(this);
        ptl_crate_to_msa.setOnClickListener(this);
        ptl_crate_send.setOnClickListener(this);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("PTL Process");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InwardFragment.OnFragmentInteractionListener) {
            mListener = (PTLProcessFragment.OnFragmentInteractionListener) context;
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
        switch (view.getId()) {

            case R.id.ptl_picking:
                fragment = new PTLPicking();
                break;
            case R.id.ptl_return_order_putaway:
                fragment = new PTLReturnPutAway();
                break;
            case R.id.ptl_crate_to_msa_bin:
                fragment = new PTLCrateToMSABin();
                break;
            case R.id.ptl_crate_send:
                fragment = new PTLCrateSendToPTL();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "ptl_process_list");
            ft.addToBackStack("ptl_process_list");
            ft.commit();
        }
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}