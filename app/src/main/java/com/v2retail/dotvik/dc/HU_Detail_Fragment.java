package com.v2retail.dotvik.dc;

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
import com.v2retail.util.SharedPreferencesData;
import com.v2retail.util.Tables;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HU_Detail_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HU_Detail_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HU_Detail_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final String TAG = HU_Detail_Fragment.class.getName();

    TextView mResponseView;
    Button save;
    Button back;
    EditText hu_no_et;
    Context con;
    FragmentManager fm;
    AlertBox box ;
    String URL="";
    String WERKS="";
    String USER="";
    ProgressDialog dialog;
    ArrayList<ArrayList<String>> dtEan;
    ArrayList<ArrayList<String>> dtDel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Tables tables = new Tables();

    private OnFragmentInteractionListener mListener;

    public HU_Detail_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("HU Details ");
    }

    public static HU_Detail_Fragment newInstance(String param1, String param2) {
        HU_Detail_Fragment fragment = new HU_Detail_Fragment();
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

        View view = inflater.inflate(R.layout.hu_detail, container, false);
        con = getContext();
        box = new AlertBox(con);
        dialog=new ProgressDialog(con);
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
        hu_no_et = (EditText) view.findViewById(R.id.hu_no);
        mResponseView = (TextView) view.findViewById(R.id.response);
        save = (Button) view.findViewById(R.id.save);
        back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStack();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              try {
                  goToNext();
              }catch (Exception e)
              {
                  box.getErrBox(e);
              }
            }
        });


        hu_no_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String no = hu_no_et.getText().toString().trim();
                    if (TextUtils.isEmpty(hu_no_et.getText().toString().trim()) &&  no.equals("")) {

                        box.getBox("Alert", "First Enter Hu No!");
                        hu_no_et.requestFocus();
                        return true;
                    } else {
                        try {
                            goToNext();
                        }catch (Exception e)
                        {
                            box.getErrBox(e);
                        }
                        return true;
                    }
                }
                return false;
            }
        });


        Log.d(TAG, TAG + " created");
        return view;
    }

    private void goToNext() {
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String hu= hu_no_et.getText().toString().trim();
                if(TextUtils.isEmpty(hu_no_et.getText().toString().trim()) && hu.equals(""))
                {
                    dialog.dismiss();
                box.getBox("Alert","First Enter HU No");
                return;
                }
                String valueRequestPayload = "hudetails#" +hu.trim() + "#<eol>";
                Log.d(TAG, "payload : " + valueRequestPayload);
                Log.d(TAG, "payload sent to server ");
                try {
                    sendAndRequestResponse(valueRequestPayload);

                }catch (Exception e)
                {  dialog.dismiss();
                    box.getErrBox(e);
                }
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

                Log.d(TAG, "response :" + response);

                if (response == null) {
                    dialog.dismiss();
                      box.getBox("Err","No response from Server");

                } else if (response.charAt(0) == ('S')) {

                    dialog.dismiss();
                    Log.d(TAG, " Response :Invoice Check " + response);
                    response = response.substring(2, response.length());

                    String[] arrayRcvdData = response.split("!");
                    String[] arrayDelData = arrayRcvdData[0].split("#");
                    String[] arrayEanData = arrayRcvdData[1].split("#");


                    dtDel = tables.getDeliveryTable();
                    for (int lk = 2; lk <= arrayDelData.length - 7; ) {
                        dtDel.get(0).add(arrayDelData[lk]);
                        dtDel.get(1).add(arrayDelData[lk + 1]);
                        dtDel.get(2).add(arrayDelData[lk + 2]);
                        dtDel.get(3).add(arrayDelData[lk + 3]);
                        dtDel.get(4).add(arrayDelData[lk + 4]);
                        dtDel.get(5).add(arrayDelData[lk + 5]);
                        dtDel.get(6).add(arrayDelData[lk + 6]);
                        lk = lk + 7;
                    }

                    dtEan = tables.getEANTAble("del");
                    for (int lk = 0; lk <= arrayEanData.length - 4; ) {

                        dtEan.get(0).add(arrayEanData[lk]);
                        dtEan.get(1).add(arrayEanData[lk + 1]);
                        dtEan.get(2).add(arrayEanData[lk + 2]);
                        dtEan.get(3).add(arrayEanData[lk + 3]);
                        lk = lk + 4;
                    }

                    Bundle args = new Bundle();
                    args.putString("del_no", arrayDelData[0]);
                    args.putString("total_hu_scan_qty", arrayDelData[1]);
                    args.putString("hu_no", hu_no_et.getText().toString());
                    args.putSerializable("hu_list", dtDel);
                    args.putSerializable("Ean_list", dtEan);


                    Fragment fragment = new HU_Material_Scan_Fragment();

                    fragment.setArguments(args);
                    if (fragment != null) {

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.home, fragment, "HU_Detail");
                        ft.addToBackStack("HU_Detail");
                        ft.commit();

                    }


                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);

                    response = response.substring(2, response.length());
                    dialog.dismiss();
                    box.getBox("Err",response);

                } else {

                    Log.d(TAG, " Response is unknown :" + response);
                    dialog.dismiss();
                    box.getBox("Err",response);


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
                box.getBox("Err",err);
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
