package com.v2retail.dotvik.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * {@link Direct_Picking_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Direct_Picking_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Direct_Picking_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = Direct_Picking_Fragment.class.getName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String URL = "";
    String WERKS = "";
    String USER = "";
    AlertBox box;
    Context con;
    ProgressDialog dialog;

    EditText date_et;
    EditText store_name_et;
    TextView mResponseView;
    Button back;
    FragmentManager fm;

    String date_string;
    String store_name_string;
    private OnFragmentInteractionListener mListener;

    public Direct_Picking_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Direct Picking");
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
    public static Direct_Picking_Fragment newInstance(String param1, String param2) {
        Direct_Picking_Fragment fragment = new Direct_Picking_Fragment();
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

        View view = inflater.inflate(R.layout.floor_putway, container, false);
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
        back = (Button) view.findViewById(R.id.back);
        Button next = (Button) view.findViewById(R.id.next);
        mResponseView = (TextView) view.findViewById(R.id.response);

        back.setOnClickListener(this);
        EditTextDate editTextDate = new EditTextDate(getContext());
        editTextDate.setDateOnView(date_et);
        // date_et.setEnabled(false);
        next.setOnClickListener(this);
        next.requestFocus();
        store_name_et.setText(WERKS);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:

                try {
                    if (ValidateData())
                        searchData();
                } catch (Exception e) {
                    box.getErrBox(e);
                }
                break;
            case R.id.back:
                fm.popBackStack();
                break;
        }
    }

    private void searchData() {

        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String addrec = "";
                String store = store_name_et.getText().toString().trim();
                if (!TextUtils.isEmpty(store_name_et.getText().toString().trim())&& !store.equals("")) {
                    addrec = store + "#" + date_et.getText().toString().trim();

                }
                else {
                    dialog.dismiss();
                    box.getBox("Error", "Start Again , Store Name not Found");
                    return;
                }
                String valueRequestPayload = "storegetbin#" + addrec + "#<eol>";
                Log.d(TAG, "payload : " + valueRequestPayload);
                Log.d(TAG, "payload sent to server ");

                sendAndRequestResponse(valueRequestPayload);
            }
        }, 1000);

    }

    private boolean ValidateData() {


        date_string = date_et.getText().toString().trim();
        store_name_string = store_name_et.getText().toString().trim();


        if (!Validate(date_string, "Date"))
            if (!Validate(store_name_string, "store Name")) {
                return false;
            }


        return true;


    }

    private boolean Validate(String value, String Tag) {


        if (value.equals("") ) {


            box.getBox("Invalid Value!", "Please enter value for " + Tag);
            return false;
        }

        return true;
    }

    private void clear() {

        mResponseView.setText("");
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

                    box.getBox("Err", "Empty response from Server/ Unable to connect");

                } else if (response.charAt(0) == ('S')) {

                    response = response.substring(2, response.length());

                    String[] arrayRcvdData = response.split("!");
                    String[] arrayBinData = arrayRcvdData[0].split("#");


                    ArrayList<String> bin_list = new ArrayList<>();
                    for (String temp : arrayBinData) {

                        bin_list.add(temp);

                    }
                    Log.d("tag", "bin list ->" + bin_list);

                    Fragment fragment = new Scan_Direct__picking_Process_Fragment();
                    Bundle args = new Bundle();
                    args.putSerializable("bin_list", bin_list);
                    fragment.setArguments(args);

                    if (fragment != null) {
                        clear();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.home, fragment, "Direct_picking");
                        ft.addToBackStack("Direct_picking");

                        ft.commit();

                    }
                    clear();


                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);
                    response = response.substring(2, response.length());
                   ;
                    box.getBox("Err", response);
                    clear();
                } else {

                    Log.d(TAG, " Response is unknown :" + response);

                    box.getBox("Err", response);

                    clear();

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
