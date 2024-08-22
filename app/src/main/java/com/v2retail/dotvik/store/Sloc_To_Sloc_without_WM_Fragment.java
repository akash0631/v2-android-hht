package com.v2retail.dotvik.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.v2retail.util.EditTextDate;
import com.v2retail.util.SharedPreferencesData;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sloc_To_Sloc_without_WM_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sloc_To_Sloc_without_WM_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sloc_To_Sloc_without_WM_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String scanner = "";
    private String requester = "";
    AlertBox box;
    private String TAG = GRT_From_DisplayFragment.class.getName();
    Context con;
    ProgressDialog dialog;
    String URL = "";
    String WERKS = "";
    String USER = "";
    Button back;
    Button next;
    FragmentManager fm;
    EditText date_et;
    EditText store_name_et;
    EditText dest_sloc_et;
    EditText source_sloc_et;
    TextView mResponseView;
    ArrayList<String> source_bin_table = new ArrayList<>();
    ArrayList<String> dest_bin_table = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public Sloc_To_Sloc_without_WM_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Sloc To Sloc Without WWM");
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
    public static Sloc_To_Sloc_without_WM_Fragment newInstance(String param1, String param2) {
        Sloc_To_Sloc_without_WM_Fragment fragment = new Sloc_To_Sloc_without_WM_Fragment();
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


    private void getSlocdata() {
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String addrec = null;
                addrec = store_name_et.getText().toString() + "#" + " ";
                String valueRequestPayload = "getsloc#" + addrec + "#<eol>";
                requester = "getSloc";
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
                dialog.dismiss();
                if (response == null) {

                    box.getBox("Err", "No response from Server");

                } else if (response.equals("")||response.equals("null")) {
                    Log.d(TAG, " Response is Failure -> " + response);


                    box.getBox("Err", "Unable to Connect Server/ Empty Response");


                } else if (response.charAt(0) == ('S')) {
                    try {
                        if (requester.equals("getSloc"))
                            loadSlocDataFromServer(response);

                    } catch (Exception e) {
                        box.getErrBox(e);
                    }


                    Log.d("tag", "  response ->" + response);

                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);


                    response = response.substring(2, response.length());

                    box.getBox("Err", response);

                } else {

                    Log.d(TAG, " Response is unknown :" + response);
                    dialog.dismiss();
                    box.getBox("Err", response);
                    ;

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
                ;
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

    private void loadSlocDataFromServer(String rcvdData) {

        String[] myArr = null;
        myArr = rcvdData.split("#");

        String[] arrayRcvdData = null;
        String[] arraySRCData = null;
        String[] arrayDSTData = null;
        if (myArr[0].equals("S")) {
            rcvdData=rcvdData.substring(2,rcvdData.length());
            arrayRcvdData = rcvdData.split("!");
            arraySRCData = arrayRcvdData[0].split("#");
            arrayDSTData = arrayRcvdData[1].split("#");

            for (int lk = 0; lk < arraySRCData.length; lk++) {
                source_bin_table.add(arraySRCData[lk]);
            }
            for (int j = 0; j < arrayDSTData.length; j++) {

                dest_bin_table.add(arrayDSTData[j]);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sloc_to_sloc_wwm, container, false);
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
        date_et = (EditText) view.findViewById(R.id.date);
        store_name_et = (EditText) view.findViewById(R.id.store_name);
        dest_sloc_et = (EditText) view.findViewById(R.id.dest_sloc);
        source_sloc_et = (EditText) view.findViewById(R.id.source_sloc);
        mResponseView = (TextView) view.findViewById(R.id.response);

        store_name_et.setText(WERKS);

        EditTextDate editTextDate = new EditTextDate(con);
        editTextDate.setDateOnView(date_et);
        //  date_et.setEnabled(false);

        back = (Button) view.findViewById(R.id.back);
        next = (Button) view.findViewById(R.id.next);

        next.setOnClickListener(this);
        back.setOnClickListener(this);

        source_sloc_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    /*|| (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)*/) {

                    String sloc = source_sloc_et.getText().toString();
                    if (sloc == null || sloc.length() < 0 || sloc.equals("")) {
                        box.getBox("Alert", "Enter Source Sloc!");
                        return true;
                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(source_sloc_et.getWindowToken(), 0);

                        dest_sloc_et.requestFocus();
                        return true;
                    }
                }
                return false;
            }
        });
        dest_sloc_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    /*|| (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)*/) {

                    String sloc = dest_sloc_et.getText().toString();
                    if (sloc == null || sloc.length() < 0 || sloc.equals("")) {

                        box.getBox("Alert", "Enter Destination Sloc!");
                        return true;
                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(dest_sloc_et.getWindowToken(), 0);

                        next.requestFocus();
                        return true;
                    }
                }
                return false;
            }
        });


        try {
            getSlocdata();
        } catch (Exception e) {
            box.getErrBox(e);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:

                try {
                    nextScreen();

                } catch (Exception e) {
                    box.getErrBox(e);
                }
                break;
            case R.id.back:
                fm.popBackStack();
                break;

        }
    }

    private void nextScreen() {


        String source = source_sloc_et.getText().toString();
        String dest = dest_sloc_et.getText().toString();
        Log.d(TAG,"Source table->"+source_bin_table);
        Log.d(TAG,"des table->"+dest_bin_table);
       if (source_bin_table.contains(source)) {

        }else {
           box.getBox("Alert", "Invalid Source!");
           return;
       }


        if (dest_bin_table.contains(dest)) {

        }
        else {
            box.getBox("Alert", "Invalid Destination!");
            return;
        }
        if (source == null || source.equals("") || source.length() < 0) {
            box.getBox("Alert", "Please Scan/Fill source");
            return;
        }
        if (dest == null || dest.equals("") || dest.length() < 0) {
            box.getBox("Alert", "Please Scan/Fill destination");
            return;
        }
        if (dest.equals("0002") || dest.equals("0005")) {
            box.getBox("Alert", "Invalid destination");
            return;
        }
        if (source.equals("0002") || source.length() < 0) {
            box.getBox("Alert", "Invalid source");
            return;
        }
        if (source.equals(dest)) {
            box.getBox("Alert", "Source and destination can not be same");
            return;
        } else {
            Bundle args = new Bundle();
            args.putString("source", source);
            args.putString("dest", dest);
            Fragment fragment = new Scan_TRFDispToProc_Fragment();
            fragment.setArguments(args);
            if (fragment != null) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.home, fragment, "sloc");
                ft.addToBackStack("sloc");
                ft.commit();
            }
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
                    case "source":
                        Log.v(TAG, "scanContent source = " + scanContent);
                        source_sloc_et.setText(scanContent);

                        break;
                    case "dest":
                        Log.v(TAG, "scanContent dest = " + scanContent);

                        dest_sloc_et.setText(scanContent);


                        break;
                }

            } else {
                box.getBox("Scanner Err", "No Content Received. Please Scan Again");

            }
        }


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
