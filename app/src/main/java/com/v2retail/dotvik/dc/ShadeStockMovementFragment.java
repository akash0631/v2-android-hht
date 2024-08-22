package com.v2retail.dotvik.dc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.v2retail.dotvik.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShadeStockMovementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShadeStockMovementFragment extends Fragment implements View.OnClickListener {

    private Button picking,putway;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShadeStockMovementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShadeStockMovementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShadeStockMovementFragment newInstance(String param1, String param2) {
        ShadeStockMovementFragment fragment = new ShadeStockMovementFragment();
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

        View view = inflater.inflate(R.layout.fragment_shade_stock_movement, container, false);

        picking = (Button) view.findViewById(R.id.picking);
        putway = (Button) view.findViewById(R.id.putway);

        picking.setOnClickListener(this);
        putway.setOnClickListener(this);
        return view ;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("Shade Stock Movement");
    }

    @Override
    public void onClick(View v) {

        Fragment fragment = null;
        switch (v.getId()){

            case R.id.picking:
                fragment = new StockMovementPickingFragment("picking");
                break;

            case R.id.putway:
                fragment = new StockMovementPickingFragment("putway");
                break;

        }
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "outward");
            ft.addToBackStack("outward");
            ft.commit();
        }


    }
}