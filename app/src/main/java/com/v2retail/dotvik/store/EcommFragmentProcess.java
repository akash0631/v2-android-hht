package com.v2retail.dotvik.store;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.v2retail.dotvik.R;
import com.v2retail.util.AlertBox;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EcommFragmentProcess#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EcommFragmentProcess extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button ecomm_picking, ecomm_delivery_update, ecomm_return_process;
    FragmentManager fm;
    AlertBox box;
    Context con;
    String TAG = EcommFragmentProcess.class.getName();

    private OnFragmentInteractionListener mListener;

    public EcommFragmentProcess() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EcommFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EcommFragmentProcess newInstance(String param1, String param2) {
        EcommFragmentProcess fragment = new EcommFragmentProcess();
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
        con=getContext();
        box=new AlertBox(con);
        View view = inflater.inflate(R.layout.fragment_ecomm_process, container, false);
        ecomm_picking = (Button) view.findViewById(R.id.store_ecomm_picking);
        ecomm_delivery_update = (Button) view.findViewById(R.id.store_ecomm_delivery_update);
        ecomm_return_process = (Button) view.findViewById(R.id.store_ecomm_return_process);

        ecomm_picking.setOnClickListener(this);
        ecomm_delivery_update.setOnClickListener(this);
        ecomm_return_process.setOnClickListener(this);
        Log.d(TAG, TAG + " created");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Store Ecomm Process");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        fm.popBackStack();
        Fragment fragment = new DashBoard();
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment);
            ft.commit();
        }
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

            case R.id.store_ecomm_picking:
                fragment = new StoreEcommPicking();
                break;

            case R.id.store_ecomm_delivery_update:
                fragment = new Delivery_Update_Fragment();
                break;


            case R.id.store_ecomm_return_process:
                fragment = new EcomReturnProcess();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "ecommfragmentprocess");
            ft.addToBackStack("ecommfragmentprocess");
            ft.commit();
        }
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
}