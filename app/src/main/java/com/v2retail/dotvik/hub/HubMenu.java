package com.v2retail.dotvik.hub;

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
import com.v2retail.dotvik.dc.CLAProcessFragment;
import com.v2retail.dotvik.dc.DC_DashBoard;
import com.v2retail.dotvik.dc.InwardFragment;
import com.v2retail.dotvik.dc.OutWardFragment;
import com.v2retail.dotvik.dc.Process_Selection_Activity;
import com.v2retail.dotvik.dc.Stock_Take_Process_Fragment;
import com.v2retail.dotvik.dc.V11ToMsaFragment;
import com.v2retail.dotvik.dc.grt.GRTProcessMenu;
import com.v2retail.dotvik.dc.ptl.PTLProcessFragment;
import com.v2retail.dotvik.hub.inward.MenuHubInward;
import com.v2retail.dotvik.hub.outward.MenuHubOutward;
import com.v2retail.util.AlertBox;

public class HubMenu extends Fragment implements
        View.OnClickListener,
        MenuHubInward.OnFragmentInteractionListener,
        MenuHubOutward.OnFragmentInteractionListener {

    Context con;
    FragmentManager fm;
    AlertBox box;

    Button inward, outward;
    private HubMenu.OnFragmentInteractionListener mListener;

    public HubMenu() {

    }

    public static HubMenu newInstance() {
        return new HubMenu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getParentFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hub_menu, container, false);

        fm=getFragmentManager();
        con = getContext();
        box=new AlertBox(con);
        inward = view.findViewById(R.id.hub_inward);
        outward = view.findViewById(R.id.hub_outward);
        inward.setOnClickListener(this);
        outward.setOnClickListener(this);
        return view;
    }

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
        if (context instanceof HubMenu.OnFragmentInteractionListener) {
            mListener = (HubMenu.OnFragmentInteractionListener) context;
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
        ((HubProcessSelectionActivity) getActivity())
                .setActionBarTitle("HUB Process");
    }

    @Override
    public void onClick(View view) {
        setFragment(view.getId());
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public void setFragment(int fragmentID) {
        Fragment fragment = null;
        switch (fragmentID) {
            case R.id.hub_inward:
                fragment = new MenuHubInward();
                break;
            case R.id.hub_outward:
                fragment = new MenuHubOutward();
                break;
        }
        clearStack();
        if (fragment != null) {
            FragmentTransaction ft =fm.beginTransaction();
            ft.replace(R.id.home, fragment);
            ft.addToBackStack("hub_menu");
            ft.commit();
        }
    }

    public void clearStack() {
        if(fm!=null)
        {
            int count=fm.getBackStackEntryCount();
            if (count > 1) {
                fm.popBackStackImmediate();
            }
        }

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}