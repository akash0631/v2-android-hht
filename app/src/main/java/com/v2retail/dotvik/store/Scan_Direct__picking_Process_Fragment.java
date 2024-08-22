package com.v2retail.dotvik.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.v2retail.ApplicationController;
import com.v2retail.dotvik.R;
import com.v2retail.util.AlertBox;
import com.v2retail.util.CameraCheck;
import com.v2retail.util.SharedPreferencesData;
import com.v2retail.util.Tables;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Scan_Direct__picking_Process_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Scan_Direct__picking_Process_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Scan_Direct__picking_Process_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "bin_list";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    int ScanQty = 0;
    int OpenQty = 0;
    int ScQty = 0;
    int sum = 0;
    ArrayList<Integer> rows_index = new ArrayList<>();
    private String mParam2;
    String[] arrBarQty = null;
    String barcode = "";
    FragmentManager fm;
    Button save;
    Button back;
    Tables tables = new Tables();
    Context con;
    AlertBox box;
    ProgressDialog dialog;
    EditText date;
    Button barcode_scan;
    Button bin_scan;
    String TAG = Scan_Direct__picking_Process_Fragment.class.getName();
    String URL = "";
    String WERKS = "";
    String USER = "";
    TextView mResponseView;
    EditText article_available_stock_et;
    EditText total_sq_et;
    EditText bin_et;
    EditText barcode_art_et;
    EditText article_no_et;
    EditText asq_et;
    EditText tsq_et;

    ArrayList<ArrayList<String>> rows1;
    String requester = "";
    String scanner = "";

    ArrayList<ArrayList<String>> dtPO;
    ArrayList<ArrayList<String>> dtEAN;
    ArrayList<String> dtBin;
    ArrayList<String> mParam1;
    ArrayList<ArrayList<String>> dtMaterial;
    private OnFragmentInteractionListener mListener;

    public Scan_Direct__picking_Process_Fragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Scan Direct Picking");
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
    public static Scan_Direct__picking_Process_Fragment newInstance(String param1, String param2) {
        Scan_Direct__picking_Process_Fragment fragment = new Scan_Direct__picking_Process_Fragment();
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
            mParam1 = (ArrayList<String>) getArguments().getSerializable(ARG_PARAM1);
            Log.d(TAG, "bin array data :" + mParam1);

            // mParam2 = getArguments().getString(ARG_PARAM2);
        }
        fm = getFragmentManager();

    }

    private void loadData() {
        if (dtEAN != null) {
            dtEAN = null;

        }

        if (dtPO != null) {
            dtPO = null;

        }
        if (dtBin != null) {
            dtBin = null;

        }
        if (mParam1 != null) {
            dtBin = mParam1;
        } else {

            dtBin = tables.getBINTAble();
        }
        dtPO = tables.getPOTAble("direct");


        dtEAN = tables.getEANTAble("");

        dtMaterial = tables.getMATTAble();

        rows1 = tables.getEANTAble("");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_direct_picking, container, false);
        con = getContext();
        dialog = new ProgressDialog(con);
        box = new AlertBox(con);

        SharedPreferencesData data = new SharedPreferencesData(con);
        URL = data.read("URL");
        WERKS = data.read("WERKS");
        USER = data.read("USER");
        if (!URL.isEmpty())
            Log.d(TAG, "URL->" + URL);
        if (!WERKS.isEmpty())
            Log.d(TAG, "WERKS->" + WERKS);
        if (!USER.isEmpty())
            Log.d(TAG, "USER->" + USER);

        back = (Button) view.findViewById(R.id.back);
        barcode_scan = (Button) view.findViewById(R.id.barcode_scan);
        save = (Button) view.findViewById(R.id.save);
        bin_scan = (Button) view.findViewById(R.id.bin_scan);

        total_sq_et = (EditText) view.findViewById(R.id.total_scan_qty);
        bin_et = (EditText) view.findViewById(R.id.bin_no);
        article_no_et = (EditText) view.findViewById(R.id.article_no);
        tsq_et = (EditText) view.findViewById(R.id.tsq);
        asq_et = (EditText) view.findViewById(R.id.asq);
        article_available_stock_et = (EditText) view.findViewById(R.id.article_available_stock);
        barcode_art_et = (EditText) view.findViewById(R.id.barcode_no);
        mResponseView = (TextView) view.findViewById(R.id.response);

        back.setOnClickListener(this);
        barcode_scan.setOnClickListener(this);
        bin_scan.setOnClickListener(this);
        save.setOnClickListener(this);
        barcode_art_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    /*|| (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)*/) {

                    if (!TextUtils.isEmpty(bin_et.getText().toString().trim()) ) {
                        try {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(barcode_art_et.getWindowToken(), 0);
                            setFormData();

                        } catch (Exception e) {
                            box.getErrBox(e);
                            return true;
                        }


                    } else {

                        box.getBox("Alert!!", "First Scan Bar Number");

                        barcode_art_et.requestFocus();
                        return true;
                    }

                }
                return false;
            }
        });

        bin_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    /*|| (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)*/) {

                    String bin = bin_et.getText().toString().trim();
                    if (dtBin.size() > 0) {
                        if (!dtBin.contains(bin)) {

                            box.getBox("Alert", "Invalid Bin Number!");
                            bin_et.requestFocus();
                            return true;
                        } else {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(bin_et.getWindowToken(), 0);

                            barcode_art_et.requestFocus();
                            return true;
                        }
                    } else {

                        box.getBox("Alert", "Bin data not available!");
                        fm.popBackStack();

                    }


                }
                return false;
            }
        });

        try {
            loadData();
        } catch (Exception e) {
            box.getErrBox(e);
        }
        return view;
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


    @Override
    public void onClick(View view) {
        switch (view.getId())

        {
            case R.id.save:


                try {
                    saveDataToServer();
                } catch (Exception e) {
                    box.getErrBox(e);
                }


                break;

            case R.id.back:

                fm.popBackStack();
                break;

            case R.id.barcode_scan:

                barcode_art_et.setText("");

                scanner = "bar";
                if(CameraCheck.isCameraAvailable(con))
                    IntentIntegrator.forSupportFragment(Scan_Direct__picking_Process_Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();

                break;
            case R.id.bin_scan:


                bin_et.setText("");
                barcode_art_et.setText("");
                scanner = "bin";
                if(CameraCheck.isCameraAvailable(con))
                    IntentIntegrator.forSupportFragment(Scan_Direct__picking_Process_Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult == null) {

            Log.d(TAG, TAG + " scanned result...");
            box.getBox("Scanner Err", "Unable to receive Data");


        } else {
            Log.d(TAG, "Scan data received");
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            Log.v(TAG, "scanContent = " + scanContent);
            Log.v(TAG, "scanFormat = " + scanFormat);

            if (scanContent != null) {
                switch (scanner)

                {
                    case "bar":

                        if (!(bin_et.equals("") || bin_et.length() < 0 || bin_et.equals(null))) {
                            barcode_art_et.setText(scanContent);
                            //bar_scan.setText("Rescan");
                            Log.v(TAG, "bar code = " + scanContent);
                            // Log.v(TAG,"stored bar code = " + barcode_art);

                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(barcode_art_et.getWindowToken(), 0);

                            try {
                                setFormData();
                            } catch (Exception e) {
                                box.getErrBox(e);
                            }

                        } else {

                            box.getBox("Alert!!", "First Scan Bar Number");
                        }

                        break;
                    case "bin":


                        if (dtBin.contains(scanContent)) {
                            bin_et.setText(scanContent);
                            barcode_art_et.requestFocus();
                        } else {

                            box.getBox("Alert", "Incorrect Bin!");
                        }
                        Log.v(TAG, "bin code = " + scanContent);


                        break;
                }

            } else {
                box.getBox("Scanner Err", "No Content Received. Please Scan Again");

            }
        }


    }

    private void setFormData() {

        barcode = barcode_art_et.getText().toString();
        if (barcode.equals("") || barcode == null || barcode.length() < 0) {

            box.getBox("Alert", "First Scan Barcode");
            return;
        }
        arrBarQty = barcode.split("-");
        rows1 = getEANTableData(arrBarQty);
        if (rows1.get(0).size() > 0) {
            try {
                processData(arrBarQty);
            } catch (Exception e) {
                box.getErrBox(e);
            }
        } else {


            try {
                getdataMat();
            } catch (Exception e) {
                box.getErrBox(e);
            }

        }


    }

    private void getdataMat() {
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String addrec = null;

                addrec = WERKS + "#" + bin_et.getText().toString() + "#" + barcode_art_et.getText().toString();
                requester = "getMat";
                String valueRequestPayload = "getmatbinstock#" + addrec + "#<eol>";
                Log.d(TAG, "payload-> " + valueRequestPayload);
                sendAndRequestResponse(valueRequestPayload);
            }
        }, 2000);


    }

    private void sendAndRequestResponse(final String requestBody) {
        final RequestQueue mRequestQueue;
        StringRequest mStringRequest;

        //RequestQueue initialized
        mRequestQueue = ApplicationController.getInstance().getRequestQueue();

        mStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                response = response.substring(9, response.length());

                Log.d(TAG, "response ->" + response);

                if (response == null) {
                    dialog.dismiss();
                    box.getBox("Err", "No response from Server");

                } else if (response.equals("")) {
                    dialog.dismiss();
                    box.getBox("Err", "Empty response from Server/ Unable to connect");

                } else if (response.charAt(0) == ('S')) {
                    if (requester.equals("getMat")) {
                        loadDataFromServer(response);

                    } else if (requester.equals("save")) {
                        bin_et.setText("");
                        article_no_et.setText("");
                        barcode_art_et.setText("");
                        article_available_stock_et.setText("");
                        tsq_et.setText("");
                        asq_et.setText("");
                        total_sq_et.setText("");


                        dialog.dismiss();
                        box.getBox("Message", response.substring(2, response.length()));

                        dtMaterial.clear();
                        dtEAN.clear();
                        loadData();


                    }
                    Log.d("tag", "  response ->" + response);

                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);

                    response = response.substring(2, response.length());
                    dialog.dismiss();
                    box.getBox("Error", response);
                    if (requester.equals("getMat")) {

                        barcode_art_et.setText("");
                        barcode_art_et.requestFocus();

                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
                String err = "";

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    err = "Communication Error!";

                } else if (error instanceof AuthFailureError) {
                    err = "Authentication Error!";
                } else if (error instanceof ServerError) {
                    err = "Server Side Error!";
                } else if (error instanceof NetworkError) {
                    err = "Network Error!";
                } else if (error instanceof ParseError) {
                    err = "Parse Error!";
                } else err = error.toString();

                dialog.dismiss();
                box.getBox("Err", err);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                Response<String> res = super.parseNetworkResponse(response);
                String data = res.result;
                Log.i(TAG, data);

                return res;
            }


        };
        mStringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        mRequestQueue.add(mStringRequest);

    }

    private void loadDataFromServer(String resp) {
//Response:
//
        //S#000000001130033500#30.000#0.000#0.000#00-1603A4#!
// 300#000000001130033500#19074363#1##300#000000001130033500#19075652#15##300#000000001130033500#19075653#15##
        Log.d(TAG, "loading Server data :");
        dialog.dismiss();
        resp = resp.substring(2, resp.length());
        String arrayRcvdData[] = resp.split("!");
        String[] arrayPOData = arrayRcvdData[0].split("#");
        String[] eanData = arrayRcvdData[1].split("##");


        //testing purpose
        List<String> data_list = Arrays.asList(arrayPOData);
        Log.d(TAG, "po data :" + data_list);

        data_list = Arrays.asList(eanData);
        Log.d(TAG, "ean data :" + data_list);


        for (int lk = 0; lk <= arrayPOData.length - 5; lk++) {

            dtPO.get(0).add(arrayPOData[lk]);
            dtPO.get(1).add(arrayPOData[lk + 1]);
            dtPO.get(2).add(arrayPOData[lk + 2]);
            dtPO.get(3).add(arrayPOData[lk + 3]);
            dtPO.get(4).add(arrayPOData[lk + 4]);
            lk = lk + 5;
        }


        for (int lk = 0; lk < eanData.length; lk++) {
            String ar[] = eanData[lk].split("#");

            for (int i = 0, j = 0; j < ar.length; i++, j++) {
                dtEAN.get(i).add(ar[j]);
            }

        }
        Log.d(TAG, "DTean :" + dtEAN);
        Log.d(TAG, "DTPO :" + dtPO);
        Log.d(TAG, "Status :" + "Data Loaded Successfully");

        try {
            processData(arrBarQty);
        } catch (Exception e) {
            box.getErrBox(e);
        }

    }

    private void saveDataToServer() {

        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // for version 2 API, we need to pass picklist number,
                // but direct picking do not have picklist number,
                // so we need to pass empty. Pradeep
                String picklistNumber = "";
                String msg = null;
                msg = WERKS + "," + USER;
                int i = 0, j = 0;
                int s = dtMaterial.size();
                int size = dtMaterial.get(0).size();
                StringBuilder builder = new StringBuilder();
                while (size > i) {
                    j = 0;
                    while (s > j) {
                        builder.append("," + dtMaterial.get(j).get(i));
                        j++;
                    }
                    builder.append("," + picklistNumber );
                    i++;
                }

                msg = msg + builder.toString();
                String addrec = null;

                addrec = msg;
                requester = "save";
                String valueRequestPayload = "savedirectpicking_v2#" + addrec + "#<eol>";
                Log.d(TAG, "Payload-> " + valueRequestPayload);
                sendAndRequestResponse(valueRequestPayload);
            }
        }, 1000);


    }

    private void processData(String[] arrBarQty) {
        String EANNR = null;
        String Str55 = null;
        String EAMMAterial = null;

        int i = 0;
        int ind = 0;
        EANNR = null;
        rows1 = getEANTableData(arrBarQty);
        if (rows1.get(2).size() == 0) {
            Log.d(TAG, "No data found");

            return;
        }
        //  rows1=dtEAN;
        Log.d(TAG, "rows1 array data :" + rows1);
        EAMMAterial = rows1.get(1).get(0);
        //   ScanQty = Integer.valueOf(rows1.get(2).get(0));
        ScanQty = Integer.valueOf(rows1.get(3).get(0));
        article_no_et.setText(EAMMAterial.replaceFirst("^0+(?!$)", ""));

        //arrBarQty = txt_Article.Text.split('-');
        if (arrBarQty.length > 1)
            ScanQty = Integer.valueOf(arrBarQty[1]);


        ArrayList<ArrayList<String>> rowsPO = getPOTableData(EAMMAterial);
        if (rowsPO.get(0).size() > 0) {
            article_available_stock_et.setText(rowsPO.get(1).get(0));
        } else {
            box.getBox("Err", "No Article Found!");
            Log.d(TAG, "NO data found for dtpo(matnr == eammaterial)");
            return;
        }

        OpenQty = (int) Math.floor(Double.valueOf(article_available_stock_et.getText().toString()));
        Str55 = EAMMAterial + "," + ScanQty + "," + bin_et.getText().toString();

        int sumqty = 0;
        if (dtMaterial != null)
            if (dtMaterial.get(0).size() > 0) {
                ArrayList<ArrayList<String>> rowsMAT = tables.getMATTAble();

                if (dtMaterial.get(0).contains(EAMMAterial)) {
                    rowsMAT = getMatTableData(EAMMAterial);
                }

                if (rowsMAT.get(0).size() > 0) {

                    i = 0;


                    while (rows_index.size() > i) {
                        //adding row data
                        ind = rows_index.get(i);
                        if (rowsMAT.get(0).size() > 0) {
                            sumqty += Integer.valueOf(dtMaterial.get(1).get(ind));

                        }
                        i++;
                    }
                    sum = sumqty;
                } else {
                    sum = 0;
                }
            } else {
                sum = 0;
            }


        rows_index.clear();
        sum = sum + ScanQty;

        if (sum <= OpenQty) {
            ScQty = ScQty + ScanQty;
            asq_et.setText(String.valueOf(sum));
            dtMaterial.get(0).add(EAMMAterial);
            dtMaterial.get(1).add(String.valueOf(ScanQty));
            dtMaterial.get(2).add(bin_et.getText().toString());

            total_sq_et.setText(String.valueOf(ScQty));
            if (sum == 0) {
                tsq_et.setText(String.valueOf(ScanQty));
            } else {
                tsq_et.setText(String.valueOf(sum));
            }


            barcode_art_et.setText("");
            //
            // rowsPO(0)("OPEN_QTY") = Convert.ToInt32(txt_TO.Text) - Convert.ToInt32(ScanQty)
            //
            barcode_art_et.requestFocus();
        } else {

            box.getBox("Alert", "Scanned Qty can't be greater than Open Qty");
            article_no_et.setText("");

        }
    }

    private ArrayList<ArrayList<String>> getPOTableData(String EAMMAterial) {
        ArrayList<ArrayList<String>> rowsPO = tables.getPOTAble("");
        int i = 0;
        rows_index.clear();
        String bin_no = bin_et.getText().toString();

        while (dtPO.get(0).size() > i) {
            if (dtPO.get(0).get(i).contains(EAMMAterial)) {
                //getting index of rows that contains matnr=EAMMAterial
                if (dtPO.get(4).get(i).contains(bin_no))
                    rows_index.add(i);

            }
            i++;
        }

        i = 0;
        int ind = 0;
        Log.d(TAG, " matnr=EAMMAterial Dtpo index:" + rows_index);
        if (rows_index.size() > 0)
            while (rows_index.size() > i) {
                //adding row data
                ind = rows_index.get(i);
                if (rowsPO.size() < 4) {
                    rowsPO.get(0).add(dtPO.get(0).get(ind));
                    rowsPO.get(1).add(dtPO.get(1).get(ind));
                    rowsPO.get(2).add(dtPO.get(2).get(ind));

                }
                i++;
            }

        Log.d(TAG, " matnr=EAMMAterial Dtpo  :" + rowsPO);
        return rowsPO;
    }

    private ArrayList<ArrayList<String>> getMatTableData(String EAMMAterial) {
        ArrayList<ArrayList<String>> rowsMAT = tables.getMATTAble();
        int i = 0;
        rows_index.clear();
        while (dtMaterial.get(0).size() > i) {
            if (dtMaterial.get(0).get(i).contains(EAMMAterial)) {//getting index of rows that contains matnr=EAMMAterial
                rows_index.add(i);
            }
            i++;
        }
        i = 0;
        int ind = 0;
        if (rows_index.size() > 0)
            while (rows_index.size() > i) {
                //adding row data
                ind = rows_index.get(i);
                if (rowsMAT.size() < 4) {
                    rowsMAT.get(0).add(dtMaterial.get(0).get(ind));
                    rowsMAT.get(1).add(dtMaterial.get(1).get(ind));
                    rowsMAT.get(2).add(dtMaterial.get(2).get(ind));

                }
                i++;
            }

        Log.d(TAG, " matnr=EAMMAterial Dtpo  :" + rowsMAT);
        return rowsMAT;
    }

    private ArrayList<ArrayList<String>> getEANTableData(String[] arrBarQty) {
        ArrayList<ArrayList<String>> rows = tables.getEANTAble("");
        int i = 0;
        //   i=dtEAN.get(2).size();
        i = 0;
        if (arrBarQty != null)
            if (dtEAN.size() > 4)
                while (dtEAN.get(2).size() > i) {
                    if (dtEAN.get(2).get(i).contains(arrBarQty[0])) {//getting index of row that contains ean11=arrbarqty[0]
                        rows_index.add(i);
                    }
                    i++;
                }
        Log.d(TAG, " ean11=arrbarqty[0] Dtean index: " + rows_index);
        i = 0;

        int ind = 0;
        if ((rows_index.size() > 0))
            while (rows_index.size() > i) {
                //adding row data
                ind = rows_index.get(i);
                if (dtEAN.size() > 5) {
                    rows.get(0).add(dtEAN.get(0).get(ind));
                    rows.get(1).add(dtEAN.get(1).get(ind));
                    rows.get(2).add(dtEAN.get(2).get(ind));
                    rows.get(3).add(dtEAN.get(3).get(ind));
                    rows.get(4).add(dtEAN.get(4).get(ind));
                    i += 5;
                } else if (dtEAN.size() > 4) {
                    rows.get(0).add(dtEAN.get(0).get(ind));
                    rows.get(1).add(dtEAN.get(1).get(ind));
                    rows.get(2).add(dtEAN.get(2).get(ind));
                    rows.get(3).add(dtEAN.get(3).get(ind));
                    i += 4;
                } else i++;
            }
        Log.d(TAG, " ean11=arrbarqty[0] Dtean  : " + rows);
        return rows;

    }
}
