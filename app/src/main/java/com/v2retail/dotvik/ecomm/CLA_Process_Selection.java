package com.v2retail.dotvik.ecomm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.v2retail.dotvik.R;
import com.v2retail.util.AlertBox;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.v2retail.dotvik.dc.DC_DashBoard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.v2retail.dotvik.dc.DC_DashBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CLA_Process_Selection extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context con;
    FragmentManager fm;
    AlertBox box;
    Button bt_scanCratePalette, bt_tagCrate, bt_untagCrate;
    Button bt_handoverToCp;
    Button bt_rejectedCourierScanning;

    private com.v2retail.dotvik.dc.DC_DashBoard.OnFragmentInteractionListener mListener;

    public CLA_Process_Selection() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InboundFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.v2retail.dotvik.dc.DC_DashBoard newInstance(String param1, String param2) {
        com.v2retail.dotvik.dc.DC_DashBoard fragment = new com.v2retail.dotvik.dc.DC_DashBoard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cla_process_selection, container, false);
        fm=getFragmentManager();
        con = getContext();
        box=new AlertBox(con);
        bt_scanCratePalette = (Button) view.findViewById(R.id.scan_crate_in_ecom_palette);
        bt_tagCrate = (Button) view.findViewById(R.id.tag_palette_with_ecom_cla_bin);
        bt_untagCrate = (Button) view.findViewById(R.id.untag_palette_with_ecom_cla_bin);
        bt_handoverToCp = (Button) view.findViewById(R.id.handover_to_courier_partner);
        bt_rejectedCourierScanning = (Button) view.findViewById(R.id.rejected_courier_scanning);


        bt_scanCratePalette.setOnClickListener(this);
        bt_tagCrate.setOnClickListener(this);
        bt_untagCrate.setOnClickListener(this);
        bt_handoverToCp.setOnClickListener(this);
        bt_rejectedCourierScanning.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }



        if (getFragmentManager().getBackStackEntryCount() == 1){

            box.getDialogBox(getActivity());

        }
        else {
            fm.popBackStack();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof com.v2retail.dotvik.dc.DC_DashBoard.OnFragmentInteractionListener) {
            mListener = (com.v2retail.dotvik.dc.DC_DashBoard.OnFragmentInteractionListener) context;
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
    public void onResume() {
        super.onResume();
        ((Ecomm_Process_Selection) getActivity())
                .setActionBarTitle("CLA Processes");
    }

    @Override
    public void onClick(View view) {
        setFragment(view.getId());
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void setFragment(int fragmentID) {

        Fragment fragment = null;
        switch (fragmentID) {

            case R.id.scan_crate_in_ecom_palette:
                fragment = new CLA_ScanEcomCrateInPalette();
                break;
            case R.id.tag_palette_with_ecom_cla_bin:
                fragment = new CLA_TagPaletteWithBin();
                break;
            case R.id.untag_palette_with_ecom_cla_bin:
                fragment = new CLA_UntagPaletteWithBin();
                break;
            case R.id.handover_to_courier_partner:
                fragment = new CLA_HandoverToCourierPartner();
                break;
            case R.id.rejected_courier_scanning:
                fragment = new CLA_RejectedCourierScanning();
                break;
        }

        if (fragment != null) {
            clearStack();

            FragmentTransaction ft =fm.beginTransaction();
            ft.replace(R.id.home, fragment);
            ft.addToBackStack("Putway_Empty_Crate");
            ft.commit();
        }
    }

    public void clearStack() {
        if(fm!=null)
        {int count=fm.getBackStackEntryCount();
            if (count > 1) {
                fm.popBackStackImmediate();
            }

        }

    }
}
