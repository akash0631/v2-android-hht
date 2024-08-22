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
import com.v2retail.util.SharedPreferencesData;
import com.v2retail.util.Tables;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Picking_Delivery_Scan_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Picking_Delivery_Scan_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Picking_Delivery_Scan_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String URL="";
    String WERKS="";
    String USER="";
    ProgressDialog  dialog;
    AlertBox box;

    private static final String TAG = Picking_Delivery_Scan_Fragment.class.getName();

    ArrayList<ArrayList<String>> dtDel;
    ArrayList<ArrayList<String>> dtEAN;
    ArrayList<ArrayList<String>> dtPac;
    Tables tables = new Tables();
    TextView mResponseView;
    Button next;
    Button back;
    EditText date;
    EditText delivery_no;
    EditText invoice_number;
    Context con;
    FragmentManager fm;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String scanner = "";
    private String requester = "";

    private OnFragmentInteractionListener mListener;

    public Picking_Delivery_Scan_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("Delivery Scan");
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
    public static Picking_Delivery_Scan_Fragment newInstance(String param1, String param2) {
        Picking_Delivery_Scan_Fragment fragment = new Picking_Delivery_Scan_Fragment();
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

    private void getPackMaterial() {
        final String addrec = delivery_no.getText().toString();

        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                requester = "packingMat";
                String valueRequestPayload = "packgingmaterial#" + addrec + "#<eol>";
                Log.d(TAG, "payload : " + valueRequestPayload);
                Log.d(TAG, "payload sent to server ");


                try {

                     sendAndRequestResponse(valueRequestPayload);
                }catch (Exception e)
                {    dialog.dismiss();
                    box.getErrBox(e);
                }

            }
        }, 2000);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.delivery_scan, container, false);

        con = getContext();
        box=new AlertBox(con);
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

        delivery_no = (EditText) view.findViewById(R.id.delivery_no);
        mResponseView = (TextView) view.findViewById(R.id.response);

        next = (Button) view.findViewById(R.id.next);
        back = (Button) view.findViewById(R.id.back);

        back.setOnClickListener(this);
        next.setOnClickListener(this);


        Log.d(TAG, TAG + " created");
        try {
             getPackMaterial();
        }catch (Exception e)
        {
            box.getErrBox(e);
        }

        return view;
    }

    private void scnDelivery() {

        final String addrec = delivery_no.getText().toString().trim();
        if(TextUtils.isEmpty(delivery_no.getText().toString().trim())&& addrec.equals(""))
        {
            box.getBox("Alert","Please Enter Delivery No");
            return;
        }
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                requester = "delivery";
                String valueRequestPayload = "scndelivery#" + addrec + "#<eol>";
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
                dialog.dismiss();
                Log.d(TAG, "response :" + response);

                if (response == null) {

                    box.getBox("Err", "No response from Server");

                } else if (response.equals("")) {
                    Log.d(TAG, " Response is Failure -> " + response);
                    box.getBox("Err", "Unable to Connect Server/ Empty Response");


                } else if (response.charAt(0) == ('S')) {


                    Log.d(TAG, " Response :Invoice Check " + response);
                    response = response.substring(2, response.length());
                    if (requester.equals("packingMat"))
                        try {
                            loadPackMaterialFromServer(response);
                        }catch (Exception e)
                        {
                            box.getErrBox(e);
                        }


                    if (requester.equals("delivery"))
                        try {
                            loadDataFromServer(response);
                        }catch (Exception e)
                        {
                            box.getErrBox(e);
                        }



                }else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);

                    response = response.substring(2, response.length());

                    box.getBox("Err", response);

                } else {

                    Log.d(TAG, " Response is unknown :" + response);

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
                 box.getBox("Scanner Err",err);
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

    private void loadPackMaterialFromServer(String resp) {

        resp = resp.substring(2, resp.length());
        String arrayRcvdData[] = resp.split("#");
//Response:S#
// 300#V2R#000000001512002115#GUNNY BAG-NA-NA-44"X54"PLASTIC BAG#
// 300#V2R#000000001512002116#GUNNY BAG-30"X44"BAG HDPE#
// 300#V2R#000000001512002250#VENDOR PACKING MATERIAL#
        dtPac=tables.getPacMatTable("");
        dtPac.get(0).add("select");
        dtPac.get(1).add("0");
        dtPac.get(2).add("0");
        dtPac.get(3).add("select");
        for (int lk = 0; lk <= arrayRcvdData.length - 4; ) {

            dtPac.get(0).add(arrayRcvdData[lk]);
            dtPac.get(1).add(arrayRcvdData[lk + 1]);
            dtPac.get(2).add(arrayRcvdData[lk + 2]);
            dtPac.get(3).add(arrayRcvdData[lk + 3]);
            lk = lk + 4;
        }


      Log.d(TAG,"DTpac -> "+dtPac);
      Log.d(TAG,"Data Loaded Successfully From Server");


    }

    private void loadDataFromServer(String resp) {
//Response:
// S#
// 000000001112019413#DH24#0001##5.000#EA#0.000#
// 000000001121026991#DH24#0001##5.000#EA#5.000#
// 000001110012750021#DH24#0001##5.000#EA#5.000#
// 000001110012428008#DH24#0001##5.000#EA#5.000#
// 000001110012428009#DH24#0001##5.000#EA#5.000#!
// 300#000000001112019413#81000024#1#
// 300#000000001121026991#81000100#1#
// 300#000001110012428008#10055891#1#
// 300#000001110012428009#10055892#1#
// 300#000001110012428009#21068012#6#
// 300#000001110012750021#10051454#1#
// 300#000001110012428009#21068013#6#

        resp = resp.substring(2, resp.length());
        String arrayRcvdData[] = resp.split("!");
        String[] arrayDelData = arrayRcvdData[0].split("#");
        String[] arrayEanData = arrayRcvdData[1].split("#");


        List<String> data_list = Arrays.asList(arrayDelData);
        Log.d(TAG, "Del data :" + data_list);

        data_list = Arrays.asList(arrayEanData);
        Log.d(TAG, "ean data :" + data_list);

        dtEAN = tables.getEANTAble("del");
        for (int lk = 0; lk <= arrayEanData.length - 4; ) {
            dtEAN.get(0).add(arrayEanData[lk]);
            dtEAN.get(1).add(arrayEanData[lk + 1]);
            dtEAN.get(2).add(arrayEanData[lk + 2]);
            dtEAN.get(3).add(arrayEanData[lk + 3]);

            lk = lk + 4;
        }

        Log.d(TAG, "EAN array data :" + dtEAN);
        dtDel = tables.getDeliveryTable();
        for (int lk = 0; lk <= arrayDelData.length - 7; ) {

            dtDel.get(0).add(arrayDelData[lk]);
            dtDel.get(1).add(arrayDelData[lk + 1]);
            dtDel.get(2).add(arrayDelData[lk + 2]);
            dtDel.get(3).add(arrayDelData[lk + 3]);
            dtDel.get(4).add(arrayDelData[lk + 4]);
            dtDel.get(5).add(arrayDelData[lk + 5]);
            dtDel.get(6).add(arrayDelData[lk + 6]);
            lk = lk + 7;
        }
        Log.d(TAG, "Del array data :" + dtDel);




        Bundle args = new Bundle();
        args.putString("Delivery_no", delivery_no.getText().toString());
        args.putSerializable("dtDel", dtDel);
        args.putSerializable("dtEan", dtEAN);
        args.putSerializable("dtPac", dtPac);
        Fragment fragment = new Scan_Packing_Material_Fragment();
        fragment.setArguments(args);
        if (fragment != null) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "Picking_delivery");
            ft.addToBackStack("Picking_delivery");
            ft.commit();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())

        {
            case R.id.next:
                try {
                    scnDelivery();

                }catch (Exception e)
                {
                    box.getErrBox(e);
                }

                break;
            case R.id.back:
                if (dtDel != null)
                      dtDel.clear();
                if (dtEAN != null)
                    dtEAN.clear();

                fm.popBackStack();

                break;


        }
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
