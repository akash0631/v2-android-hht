package com.v2retail.dotvik.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.v2retail.commons.UIFuncs;
import com.v2retail.dotvik.R;
import com.v2retail.util.AlertBox;
import com.v2retail.util.CameraCheck;
import com.v2retail.util.SharedPreferencesData;
import com.v2retail.util.Tables;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Scan_Picking_against_picking_Process_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Scan_Picking_against_picking_Process_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Scan_Picking_against_picking_Process_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "po_list";
    private static final String ARG_PARAM2 = "ean_list";
    private static final String ARG_PARAM3 = "picklist_number";

    // TODO: Rename and change types of parameters
    private String[] mParam1;
    ArrayList<ArrayList<String>> rows1;
    ArrayList<Integer> rows_index = new ArrayList<>();
    private String[] mParam2;
    private String scanner;
    private String TAG = Scan_Picking_against_picking_Process_Fragment.class.getName();

    Tables tables = new Tables();
    AlertBox box;
    Context con;
    ProgressDialog dialog;
    EditText date;
    FragmentManager fm;
    Button save;
    Button back;
    Button barcode_scan;
    Button bin_scan;
    String URL = "";
    String WERKS = "";
    String USER = "";
    String picklistNumber = "";

    TextView mResponseView;
    Spinner pack_mat_spinner;
    EditText total_sq_et;
    EditText bin_et;
    EditText article_available_stock_et;
    EditText barcode_art_et;
    EditText article_no_et;
    EditText asq_et;
    EditText tsq_et;
    int ScQty = 0;
    int ScanQty = 0;
    int OpenQty = 0;
    int sum = 0;
    String[] arrBarQty;
    String requester = "";

    ArrayList<ArrayList<String>> dtPO;
    ArrayList<ArrayList<String>> dtEAN;
    ArrayList<ArrayList<String>> dtPacMat;
    ArrayList<String> dtBin;
    ArrayList<ArrayList<String>> dtMaterial;
    private OnFragmentInteractionListener mListener;

    public Scan_Picking_against_picking_Process_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Scan Picking Against Picking  ");
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
    public static Scan_Picking_against_picking_Process_Fragment newInstance(String param1, String param2) {
        Scan_Picking_against_picking_Process_Fragment fragment = new Scan_Picking_against_picking_Process_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, "");
        fragment.setArguments(args);
        return fragment;
    }

    public static Scan_Picking_against_picking_Process_Fragment newInstance(String param1, String param2, String picklistNumber) {
        Scan_Picking_against_picking_Process_Fragment fragment = new Scan_Picking_against_picking_Process_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, picklistNumber);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            dtPO = (ArrayList<ArrayList<String>>) getArguments().getSerializable(ARG_PARAM1);
            dtEAN = (ArrayList<ArrayList<String>>) getArguments().getSerializable(ARG_PARAM2);
            picklistNumber = getArguments().getString(ARG_PARAM3);

            if (dtPO != null) {
                Log.d(TAG, "dtpo :" + dtPO);
            }

            if (dtEAN != null) {
                Log.d(TAG, "dtEAN :" + dtEAN);
            }

        }
        fm = getFragmentManager();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_direct_picking, container, false);

        con = getContext();
        box = new AlertBox(con);
        dialog = new ProgressDialog(con);
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
        UIFuncs.disableKeyInput(bin_et, view, con);
        article_no_et = (EditText) view.findViewById(R.id.article_no);
        tsq_et = (EditText) view.findViewById(R.id.tsq);
        asq_et = (EditText) view.findViewById(R.id.asq);
        barcode_art_et = (EditText) view.findViewById(R.id.barcode_no);
        UIFuncs.disableKeyInput(barcode_art_et, view, con);
        article_available_stock_et = (EditText) view.findViewById(R.id.article_available_stock);
        mResponseView = (TextView) view.findViewById(R.id.response);
        barcode_art_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(barcode_art_et.getWindowToken(), 0);
                        setFormData();
                        barcode_art_et.requestFocus();
                    } catch (Exception e) {

                        box.getErrBox(e);
                    }
                }
                return false;
            }
        });
        barcode_art_et.addTextChangedListener(new TextWatcher() {
            boolean scannerReading = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if( (before==0 && start ==0) && count > 6) {
                    scannerReading = true;
                } else {
                    scannerReading = false;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                String barcode = s.toString().toUpperCase();
                if(barcode.length()>0 && scannerReading) {
                    setFormData();
                    barcode_art_et.requestFocus();
                }
            }
        });
        bin_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    /*|| (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)*/) {

                    String bin = bin_et.getText().toString();
                    if (bin == null || bin.length() < 0 || bin.equals("")) {

                        box.getBox("Alert", "Enter Bin No!");
                        return true;
                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(bin_et.getWindowToken(), 0);

                        barcode_art_et.requestFocus();
                        return true;
                    }
                }
                return false;
            }
        });
        bin_et.addTextChangedListener(new TextWatcher() {
            boolean scannerReading = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if( (before==0 && start ==0) && count > 6) {
                    scannerReading = true;
                } else {
                    scannerReading = false;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                String bin = s.toString().toUpperCase();
                if(bin.length()>0 && scannerReading) {
                    barcode_art_et.requestFocus();
                }
            }
        });


        back.setOnClickListener(this);
        barcode_scan.setOnClickListener(this);
        bin_scan.setOnClickListener(this);
        save.setOnClickListener(this);

        bin_et.requestFocus();
        try {
            LoadData();

        } catch (Exception e) {
            box.getErrBox(e);
        }
        return view;
    }

    void LoadData() {

        if (dtBin != null) {
            dtBin = null;

        }

        dtBin = tables.getBINTAble();
        dtMaterial = tables.getMATTAble();


    }

    private void networkCall() {
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    saveDataToServer();
                } catch (Exception e) {
                    dialog.dismiss();
                    box.getErrBox(e);
                }
            }
        }, 2000);
    }

    private void saveDataToServer() {
        String msg = "";
        msg = WERKS + "," + USER;
        int i = 0, j = 0;

        StringBuilder Stuz = new StringBuilder();
        if (dtMaterial.get(0).size() < 0) {
            dialog.dismiss();
            box.getBox("Alert", "No Material Scanned! Please Scan SOme MAterial First!");
            return;
        }
        int len = dtMaterial.get(0).size();
        int lenItem = dtMaterial.size();
        while (len > i) {
            j = 0;
            while (lenItem > j) {

                Stuz.append("," + dtMaterial.get(j).get(i));
                j++;
            }
            Stuz.append("," + picklistNumber);
            i++;
        }
        msg = msg + Stuz.toString();
        String addrec = msg;
        String valueRequestPayload = "savedirectpicking_v2#" + addrec + "#<eol>";
        Log.d(TAG, "payload ->" + valueRequestPayload);
        sendAndRequestResponse(valueRequestPayload);


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
                dialog.dismiss();
                if (response == null) {

                    box.getBox("Err", "No response from Server");

                } else if (response.equals("")) {
                    Log.d(TAG, " Response is Failure -> " + response);


                    box.getBox("Err", "Unable to Connect Server/ Empty Response");


                } else if (response.charAt(0) == ('S')) {

                    bin_et.setText("");
                    article_no_et.setText("");
                    barcode_art_et.setText("");
                    tsq_et.setText("");
                    asq_et.setText("");
                    total_sq_et.setText("");
                    article_available_stock_et.setText("");
                    dtMaterial.clear();
                    dialog.dismiss();
                    box.getBox("Response", response);
                    dtMaterial = null;
                    dtEAN = null;
                    dtBin = null;
                    fm.popBackStack();

                    Log.d("tag", "  response ->" + response);

                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);

                    response = response.substring(2, response.length());

                    box.getBox("Response", response);


                } else {

                    Log.d(TAG, " Response is unknown :" + response);

                    box.getBox("Err", response);


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
                return DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        mRequestQueue.add(mStringRequest);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())

        {
            case R.id.save:


                requester = "save";
                try {
                    networkCall();
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
                IntentIntegrator.forSupportFragment(Scan_Picking_against_picking_Process_Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();

                break;
            case R.id.bin_scan:


                bin_et.setText("");
                scanner = "bin";
                if(CameraCheck.isCameraAvailable(con))
                IntentIntegrator.forSupportFragment(Scan_Picking_against_picking_Process_Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();

                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult == null) {

            Log.d(TAG, TAG + " scanned result...");
           box.getBox("Scanner Err","Unable to receive Data");

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

                            Log.v(TAG, "bar code = " + scanContent);

                            setFormData();

                            barcode_art_et.requestFocus();
                        } else {

                            box.getBox("Alert!!", "First Scan Bar Number");
                        }

                        break;
                    case "bin":

                        Log.v(TAG, "bin code = " + scanContent);
                        if (dtPO != null && (dtPO.get(3).size() > 0) && dtPO.get(3).contains(scanContent))
                            bin_et.setText(scanContent);
                        else {

                            box.getBox("Alert!!", "Incorrect Bin");

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

        String barcode = barcode_art_et.getText().toString();
        String[] arrBarQty = barcode.split("-");

        rows1 = getEANTableData(arrBarQty);


        if (rows1.get(0).size() > 0) {
            try {
                processData(arrBarQty, rows1);

            } catch (Exception e) {
                box.getErrBox(e);
            }
        } else {
            box.getBox("Alert", "Incorrect Article!");
            barcode_art_et.setText("");
            barcode_art_et.requestFocus();
        }


    }


    private void processData(String[] arrBarQty, ArrayList<ArrayList<String>> rows1) {
        String EANNR = null;
        String Str55 = null;
        String EAMMAterial = null;

        int i = 0;
        int ind = 0;
        EANNR = null;
        rows1 = getEANTableData(arrBarQty);
        if (rows1.get(2).size() == 0) {
            Log.d(TAG, "No data found");
            barcode_art_et.setText("");
            barcode_art_et.requestFocus();
            return;
        }
        //  rows1=dtEAN;
        Log.d(TAG, "rows1 array data :" + rows1);
        EAMMAterial = rows1.get(1).get(0);
        //   ScanQty = Integer.valueOf(rows1.get(2).get(0));
        ScanQty = Integer.valueOf(rows1.get(3).get(0));
        //arrBarQty = txt_Article.Text.split('-');
        if (arrBarQty.length > 1)
            ScanQty = Integer.valueOf(arrBarQty[1]);


        ArrayList<ArrayList<String>> rowsPO = getPOTableData(EAMMAterial);
        if (rowsPO.get(0).size() > 0) {
            article_available_stock_et.setText(rowsPO.get(1).get(0));
        } else {
            box.getBox("Err", "Incorrect Article");
            barcode_art_et.setText("");
            barcode_art_et.requestFocus();
            Log.d(TAG, "NO data found for dtpo(matnr == eammaterial)");
            return;
        }
        article_no_et.setText(EAMMAterial.replaceFirst("^0+(?!$)", ""));
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

            barcode_art_et.requestFocus();
        } else {

            box.getBox("Alert", "Scanned Qty can't be greater than Open Qty");
            barcode_art_et.setText("");
            barcode_art_et.requestFocus();
        }
    }

    private ArrayList<ArrayList<String>> getPOTableData(String EAMMAterial) {
        ArrayList<ArrayList<String>> rowsPO = tables.getPOTAble("pick");
        int i = 0;
        rows_index.clear();

        while (dtPO.get(0).size() > i) {
            if (dtPO.get(0).get(i).contains(EAMMAterial)) {
                //getting index of rows that contains matnr=EAMMAterial
                if (dtPO.get(3).get(i).contains(bin_et.getText().toString()))
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
                if (rowsPO.size() > 3) {
                    rowsPO.get(0).add(dtPO.get(0).get(ind));
                    rowsPO.get(1).add(dtPO.get(1).get(ind));
                    rowsPO.get(2).add(dtPO.get(2).get(ind));
                    rowsPO.get(3).add(dtPO.get(3).get(ind));

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
                    if (dtEAN.get(2).get(i).contains(arrBarQty[0])) {
                        //getting index of row that contains ean11=arrbarqty[0]
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
                } else if (dtEAN.size() > 3) {
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
