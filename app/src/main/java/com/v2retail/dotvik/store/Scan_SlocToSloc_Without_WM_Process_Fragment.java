package com.v2retail.dotvik.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
 * {@link Scan_SlocToSloc_Without_WM_Process_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Scan_SlocToSloc_Without_WM_Process_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Scan_SlocToSloc_Without_WM_Process_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "source";
    private static final String ARG_PARAM2 = "dest";

    // TODO: Rename and change types of parameters
    private String mParam2;
    private String mParam1;
    private String scanner;
    ArrayList<Integer> rows_index = new ArrayList<>();
    private String TAG = Scan_GRT_Display_Fragment.class.getName();
    String[] arrBarQty = null;
    Tables tables = new Tables();
    Context con;
    AlertBox box;
    ProgressDialog dialog;
    EditText date;
    FragmentManager fm;
    Button save;
    Button back;
    Button barcode_scan;
    String URL = "";
    String WERKS = "";
    String USER = "";
    TextView mResponseView;
    EditText total_sq_et;
    EditText article_available_stock_et;
    EditText barcode_art_et;
    EditText article_no_et;
    EditText asq_et;
    EditText tsq_et;
    int ScQty = 0;
    int TPOQty = 0;
    int ScanQty = 0;
    int OpenQty = 0;
    int sum = 0;
    String requester = "";
    ArrayList<ArrayList<String>> rows1;
    ArrayList<ArrayList<String>> dtPO;
    ArrayList<ArrayList<String>> dtEAN;
    ArrayList<String> dtBin;
    ArrayList<ArrayList<String>> dtMaterial;
    private OnFragmentInteractionListener mListener;

    public Scan_SlocToSloc_Without_WM_Process_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Scan Sloc To Sloc");
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
    public static Scan_SlocToSloc_Without_WM_Process_Fragment newInstance(String param1, String param2) {
        Scan_SlocToSloc_Without_WM_Process_Fragment fragment = new Scan_SlocToSloc_Without_WM_Process_Fragment();
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
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_sts_wwm, container, false);

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

        total_sq_et = (EditText) view.findViewById(R.id.total_scan_qty);
        article_no_et = (EditText) view.findViewById(R.id.article_no);
        tsq_et = (EditText) view.findViewById(R.id.tsq);
        asq_et = (EditText) view.findViewById(R.id.asq);
        barcode_art_et = (EditText) view.findViewById(R.id.barcode_no);
        article_available_stock_et = (EditText) view.findViewById(R.id.article_available_stock);
        mResponseView = (TextView) view.findViewById(R.id.response);
        barcode_art_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    /*|| (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)*/) {

                    String code = barcode_art_et.getText().toString();
                    if (code == null || code.length() < 0 || code.equals("")) {

                        box.getBox("Alert", "Enter Barcode No First!");
                        return true;
                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(barcode_art_et.getWindowToken(), 0);

                        try {
                            setFormData();
                        } catch (Exception e) {
                            box.getErrBox(e);
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        back.setOnClickListener(this);
        barcode_scan.setOnClickListener(this);
        save.setOnClickListener(this);

        try {
            LoadData();
        } catch (Exception e) {
            box.getErrBox(e);
        }
        //getPACMaterial();
        return view;
    }

    void LoadData() {
        if (dtEAN != null) {
            dtEAN = null;

        }

        if (dtPO != null) {
            dtPO = null;

        }
        if (dtBin != null) {
            dtBin = null;

        }
        if (dtMaterial != null) {
            dtMaterial = null;

        }
        if (mParam1 == null || mParam1.length() < 0 || mParam1.equals("")) {

            box.getBox("Alert", "Source Sloc Not Received");
            return;
        }
        if (mParam2 == null || mParam2.length() < 0 || mParam2.equals("")) {

            box.getBox("Alert", "Destination Sloc Not Received");
            return;
        }
        dtMaterial = tables.getMATTAble();
        dtEAN = tables.getEANTAble("display");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())

        {
            case R.id.save:

                if (mResponseView.getVisibility() == View.VISIBLE) {
                    mResponseView.setText("");
                    mResponseView.setVisibility(View.GONE);
                }
                requester = "save";

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
                IntentIntegrator.forSupportFragment(Scan_SlocToSloc_Without_WM_Process_Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();

                break;

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
                String msg = "";
                msg = WERKS + "," + mParam1 + "," + mParam2 + "," + USER;

                int i = 0, j = 0;

                StringBuilder Stuz = new StringBuilder();
                int len = dtMaterial.get(0).size();
                int lenItem = dtMaterial.size();
                while (len > i) {
                    while (lenItem > j) {
                        Stuz.append("," + dtMaterial.get(j).get(i));
                        j++;
                    }
                    i++;
                }
                msg = msg + Stuz.toString();
                String addrec = msg;
                requester = "save";
                String valueRequestPayload = "savesloctoslocwwm#" + addrec + "#<eol>";
                Log.d(TAG, "Payload-> " + valueRequestPayload);
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
                    Log.d(TAG, " Response is Failure -> " + response);


                    dialog.dismiss();
                    box.getBox("Err", "Empty response from Server");

                } else if (response.charAt(0) == ('S')) {
                    if (requester.equals("getstorestock"))
                        try {
                            loadStoreStockDataFromServer(response);
                        } catch (Exception e) {
                            box.getErrBox(e);
                        }
                    else if (requester.equals("save")) {
                        article_no_et.setText("");
                        tsq_et.setText("");
                        asq_et.setText("");
                        total_sq_et.setText("");
                        dialog.dismiss();
                        box.getBox("Message", response);
                        dtMaterial = null;
                        dtEAN = null;
                        dtBin = null;
                    }

                    Log.d("tag", "  response ->" + response);

                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);
                    response = response.substring(2, response.length());
                    dialog.dismiss();
                    box.getBox("Error", response);

                    if (requester.equals("getstorestocktake")) {

                        barcode_art_et.setText("");
                        barcode_art_et.requestFocus();
                    }


                } else {

                    Log.d(TAG, " Response is unknown :" + response);

                    dialog.dismiss();
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

    private void loadStoreStockDataFromServer(String rcvdData) {
        dialog.dismiss();
        String[] arrayRcvdData = rcvdData.split("!");
        String[] arrayPOData = arrayRcvdData[0].split("#");
        String[] arrayEanData = arrayRcvdData[1].split("#");

        String matnr = arrayPOData[2];
        String labst = arrayPOData[3];

        ArrayList<String> dtean = new ArrayList();
        for (int lk = 0; lk <= arrayEanData.length; lk++) {
            dtean.add(labst + "," + arrayPOData[lk] + "," + arrayPOData[lk + 1] + "," + arrayPOData[lk + 2] + "," + arrayPOData[lk + 3] + "," + arrayPOData[lk + 4]);
            dtEAN.get(0).add(labst);
            dtEAN.get(1).add(arrayPOData[lk]);
            dtEAN.get(2).add(arrayPOData[lk + 1]);
            dtEAN.get(3).add(arrayPOData[lk + 2]);
            dtEAN.get(4).add(arrayPOData[lk + 3]);
            dtEAN.get(5).add(arrayPOData[lk + 4]);
            lk = lk + 6;
        }
        try {
            processData(arrBarQty, rows1);
        } catch (Exception e) {
            box.getErrBox(e);
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


                barcode_art_et.setText(scanContent);
                //bar_scan.setText("Rescan");
                Log.v(TAG, "bar code = " + scanContent);
                // Log.v(TAG,"stored bar code = " + barcode_art);

                try {
                    setFormData();

                } catch (Exception e) {
                    box.getErrBox(e);
                }


            } else {


                box.getBox("Scanner Err", "No Content Received. Please Scan Again");
            }
        }


    }

    private void setFormData() {

        String barcode = barcode_art_et.getText().toString();
        arrBarQty = barcode.split("-");
        rows1 = tables.getEANTAble("display");

        //   i=dtEAN.get(2).size();
        int i = 0;
        if (arrBarQty != null)
            if (dtEAN.size() < 7)
                while (dtEAN.get(3).size() > i) {
                    if (dtEAN.get(3).contains(arrBarQty[0])) {//getting index of row that contains ean11=arrbarqty[0]
                        rows_index.add(dtEAN.get(3).indexOf((arrBarQty[0])));

                    }
                    i++;
                }
        i = 0;

        int ind = 0;
        if ((rows_index.size() > 0))
            while (rows_index.size() > i) {
                //adding row data
                ind = rows_index.get(i);
                if (rows1.size() < 6) {
                    rows1.get(0).add(dtEAN.get(0).get(ind));
                    rows1.get(1).add(dtEAN.get(1).get(ind));
                    rows1.get(2).add(dtEAN.get(2).get(ind));
                    rows1.get(3).add(dtEAN.get(3).get(ind));
                    rows1.get(4).add(dtEAN.get(4).get(ind));
                    i += 5;
                }

                i++;
            }

        if (rows1.get(0).size() > 0) {
            try {
                processData(arrBarQty, rows1);
            } catch (Exception e) {
                box.getErrBox(e);
            }
        } else {
            try {
                getStoreStockdata();

            } catch (Exception e) {
                box.getErrBox(e);
            }


        }

    }

    private void processData(String[] arrBarQty, ArrayList<ArrayList<String>> rows1) {
        String EANNR = null;
        String Str55 = null;
        String EAMMAterial = null;

        int i = 0, ind = 0;
        ArrayList<Integer> rows_index = new ArrayList<>();
        EANNR = null;
        EAMMAterial = rows1.get(2).get(0);
        ScanQty = Integer.valueOf(rows1.get(4).get(0));

        //arrBarQty = txt_Article.Text.split('-');
        if (arrBarQty.length > 1)
            ScanQty = Integer.valueOf(arrBarQty[1]);
        EANNR = rows1.get(5).get(0);
        article_no_et.setText(EAMMAterial.replaceFirst("^0+(?!$)", ""));
        article_available_stock_et.setText(rows1.get(0).get(0));

        OpenQty = (int) Math.floor(Double.valueOf(article_available_stock_et.getText().toString()));
        Str55 = EAMMAterial + "," + ScanQty;

        if (dtMaterial.get(0).size() > 0) {
            ArrayList<ArrayList<String>> rowsMAT = tables.getMATTAble();

            if (dtMaterial.get(0).contains(rows1.get(2).get(0))) {
                rows_index.clear();
                while (dtMaterial.get(0).size() > i) {
                    if (dtMaterial.get(0).contains(rows1.get(2).get(0))) {//getting index of rows that contains matnr=EAMMAterial
                        rows_index.add(i);

                    }
                    i++;
                }
                i = 0;
                ind = 0;
                if (rows_index.size() > 0)
                    while (rows_index.size() > i) {
                        //adding row data
                        ind = rows_index.get(i);
                        if (rowsMAT.size() < 3) {
                            rowsMAT.get(0).add(dtMaterial.get(0).get(ind));
                            rowsMAT.get(1).add(dtMaterial.get(1).get(ind));
                            rowsMAT.get(2).add(dtMaterial.get(2).get(ind));

                        }
                        i++;
                    }

            }

            if (rowsMAT.get(0).size() > 0) {

                i = 0;
                int sumqty = 0;
                while (rows_index.size() > i) {
                    //adding row data
                    ind = rows_index.get(i);
                    if (rowsMAT.size() > 0) {
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


        sum = sum + ScanQty;

        if (sum <= OpenQty) {
            ScQty = ScQty + ScanQty;
            asq_et.setText(String.valueOf(sum));
            dtMaterial.get(0).add(EAMMAterial);
            dtMaterial.get(1).add(String.valueOf(ScanQty));
            dtMaterial.get(2).add("");


            total_sq_et.setText(ScQty);
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


    private void getStoreStockdata() {
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String addrec = null;
                addrec = WERKS + "#" + article_no_et.getText().toString();
                String valueRequestPayload = "getstorestock#" + addrec + "#<eol>";
                requester = "getstorestock";
                Log.d(TAG, "Payload-> " + valueRequestPayload);
                sendAndRequestResponse(valueRequestPayload);
            }
        }, 2000);

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        fm.popBackStackImmediate();
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
