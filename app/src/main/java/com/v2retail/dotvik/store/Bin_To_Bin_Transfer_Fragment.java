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
import com.v2retail.dotvik.R;
import com.v2retail.util.AlertBox;
import com.v2retail.util.EditTextDate;
import com.v2retail.util.SharedPreferencesData;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Bin_To_Bin_Transfer_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Bin_To_Bin_Transfer_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bin_To_Bin_Transfer_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText date;
    EditText store_name_et;
    EditText destination_bin_et;
    TextView mResponseView;
    Button back;
    Button next;
    FragmentManager fm;
    Context con;
    ProgressDialog dialog;
    AlertBox box;
    String URL = "";
    String WERKS = "";
    String USER = "";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String date_string;
    private String store_name_string;
    private String TAG = Bin_To_Bin_Transfer_Fragment.class.getName();
    private OnFragmentInteractionListener mListener;

    public Bin_To_Bin_Transfer_Fragment() {
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
    public static Bin_To_Bin_Transfer_Fragment newInstance(String param1, String param2) {
        Bin_To_Bin_Transfer_Fragment fragment = new Bin_To_Bin_Transfer_Fragment();
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
                .setActionBarTitle("Bin To Bin Transfer");
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

        View view = inflater.inflate(R.layout.fragment_bin_to_bin_transfer, container, false);
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

        date = (EditText) view.findViewById(R.id.date);
        store_name_et = (EditText) view.findViewById(R.id.store_name);
        destination_bin_et = (EditText) view.findViewById(R.id.dest_bin);
        mResponseView = (TextView) view.findViewById(R.id.response);

        EditTextDate editTextDate = new EditTextDate(getContext());
        editTextDate.setDateOnView(date);
        //date.setEnabled(false);

        next = (Button) view.findViewById(R.id.next);
        back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        store_name_et.setText(WERKS);


        destination_bin_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    /*|| (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)*/) {

                    String bin = destination_bin_et.getText().toString().trim();
                    if (TextUtils.isEmpty(destination_bin_et.getText().toString().trim())&&bin.equals("")) {
                        box.getBox("Alert", "Enter destination Bin No.");
                        return true;
                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(destination_bin_et.getWindowToken(), 0);

                        next.requestFocus();
                        return true;
                    }
                }
                return false;
            }
        });
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
                Log.d(TAG, "store -> "+store);
              /*  if (!TextUtils.isEmpty(store_name_et.getText().toString().trim())&& !store.equals("")) {
                    addrec = store + "#" + date.getText().toString().trim();

                }
                else {
                    dialog.dismiss();
                    box.getBox("Error", "Start Again , Store Name not Found");
                    return;
                }*/
                final String valueRequestPayload = "storegetbin#" + addrec + "#<eol>";
                Log.d(TAG, "payload : " + valueRequestPayload);
                Log.d(TAG, "payload sent to server ");
                sendAndRequestResponse(valueRequestPayload);
            }
        }, 1000);


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
                    box.getBox("Message", "Empty Data From Server");
                } else if (response.charAt(0) == ('S')) {
                    response = response.substring(2, response.length());

                    String[] arrayRcvdData = response.split("!");
                    String[] arrayBinData = arrayRcvdData[0].split("#");


                    ArrayList<String> bin_list = new ArrayList<>();
                    for (String temp : arrayBinData) {

                        bin_list.add(temp);

                    }
                    Log.d("tag", "bin list ->" + bin_list);

                    int x = -1;
                    String dest_bin = destination_bin_et.getText().toString().trim();
                    if (bin_list.contains(dest_bin))
                        x = bin_list.indexOf(dest_bin);

                    if (x > -1) {

                        Fragment fragment = new Scan_Bin_To_BinTransfer_Process_Fragment();
                        Bundle args = new Bundle();
                        args.putSerializable("bin_list", bin_list);

                        args.putString("dest_list", dest_bin);
                        fragment.setArguments(args);
                        dialog.dismiss();
                        if (fragment != null) {

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.home, fragment, "B2b");
                            ft.addToBackStack("B2b");
                            ft.commit();

                        }


                    } else {
                        dialog.dismiss();
                        box.getBox("Alert", "Incorrect destination bin");
                    }


                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);

                    response = response.substring(2, response.length());
                    dialog.dismiss();
                    box.getBox("Error", response);

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



    private boolean ValidateData() {


        date_string = date.getText().toString().trim();
        store_name_string = store_name_et.getText().toString().trim();


        if (!Validate(date_string, "Date"))
            if (!Validate(store_name_string, "store Name")) {
                return false;
            }


        return true;


    }

    private boolean Validate(String value, String Tag) {


        if (value.equals(""))
        {
            box.getBox("Invalid Value!", "Please enter " + Tag);
            return false;
        }

        return true;
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
