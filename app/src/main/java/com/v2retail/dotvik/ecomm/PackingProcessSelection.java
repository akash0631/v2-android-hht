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
public class PackingProcessSelection extends Fragment implements View.OnClickListener {
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
    Button bt_ecommQcPcking, bt_tagPackingStation;

    private com.v2retail.dotvik.dc.DC_DashBoard.OnFragmentInteractionListener mListener;

    public PackingProcessSelection() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_packing_process_selection, container, false);
        fm=getFragmentManager();
        con = getContext();
        box=new AlertBox(con);
        bt_ecommQcPcking = (Button) view.findViewById(R.id.ecomm_qc_picking);
        bt_tagPackingStation = (Button) view.findViewById(R.id.tag_packing_station_with_picked_data);


        bt_ecommQcPcking.setOnClickListener(this);
        bt_tagPackingStation.setOnClickListener(this);


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
                .setActionBarTitle("QC / Packing Processes");
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

            case R.id.ecomm_qc_picking:
                fragment = new  EComm_QC_Picking();
                break;
            case R.id.tag_packing_station_with_picked_data:
                fragment = new PicklistTaggingToPackingStation();
                break;
        }
        clearStack();
        if (fragment != null) {
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
