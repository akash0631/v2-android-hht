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
import com.v2retail.dotvik.dc.InwardFragment;
import com.v2retail.dotvik.dc.OutWardFragment;
import com.v2retail.dotvik.dc.Stock_Take_Process_Fragment;
import com.v2retail.util.AlertBox;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.v2retail.dotvik.dc.DC_DashBoard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.v2retail.dotvik.dc.DC_DashBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EComm_DashBoard extends Fragment implements View.OnClickListener,
        InwardFragment.OnFragmentInteractionListener,
        OutWardFragment.OnFragmentInteractionListener,
        Stock_Take_Process_Fragment.OnFragmentInteractionListener {
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
    Button bt_picking, bt_putwall, bt_packing, bt_cla, cancel_qc_putaway, crate_to_msa_bin,store_order_reject;
    private com.v2retail.dotvik.dc.DC_DashBoard.OnFragmentInteractionListener mListener;

    public EComm_DashBoard() {
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
        View view = inflater.inflate(R.layout.fragment_ecomm_process_selection, container, false);
        fm=getFragmentManager();
        con = getContext();
        box=new AlertBox(con);
        bt_picking = (Button) view.findViewById(R.id.picking);
        bt_putwall = (Button) view.findViewById(R.id.putwall);
        bt_packing = (Button) view.findViewById(R.id.packing);
        bt_cla = (Button) view.findViewById(R.id.cla);
        cancel_qc_putaway = (Button) view.findViewById(R.id.cancelqcputaway);
        crate_to_msa_bin = (Button) view.findViewById(R.id.putawaycratetomsabin);
        store_order_reject = view.findViewById(R.id.storeorderreject);

        bt_picking.setOnClickListener(this);
        bt_putwall.setOnClickListener(this);
        bt_packing.setOnClickListener(this);
        bt_cla.setOnClickListener(this);
        cancel_qc_putaway.setOnClickListener(this);
        crate_to_msa_bin.setOnClickListener(this);
        store_order_reject.setOnClickListener(this);
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
                .setActionBarTitle("Ecomm Processes");
    }

    @Override
    public void onClick(View view) {
        setFragment(view.getId());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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

            case R.id.picking:
                fragment = new EComm_Picking_DateFilter();
              //   fragment = new SavePicklistFragment();
                break;
            case R.id.putwall:
                fragment = new Putwall_Process_Selection();
                break;
            case R.id.packing:
                fragment = new PackingProcessSelection();
                break;
            case R.id.cla:
               // fragment=new Stock_Take_Process_Fragment();
                fragment = new CLA_Process_Selection();
                break;
            case R.id.cancelqcputaway:
                // fragment=new Stock_Take_Process_Fragment();
                fragment = new EcommCancelQCPutaway();
                break;
            case R.id.putawaycratetomsabin:
                // fragment=new Stock_Take_Process_Fragment();
                fragment = new Putaway_CrateToMSABin();
                break;
            case R.id.storeorderreject:
                // fragment=new Stock_Take_Process_Fragment();
                fragment = new EcommStoreOrderReject();
                break;
        }
        clearStack();
        if (fragment != null) {
            FragmentTransaction ft =fm.beginTransaction();
            ft.replace(R.id.home, fragment);
            ft.addToBackStack("dashboard");
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
