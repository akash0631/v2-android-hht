package com.v2retail.dotvik.store;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.v2retail.dotvik.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Article_Sales_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Article_Sales_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Article_Sales_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "data";
    private static final String ARG_PARAM2 = "param2";
    String rcvData[] = new String[8];
    Button notify;
    Button close;
    FragmentManager fm;
    Context con;
    EditText article_et;
    EditText ean_et;
    EditText td_sale_q;
    EditText mtd_sale_q;
    EditText ytd_sale_q;
    EditText td_sale_v;
    EditText mtd_sale_v;
    EditText ytd_sale_v;
    EditText six_et;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = Article_Sales_Fragment.class.getName();
    private OnFragmentInteractionListener mListener;

    public Article_Sales_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OutWardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Article_Sales_Fragment newInstance(String param1, String param2) {
        Article_Sales_Fragment fragment = new Article_Sales_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Article Detail");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {



              rcvData[7]= getArguments().getString("IM_EAN");
              rcvData[6]= getArguments().getString("MATNR");
              rcvData[5]= getArguments().getString("YTD_NVAL");
              rcvData[4]= getArguments().getString("YTD_QTY");
              rcvData[3]= getArguments().getString("TD_QTY" );
              rcvData[2]= getArguments().getString("TD_NVAL" );
              rcvData[1]= getArguments().getString("MTD_NVAL" );
              rcvData[0]= getArguments().getString("MTD_QTY" );

         
        }
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_qty_enquiry, container, false);
        con = getContext();

         td_sale_q = (EditText) view.findViewById(R.id.tdSaleq);
        td_sale_v = (EditText) view.findViewById(R.id.tdSalev);
        mtd_sale_q = (EditText) view.findViewById(R.id.mtdSaleq);
        mtd_sale_v = (EditText) view.findViewById(R.id.mtdSalev);
        ytd_sale_q = (EditText) view.findViewById(R.id.ytdSaleq);
        ytd_sale_v = (EditText) view.findViewById(R.id.ytdSalev);

        article_et = (EditText) view.findViewById(R.id.article_et);
        ean_et = (EditText) view.findViewById(R.id.ean_et);


        close = (Button) view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStack();
            }
        });
       setData();
        return view;
    }


    private void setData() {

        if (rcvData != null && rcvData.length==8) {


            ytd_sale_v.setText(rcvData[5]);
            ytd_sale_q.setText(rcvData[4]);
            td_sale_v.setText(rcvData[2]);
            td_sale_q.setText(rcvData[3]);
            mtd_sale_v.setText(rcvData[1]);
            mtd_sale_q.setText(rcvData[0]);
            ean_et.setText(rcvData[7]);
            article_et.setText(rcvData[6].replaceFirst("^0+(?!$)", ""));

        } else {
            Log.d(TAG, "NO DATA RECEIVED!!!");
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        //fm.popBackStackImmediate();
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
