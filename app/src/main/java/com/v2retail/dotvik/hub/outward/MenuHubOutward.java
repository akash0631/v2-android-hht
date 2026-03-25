package com.v2retail.dotvik.hub.outward;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.v2retail.dotvik.R;
import com.v2retail.dotvik.dc.InwardFragment;
import com.v2retail.dotvik.dc.OutWardFragment;
import com.v2retail.dotvik.hub.HubProcessSelectionActivity;
import com.v2retail.dotvik.hub.inward.MenuHubInward;
import com.v2retail.util.AlertBox;

public class MenuHubOutward extends Fragment implements View.OnClickListener {

    private View rootView;
    private FragmentActivity activity;
    FragmentManager fm;
    Button dispatch_from_hub_huwise;
    private MenuHubOutward.OnFragmentInteractionListener mListener;

    public MenuHubOutward() {
    }

    public static MenuHubOutward newInstance() {
        return new MenuHubOutward();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getParentFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.hub_outward_menu, container, false);
        dispatch_from_hub_huwise = rootView.findViewById(R.id.hub_outward_dispatch_from_hub_huwise);
        dispatch_from_hub_huwise.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenuHubOutward.OnFragmentInteractionListener) {
            mListener = (MenuHubOutward.OnFragmentInteractionListener) context;
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
                .setActionBarTitle("HUB Outward");
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
            case R.id.hub_outward_dispatch_from_hub_huwise:
                fragment = new FragmentDispatchFromHUBHUWise();
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft =fm.beginTransaction();
            ft.replace(R.id.home, fragment);
            ft.addToBackStack("hub_menu_outward");
            ft.commit();
        }
    }
}