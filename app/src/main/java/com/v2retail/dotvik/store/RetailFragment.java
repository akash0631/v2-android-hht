package com.v2retail.dotvik.store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.v2retail.dotvik.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetailFragment extends Fragment  implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button getStock;
    private Button postDataOffline;
    FragmentManager fm;

    public RetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RetailFragment newInstance(String param1, String param2) {
        RetailFragment fragment = new RetailFragment();
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
        fm=getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getStock = view.findViewById(R.id.getStock);
        postDataOffline = view.findViewById(R.id.postDataOffline);
        getStock.setOnClickListener(this);
        postDataOffline.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.getStock:
                fragment = new GetStockFragment();
                break;
            case R.id.postDataOffline:
                fragment = new PostDataOffiineFragment();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.home, fragment, "Retail");
            ft.addToBackStack("Retail");
            ft.commit();
        }

    }
}