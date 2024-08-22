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
 * Use the {@link StorePutwallProcess#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StorePutwallProcess extends Fragment implements View.OnClickListener{

    private static final String TAG = StorePutwallProcess.class.getName();
    private GRTProcessFragment.OnFragmentInteractionListener mListener;

    Button external_hu_mapping,crate_sorting,hu_close;
    FragmentManager fm;
    Context con;
    View rootView;

    public StorePutwallProcess() {
        // Required empty public constructor
    }

    public static StorePutwallProcess newInstance(String param1, String param2) {
        StorePutwallProcess fragment = new StorePutwallProcess();
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
        rootView = inflater.inflate(R.layout.fragment_store_putwall_process, container, false);
        con = getContext();
        external_hu_mapping = rootView.findViewById(R.id.btn_grt_spwall_external_hu_mapping);
        crate_sorting = rootView.findViewById(R.id.grt_crate_sorting);
        hu_close = rootView.findViewById(R.id.btn_grt_spwall_external_hu_close);

        external_hu_mapping.setOnClickListener(this);
        crate_sorting.setOnClickListener(this);
        hu_close.setOnClickListener(this);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("Store Putwall Process");
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

            case R.id.btn_grt_spwall_external_hu_mapping:
                fragment=new StorePutwallExternalHUMapping();
                break;
            case R.id.grt_crate_sorting:
                fragment=new GRTCrateSortingProcess();
                fragment.setArguments(args);
                break;
            case R.id.btn_grt_spwall_external_hu_close:
                fragment=new StorePutwallHUClose();
                args.putString("IM_HU_CLOSE","N");
                fragment.setArguments(args);
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "grt_store_putwall_process_list");
            ft.addToBackStack("grt_store_putwall_process_list");
            ft.commit();
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}