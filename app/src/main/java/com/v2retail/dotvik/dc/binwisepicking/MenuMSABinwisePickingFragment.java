package com.v2retail.dotvik.dc.binwisepicking;

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
import android.widget.Toast;

import com.v2retail.dotvik.R;
import com.v2retail.dotvik.dc.Process_Selection_Activity;
import com.v2retail.dotvik.dc.ptlnew.MenuPTLNewFragment;
import com.v2retail.dotvik.dc.ptlnew.fullcrate30.MenuPTLNewPickingFullCrate30;
import com.v2retail.dotvik.dc.ptlnew.ptl40.MenuPTLNewPickingProcess40;
import com.v2retail.dotvik.dc.ptlnew.withoutpallate.MenuPTLNewPickingWithoutPallateFragment;
import com.v2retail.dotvik.dc.ptlnew.withpallate.FragmentPTLNewHUCloseAndPrint;
import com.v2retail.dotvik.dc.ptlnew.withpallate.MenuPTLNewPickingWithPallateFragment;
import com.v2retail.util.AlertBox;

public class MenuMSABinwisePickingFragment extends Fragment implements
        View.OnClickListener {

    FragmentManager fm;
    Context con;
    String TAG = MenuMSABinwisePickingFragment.class.getName();
    private OnFragmentInteractionListener mListener;

    Button picking, external_hu_print;

    public MenuMSABinwisePickingFragment() {
        // Required empty public constructor
    }

    public static MenuMSABinwisePickingFragment newInstance() {
        return new MenuMSABinwisePickingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getParentFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_m_s_a_binwise_picking_fragment, container, false);

        con = getContext();

        picking = view.findViewById(R.id.msa_binwise_pikcing_picking);
        external_hu_print = view.findViewById(R.id.msa_binwise_pikcing_hu_print);

        picking.setOnClickListener(this);
        external_hu_print.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("MSA Binwise Picking Process");
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
        Fragment fragment = null;
        switch (view.getId()) {

            case R.id.msa_binwise_pikcing_picking:
                fragment = FragmentMSABinwisePicking.newInstance();
                break;
            case R.id.msa_binwise_pikcing_hu_print:
                fragment = FragmentPTLNewHUCloseAndPrint.newInstance("msa_binwise");
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.home, fragment, "msa_binwise_picking");
            ft.addToBackStack("msa_binwise_picking");
            ft.commit();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}