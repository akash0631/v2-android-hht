package com.v2retail.dotvik.store;

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
import com.v2retail.util.AlertBox;

/**
 * @author Narayanan
 * @version 11.71
 * {@code Author: Narayanan, Revision: 1, Created: 30th Jul 2024, Modified: 30th Jul 2024}
 */
public class MenuFragmentStoreDisplayOutbound extends Fragment implements View.OnClickListener {

    Context con;
    FragmentManager fm;
    AlertBox box;
    private OnFragmentInteractionListener mListener;
    Button putway;

    public MenuFragmentStoreDisplayOutbound() {
        // Required empty public constructor
    }

    public static MenuFragmentStoreDisplayOutbound newInstance(String param1, String param2) {
        MenuFragmentStoreDisplayOutbound fragment = new MenuFragmentStoreDisplayOutbound();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_store_display_outbound, container, false);
        con=getContext();
        box=new AlertBox(con);
        fm=getActivity().getSupportFragmentManager();

        putway = view.findViewById(R.id.store_display_outbound_picking_0001_0010);
        putway.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        setFragment(view.getId());
    }

    public void setFragment(int fragmentID) {
        Fragment fragment = null;
        switch (fragmentID) {

            case R.id.store_display_outbound_picking_0001_0010:
                fragment = FragmentStoreDisplayOutboundPicking0001To0010.newInstance("Display > Outbound");
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft =fm.beginTransaction();
            ft.replace(R.id.home, fragment, "StoreDisplayOutbound");
            ft.addToBackStack("StoreDisplayOutbound");
            ft.commit();
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        if (fm.getBackStackEntryCount() == 1){

            box.getDialogBox(getActivity());

        }
        else {
            fm.popBackStack();
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
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Display > Outbound");
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}