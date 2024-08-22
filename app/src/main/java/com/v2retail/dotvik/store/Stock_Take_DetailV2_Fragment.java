package com.v2retail.dotvik.store;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.TextView;

import com.v2retail.dotvik.R;
import com.v2retail.util.AlertBox;
import com.v2retail.util.SharedPreferencesData;
import com.v2retail.util.Tables;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Stock_Take_DetailV2_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Stock_Take_DetailV2_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Stock_Take_DetailV2_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "bin_list";
    private static final String ARG_PARAM2 = "stockid";
    private static final String TAG = Stock_Take_DetailV2_Fragment.class.getName();

    // TODO: Rename and change types of parameters
    private ArrayList<String> mParam1;
    private String mParam2;

    Tables tables = new Tables();
    Context con;
    FragmentManager fm;
    Button next;
    Button back;
//    Button stock_take_scan;
    String URL="";
    String WERKS="";
    String USER="";
    ProgressDialog dialog;
    AlertBox box;
    TextView mResponseView;
    EditText stock_take_id;
    EditText storage_id;
    EditText bin_from;
    EditText site;
    EditText warehouse_no;
    EditText bin_to;


    String requester = "";
    String scanner = "";
    String stockID = "";

    ArrayList<ArrayList<String>> dtstocktakeBin;
    ArrayList<ArrayList<String>> dtStockTakeDetails;
    ArrayList<ArrayList<String>> dtStockBinPO;
    ArrayList<ArrayList<String>> dtCrate;
    ArrayList<String> data;
    ArrayList<String> dtBin;
    private OnFragmentInteractionListener mListener;

    public Stock_Take_DetailV2_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Stock Take  ");
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
    public static Stock_Take_DetailV2_Fragment newInstance(String param1, String param2) {
        Stock_Take_DetailV2_Fragment fragment = new Stock_Take_DetailV2_Fragment();
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
            dtBin = (ArrayList<String>) getArguments().getSerializable(ARG_PARAM1);
            stockID =  getArguments().getString(ARG_PARAM2);
        }

        fm = getFragmentManager();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_take_dc, container, false);

        con = getContext();
        box = new AlertBox(con);
        dialog = new ProgressDialog(con);
        SharedPreferencesData data=new SharedPreferencesData(con);
        URL=data.read("URL");
        WERKS=data.read("WERKS");
        USER=data.read("USER");
        if(!URL.isEmpty())
            Log.d(TAG,"URL->"+URL);
        if(!WERKS.isEmpty())
            Log.d(TAG,"WERKS->"+WERKS);
        if(!USER.isEmpty())
            Log.d(TAG,"USER->"+USER);

        back = (Button) view.findViewById(R.id.back);
//        stock_take_scan = (Button) view.findViewById(R.id.stock_take_scan);
        next = (Button) view.findViewById(R.id.next);
        stock_take_id = (EditText) view.findViewById(R.id.stock_id);
        storage_id = (EditText) view.findViewById(R.id.storage_type);
        bin_from = (EditText) view.findViewById(R.id.bin_from);
        bin_to = (EditText) view.findViewById(R.id.bin_to);
        site = (EditText) view.findViewById(R.id.site);
        warehouse_no = (EditText) view.findViewById(R.id.warehouse_no);



        back.setOnClickListener(this);
        next.setOnClickListener(this);
//        stock_take_scan.setVisibility(View.GONE);



        loadData();
        return view;
    }

    private void loadData() {

        dtStockBinPO=tables.getStockBinPOTable();
        if(dtBin!=null)
        {
            for(int i=0;i<=dtBin.size()-12;)
            {
                dtStockBinPO.get(0).add(dtBin.get(i));
                dtStockBinPO.get(1).add(dtBin.get(i + 1));
                dtStockBinPO.get(2).add(dtBin.get(i + 2));//bin
                dtStockBinPO.get(3).add(dtBin.get(i + 3));
                dtStockBinPO.get(4).add(dtBin.get(i + 4));
                dtStockBinPO.get(5).add(dtBin.get(i + 5));
                i += 12;
            }
        }
        stock_take_id.setText(stockID);
        String low=  dtStockBinPO.get(2).get(0);
        String high=  dtStockBinPO.get(2).get(dtStockBinPO.get(2).size()-1);
        bin_from.setText(low);
        bin_to.setText(high);
        site.setText( dtStockBinPO.get(3).get(0));
        warehouse_no.setText( dtStockBinPO.get(4).get(0));
        storage_id.setText( dtStockBinPO.get(5).get(0));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())

        {
            case R.id.next:


                try {
                    nextScreen();
                }catch (Exception e)
                {
                    box.getErrBox(e);
                }


                break;

            case R.id.back:

                fm.popBackStack();
                break;

//            case R.id.stock_take_scan:
//                stock_take_id.setText("");
//                scanner = "bar";
//                if(CameraCheck.isCameraAvailable(con))
//                    IntentIntegrator.forSupportFragment(Stock_Take_DetailV2_Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();
//
//                break;


        }
    }

    private void nextScreen() {
        String stocktakeId=stock_take_id.getText().toString().trim();
        String siteno=site.getText().toString().trim();
        String warehouse=warehouse_no.getText().toString().trim();
        data=new ArrayList<>();
        if(!stocktakeId.equals("")&& !siteno.equals("")&& !warehouse.equals(""))
        {
            data.add(stocktakeId);
            data.add(siteno);
            data.add(warehouse);
            data.add(storage_id.getText().toString().trim());

        }
        else {
            box.getBox("Alert","Please Enter All The details Before Proceeding");
            return;
        }

        Bundle args=new Bundle();
        args.putString("stockid", stockID);
        args.putSerializable("dtStockBinPO", dtStockBinPO);
        Fragment fragment = new Scan_Stock_take_V2Fragment();
        fragment.setArguments(args);
        clear();
        if (fragment != null) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "Scan_StockV2");
            ft.addToBackStack("Scan_StockV2");
            ft.commit();

        }

    }

    private void clear() {
        stock_take_id.setText("");
        storage_id.setText("");
        bin_to.setText("");
        bin_from.setText("");
        site.setText("");
        warehouse_no.setText("");
    }












    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        fm.popBackStack();
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
