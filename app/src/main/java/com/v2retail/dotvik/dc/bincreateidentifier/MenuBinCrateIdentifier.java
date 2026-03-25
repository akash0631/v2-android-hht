package com.v2retail.dotvik.dc.bincreateidentifier;

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
import com.v2retail.dotvik.dc.binwisepicking.FragmentMSABinwisePicking;
import com.v2retail.dotvik.dc.binwisepicking.MenuMSABinwisePickingFragment;
import com.v2retail.dotvik.dc.ptlnew.withpallate.FragmentPTLNewHUCloseAndPrint;

public class MenuBinCrateIdentifier extends Fragment implements
        View.OnClickListener {

    FragmentManager fm;
    Context con;
    String TAG = MenuBinCrateIdentifier.class.getName();
    private OnFragmentInteractionListener mListener;

    Button bin_identifier, crate_identifier;

    public MenuBinCrateIdentifier() {
    }
    public static MenuBinCrateIdentifier newInstance() {
        return new MenuBinCrateIdentifier();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getParentFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_bin_crate_identifier_fragment, container, false);

        con = getContext();

        bin_identifier = view.findViewById(R.id.bin_crate_identifier_bin);
        crate_identifier = view.findViewById(R.id.bin_crate_identifier_crate);

        bin_identifier.setOnClickListener(this);
        crate_identifier.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("Bin / Crate Identifier Process");
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
            case R.id.bin_crate_identifier_bin:
                fragment = FragmentBinCrateIdentifierBinIdentifier.newInstance();
                break;
            case R.id.bin_crate_identifier_crate:
                fragment = FragmentBinCrateIndentifierCrateIndentifier.newInstance();
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.home, fragment, "bin_crate_identifier");
            ft.addToBackStack("bin_crate_identifier");
            ft.commit();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}