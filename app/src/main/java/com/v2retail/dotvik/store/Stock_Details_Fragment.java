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
 * Activities that contain this fragment must implement the
 * {@link Stock_Details_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Stock_Details_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Stock_Details_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "pickType";
    private static final String ARG_PARAM2 = "param2";
    Button genericVar;
    Button singleVar;
    FragmentManager fm;
    AlertBox box;
    String TAG = Stock_Details_Fragment.class.getName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Stock_Details_Fragment() {
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
    public static Stock_Details_Fragment newInstance(String param1, String param2) {
        Stock_Details_Fragment fragment = new Stock_Details_Fragment();
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

        }

        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        box = new AlertBox(getContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stockdetail, container, false);
        genericVar = (Button) view.findViewById(R.id.generic);
        singleVar = (Button) view.findViewById(R.id.single);
        try {
            genericVar.setOnClickListener(this);
            singleVar.setOnClickListener(this);
        } catch (Exception e) {
            box.getErrBox(e);
        }

        Log.d(TAG, TAG + " created");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Stock Details");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        fm.popBackStack();
        Fragment fragment=new DashBoard();
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment );
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

        Bundle args=new Bundle();
        switch (view.getId()) {

            case R.id.generic:
                args.putString("type","generic");
                break;

            case R.id.single:
                args.putString("type","single");
                break;


        }
        if(mParam1!=null)
        {
             args.putString("pickType",mParam1);
             if(mParam1.equals("sale"))
             {
                 fragment = new Article_Sale_Scan_Fragment();
             }
             else if(mParam1.equals("stock"))
             {
                fragment=new Article_Scan_Fragment();
             }
        }


        if (fragment != null) {
            fragment.setArguments(args);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "Stock Details");
            ft.addToBackStack("Stock Details");
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
