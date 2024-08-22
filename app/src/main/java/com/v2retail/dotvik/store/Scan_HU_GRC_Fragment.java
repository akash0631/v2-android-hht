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
 * {@link Scan_HU_GRC_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Scan_HU_GRC_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Scan_HU_GRC_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "TTL_HU";
    private static final String ARG_PARAM2 = "store_name";
    private static final String ARG_PARAM3 = "hu_list";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> mParam3;
    private static final String TAG = Scan_HU_GRC_Fragment.class.getName();
    TextView mResponseView;
    Context con;
    Button back;
    Button scan;
    Button save;
    FragmentManager fm;
    EditText ttl_hu_et;
    EditText scan_hu_et;
    EditText short_hu_et;
    EditText excess_hu_et;
    AlertBox box;
    ProgressDialog dialog;
    String URL = "";
    String WERKS = "";
    String USER = "";
    int ttlhu = 0;
    int shorthu = 0;
    int ScanQty = 0;
    int count = 0; 
    StringBuilder barcode_number = new StringBuilder();
    Tables tables = new Tables();
    ArrayList<String> dtHu = tables.getBINTAble();
    ArrayList<String> dtMat = tables.getBINTAble();
    private OnFragmentInteractionListener mListener;


    public Scan_HU_GRC_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Scan HU GRC Process");
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
    public static Scan_HU_GRC_Fragment newInstance(String param1, String param2) {
        Scan_HU_GRC_Fragment fragment = new Scan_HU_GRC_Fragment();
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
            dtHu = (ArrayList<String>) getArguments().getSerializable(ARG_PARAM3);
            //mParam3 = getArguments().getString(ARG_PARAM3);
            if (mParam1 != null) {
                ttlhu = Integer.valueOf(mParam1);
                shorthu = Integer.valueOf(mParam1);
                ;
            }

            Log.d(TAG, "ttl hu: " + ttlhu);
            Log.d(TAG, "store name: " + mParam2);
            Log.d(TAG, "hu list : " + dtHu);
            //Log.d(TAG,  "store : "+mParam3);

        }
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, TAG + " created");

        View view = inflater.inflate(R.layout.scan_hu_grc, container, false);
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
        scan = (Button) view.findViewById(R.id.scan);

        scan_hu_et = (EditText) view.findViewById(R.id.scan_hu_no);
        ttl_hu_et = (EditText) view.findViewById(R.id.ttl_hu);
        excess_hu_et = (EditText) view.findViewById(R.id.excess_hu);
        short_hu_et = (EditText) view.findViewById(R.id.short_hu);
        save = (Button) view.findViewById(R.id.save);
        mResponseView = (TextView) view.findViewById(R.id.response);

        scan_hu_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    /*|| (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)*/) {

                    String dat = scan_hu_et.getText().toString();
                    if (dat != null || dat.length() > 0 || !dat.equals("")) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(scan_hu_et.getWindowToken(), 0);


                        try {
                            checkData(dat);
                        } catch (Exception e) {
                            box.getErrBox(e);
                        }


                        return true;
                    } else {
                        box.getBox("Alert", "Please Enter HU No.");
                        return true;
                    }

                }
                return false;
            }
        });


        if (mParam1 != null) {
            ttl_hu_et.setText(mParam1);
            short_hu_et.setText(mParam1);


        }

        back.setOnClickListener(this);
        scan.setOnClickListener(this);
        save.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

        // fm.popBackStackImmediate();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                try {
                    networkCall();
                } catch (Exception e) {
                    box.getErrBox(e);
                }

                break;

            case R.id.scan:
                Log.d(TAG, TAG + " Scanning...");
                scan_hu_et.setText("");
                if(CameraCheck.isCameraAvailable(con))
                IntentIntegrator.forSupportFragment(Scan_HU_GRC_Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();
                break;

            case R.id.back:
                fm.popBackStack();
                break;

        }
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
                    loadData();

                } catch (Exception e) {
                    box.getErrBox(e);
                }
            }
        }, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String scanContent = scanningResult.getContents();
        String scanFormat = scanningResult.getFormatName();


        if (scanningResult==null) {

            Log.d(TAG, TAG + " scanned result...");
            box.getBox("Scanner Err", "Unable to receive Data");

        } else {
            Log.d(TAG, "Scan data received");

            Log.v(TAG, "scanContent = " + scanContent);
            Log.v(TAG, "scanFormat = " + scanFormat);

            if (!scanContent.equals("null") || !scanContent.equals("")) {
                try {
                    scan_hu_et.setText(scanContent);
                    checkData(scanContent);
                    ;

                } catch (Exception e) {
                    box.getErrBox(e);
                }

            } else {
                box.getBox("Scanner Err", "No Content Received. Please Scan Again");

            }
        }


    }

    private void checkData(String scanContent) {
        String no = barcode_number.toString();
        ArrayList<String> No_list = new ArrayList<>();

        if (!scanContent.equals("null")) {
            if (dtHu != null && dtHu.contains(scanContent)) {
                if (dtMat.size() > 0) {
                    if (dtMat.contains(scan_hu_et.getText().toString())) {
                        box.getBox("Alert", "Already Scan");
                        scan_hu_et.setText("");
                    } else {
                        ScanQty += 1;
                        int tt = Integer.valueOf(ttl_hu_et.getText().toString());
                        short_hu_et.setText(String.valueOf(tt - ScanQty));
                        String str55 = scan_hu_et.getText().toString();
                        dtMat.add(str55);
                        scan_hu_et.setText("");
                    }

                } else {
                    ScanQty += 1;
                    int tt = Integer.valueOf(ttl_hu_et.getText().toString());
                    short_hu_et.setText(String.valueOf(tt - ScanQty));
                    String str55 = scan_hu_et.getText().toString();
                    dtMat.add(str55);
                    scan_hu_et.setText("");
                }

            } else {

                box.getBox("Alert!", "Incorrect HU");
                return;
            }
        } else {

            box.getBox("Alert!", "Unable to Scan data / NO data input");
            return;
        }
    }


    private void loadData() {
        //  barcode_numbers=barcode_number.toString();
        String addrec = "";
        StringBuilder builder = new StringBuilder();
        int i = 0;
        if (dtMat.size() > 0)
            while (dtMat.size() > i) {
                builder.append("," + dtMat.get(i));
                i++;
            }
        addrec = WERKS + "," + USER + builder.toString();
        String valueRequestPayload = "savehus#" + addrec + "#<eol>";
        Log.d(TAG, "payload : " + valueRequestPayload);
        Log.d(TAG, "payload sent to server ");
        sendAndRequestResponse(valueRequestPayload);


    }

    private void sendAndRequestResponse(final String requestBody) {

        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        //RequestQueue initialized
        mRequestQueue = ApplicationController.getInstance().getRequestQueue();

        mStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                response = response.substring(9, response.length());

                Log.d(TAG, "response :" + response);

                if (response == null) {
                    dialog.dismiss();
                    box.getBox("Error", "No response from Server");

                } else if (response.equals("")) {
                    dialog.dismiss();
                    box.getBox("Alert", "Empty response from server");
                } else if (response.charAt(0) == ('S')) {


                    Log.d(TAG, " Response :Data Saved " + response);
                    response = response.substring(2, response.length());

                    excess_hu_et.setText("");
                    scan_hu_et.setText("");
                    short_hu_et.setText("");
                    ttl_hu_et.setText("");
                    ScanQty = 0;
                    count = 0;
                    if (dtMat != null)
                        dtMat.clear();

                    dialog.dismiss();
                    ;
                    box.getBox("Response", response);

                    Log.d(TAG, " Response : Data Saved :" + response);
                    fm.popBackStack();
                    // clear();


                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);

                    dialog.dismiss();
                    response = response.substring(2, response.length());
                    box.getBox("Err", response);

                    clear();
                } else {
                    dialog.dismiss();

                    box.getBox("Err", response);
                    Log.d(TAG, " Response is unknown :" + response);


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
                ;
                box.getBox("Error", err);
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


    private void clear() {
        ttl_hu_et.setText("");
        short_hu_et.setText("");
        excess_hu_et.setText("");
        scan_hu_et.setText("");
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
