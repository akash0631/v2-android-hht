package com.v2retail.dotvik.hub.inward;

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
import com.v2retail.dotvik.hub.HubMenu;
import com.v2retail.dotvik.hub.HubProcessSelectionActivity;
import com.v2retail.util.AlertBox;
import com.v2retail.util.SharedPreferencesData;

public class MenuHubInward extends Fragment implements View.OnClickListener {

    private View rootView;
    private FragmentActivity activity;
    FragmentManager fm;
    Context con;
    ProgressDialog dialog;
    AlertBox box;
    Button hu_grc;
    private MenuHubInward.OnFragmentInteractionListener mListener;

    public MenuHubInward() {
    }

    public static MenuHubInward newInstance(String param1, String param2) {
        return new MenuHubInward();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getParentFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.hub_inward_menu, container, false);
        con = getContext();
        hu_grc = rootView.findViewById(R.id.hub_inward_hu_grc);

        hu_grc.setOnClickListener(this);
        return rootView;
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
        if (context instanceof MenuHubInward.OnFragmentInteractionListener) {
            mListener = (MenuHubInward.OnFragmentInteractionListener) context;
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
                .setActionBarTitle("HUB Inward");
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
            case R.id.hub_inward_hu_grc:
                fragment = new FragmentHUGRC();
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft =fm.beginTransaction();
            ft.replace(R.id.home, fragment);
            ft.addToBackStack("hub_menu_inward");
            ft.commit();
        }
    }
}