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
import android.widget.TextView;

import com.v2retail.dotvik.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Article_Detail_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Article_Detail_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Article_Detail_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "data";
    private static final String ARG_PARAM2 = "param2";
    String rcvData[] = new String[14];
    Button notify;
    Button close;
    FragmentManager fm;
    Context con;
    EditText article_et;
    EditText ean_et;
    EditText vo1_et;
    EditText total_et;
    EditText one_et;
    EditText four_et;
    EditText three_et;
    EditText five_et;
    EditText six_et;
    EditText seven_et;
    EditText eight_et;
    EditText msa_et;
    EditText nine_et;
    EditText ten_et;
    TextView mResponseView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = Article_Detail_Fragment.class.getName();
    private OnFragmentInteractionListener mListener;

    public Article_Detail_Fragment() {
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
    public static Article_Detail_Fragment newInstance(String param1, String param2) {
        Article_Detail_Fragment fragment = new Article_Detail_Fragment();
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

              rcvData[12]= getArguments().getString("IM_EAN");
              rcvData[13]= getArguments().getString("MATNR");
              rcvData[11]= getArguments().getString("V04");
              rcvData[0]= getArguments().getString("V01");
              rcvData[1]= getArguments().getString("MSA" );
              rcvData[2]= getArguments().getString("0001" );
              rcvData[3]= getArguments().getString("0003" );
              rcvData[4]= getArguments().getString("0004" );
              rcvData[5]= getArguments().getString("0005" );
              rcvData[6]= getArguments().getString("0006" );
              rcvData[7]= getArguments().getString("0007" );
              rcvData[8]= getArguments().getString("0008" );
              rcvData[9]= getArguments().getString("0009" );
              rcvData[10]= getArguments().getString("0010" );

         
        }
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_qty, container, false);
        con = getContext();

        vo1_et = (EditText) view.findViewById(R.id.vo1_et);
        total_et = (EditText) view.findViewById(R.id.total_et);
        one_et = (EditText) view.findViewById(R.id.one_et);
        three_et = (EditText) view.findViewById(R.id.three_et);
        four_et = (EditText) view.findViewById(R.id.four_et);
        five_et = (EditText) view.findViewById(R.id.five_et);
        six_et = (EditText) view.findViewById(R.id.six_et);
        seven_et = (EditText) view.findViewById(R.id.seven_et);
        eight_et = (EditText) view.findViewById(R.id.eight_et);
        msa_et = (EditText) view.findViewById(R.id.msa_et);
        article_et = (EditText) view.findViewById(R.id.article_et);
        nine_et = (EditText) view.findViewById(R.id.nine_et);
        ten_et = (EditText) view.findViewById(R.id.ten_et);
        ean_et = (EditText) view.findViewById(R.id.ean_et);

        mResponseView = (TextView) view.findViewById(R.id.response);

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

        if (rcvData != null) {
            int temp=0;
            for(int i=0;i<12;i++)
            {
                temp+=Double.valueOf(rcvData[i]).intValue();

            }
            total_et.setText(String.valueOf(temp));

            vo1_et.setText(rcvData[0]);
            msa_et.setText(rcvData[1]);
            one_et.setText(rcvData[2]);
            three_et.setText(rcvData[3]);
            four_et.setText(rcvData[4]);
            five_et.setText(rcvData[5]);
            six_et.setText(rcvData[6]);
            seven_et.setText(rcvData[7]);
            eight_et.setText(rcvData[8]);
            nine_et.setText(rcvData[9]);
            ten_et.setText(rcvData[10]);
            ean_et.setText(rcvData[12]);
            article_et.setText(rcvData[13].replaceFirst("^0+(?!$)", ""));
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
