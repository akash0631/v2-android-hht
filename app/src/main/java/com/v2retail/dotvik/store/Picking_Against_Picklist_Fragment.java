package com.v2retail.dotvik.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
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
import com.v2retail.commons.UIFuncs;
import com.v2retail.dotvik.R;
import com.v2retail.util.AlertBox;
import com.v2retail.util.EditTextDate;
import com.v2retail.util.SharedPreferencesData;
import com.v2retail.util.Tables;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Picking_Against_Picklist_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Picking_Against_Picklist_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Picking_Against_Picklist_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = Picking_Against_Picklist_Fragment.class.getName();
    String URL = "";
    String WERKS = "";
    String USER = "";
    Context con;
    ProgressDialog dialog;
    AlertBox box;
    EditText date;
    EditText Store_name_et;
    EditText pick_list_et;
    TextView mResponseView;
    Button back;
    Button next;

    Tables tables = new Tables();

    ArrayList<ArrayList<String>> dtPO;
    ArrayList<ArrayList<String>> dtEAN;
    FragmentManager fm;
    private OnFragmentInteractionListener mListener;

    public Picking_Against_Picklist_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Picking Against Picking");
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
    public static Picking_Against_Picklist_Fragment newInstance(String param1, String param2) {
        Picking_Against_Picklist_Fragment fragment = new Picking_Against_Picklist_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void loadData() {
        if (dtEAN != null) {
            dtEAN = null;

        }

        if (dtPO != null) {
            dtPO = null;

        }


        dtPO = tables.getPOTAble("pick");
        dtEAN = tables.getEANTAble("");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                try {
                    networkCall();
                } catch (Exception e) {
                    box.getErrBox(e);
                }
                break;
            case R.id.back:
                fm.popBackStack();
                break;
        }
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


        View view = inflater.inflate(R.layout.picking_against_picklist, container, false);
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
        date = (EditText) view.findViewById(R.id.date);
        Store_name_et = (EditText) view.findViewById(R.id.store_name);
        pick_list_et = (EditText) view.findViewById(R.id.pick_list);
        //UIFuncs.disableKeyInput(pick_list_et, view, con);
        mResponseView = (TextView) view.findViewById(R.id.response);
        EditTextDate editTextDate = new EditTextDate(getContext());
        editTextDate.setDateOnView(date);
        //  date.setEnabled(false);

        back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(this);

        next = (Button) view.findViewById(R.id.next);
        next.setOnClickListener(this);

        Store_name_et.setText(WERKS);
        pick_list_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    /*|| (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)*/) {

                    String list = pick_list_et.getText().toString();
                    if (list == null || list.length() < 0 || list.equals("")) {

                        box.getBox("Alert", "Please Enter Pick List No. !!");
                        return true;
                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(pick_list_et.getWindowToken(), 0);

                        next.requestFocus();
                        return true;
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

    private void networkCall() {
        String pick_list = pick_list_et.getText().toString().trim();
        if (TextUtils.isEmpty( pick_list_et.getText().toString().trim())&& pick_list.equals("")) {

            box.getBox("Alert", "Please enter pick list first");
            return;
        }
        pick_list = Store_name_et.getText().toString().trim();
        if (TextUtils.isEmpty( Store_name_et.getText().toString().trim())&& pick_list.equals("")) {

            box.getBox("Alert", "Start Again , Store Name not Found");
            return;
        }
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    searchData();
                } catch (Exception e) {
                    dialog.dismiss();
                    box.getErrBox(e);
                }
            }
        }, 2000);
    }

    private void searchData() {
        //hugetdetails#1005827668#HD22#1/2/2009#<eol>

        String addrec = Store_name_et.getText().toString().trim() + "#" + pick_list_et.getText().toString().trim() + "#" + date.getText().toString().trim();

        String valueRequestPayload = "getstorepicklist#" + addrec + "#<eol>";
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
                    box.getBox("Err", "No response from Server");

                } else if (response.equals("")) {
                    dialog.dismiss();
                    box.getBox("Err", "Empty response from Server/Unable to connect server");

                } else if (response.charAt(0) == ('S')) {
                    try {
                        loadDataFromServer(response);

                    } catch (Exception e) {
                        box.getErrBox(e);
                    }


                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure ->  " + response);

                    response = response.substring(2, response.length());
                    dialog.dismiss();
                    box.getBox("Response", response);

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

    private void loadDataFromServer(String resp) {
//Response:
//
        //S#000000001130033500#30.000#0.000#0.000#00-1603A4#!
// 300#000000001130033500#19074363#1##300#000000001130033500#19075652#15##300#000000001130033500#19075653#15##
        Log.d(TAG, "loading Server data :");
        resp = resp.substring(2, resp.length());
        String arrayRcvdData[] = resp.split("!");
        String[] arrayPOData = arrayRcvdData[0].split("#");
        String[] eanData = arrayRcvdData[1].split("##");


        //testing purpose
        List<String> data_list = Arrays.asList(arrayPOData);
        Log.d(TAG, "po data :" + data_list);

        data_list = Arrays.asList(eanData);
        Log.d(TAG, "ean data :" + data_list);

        // dtPO=tables.getPOTAble("");

        for (int lk = 0; lk <= arrayPOData.length - 4; ) {

            dtPO.get(0).add(arrayPOData[lk]);
            dtPO.get(1).add(arrayPOData[lk + 1]);
            dtPO.get(2).add(arrayPOData[lk + 2]);
            dtPO.get(3).add(arrayPOData[lk + 3]);

            lk = lk + 4;
        }


        for (int lk = 0; lk < eanData.length; lk++) {
            String ar[] = eanData[lk].split("#");

            for (int i = 0, j = 0; j < ar.length; i++, j++) {
                dtEAN.get(i).add(ar[j]);
            }

        }
        Log.d(TAG, "DTean :" + dtEAN);
        Log.d(TAG, "DTPO :" + dtPO);
        Log.d(TAG, "picklist_number :" + pick_list_et.getText().toString());


        Bundle args = new Bundle();
        args.putSerializable("po_list", dtPO);
        args.putSerializable("ean_list", dtEAN);
        args.putSerializable("picklist_number", pick_list_et.getText().toString());

        dialog.dismiss();
        Fragment fragment = new Scan_Picking_against_picking_Process_Fragment();
        fragment.setArguments(args);
        if (fragment != null) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "picking_against_picking");
            ft.addToBackStack("picking_against_picking");
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
