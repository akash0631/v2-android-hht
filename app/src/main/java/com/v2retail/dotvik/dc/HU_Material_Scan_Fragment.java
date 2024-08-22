package com.v2retail.dotvik.dc;

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
 * {@link HU_Material_Scan_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HU_Material_Scan_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HU_Material_Scan_Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private static final String ARG_PARAM1 = "del_no";
    private static final String ARG_PARAM2 = "total_hu_scan_qty";
    private static final String ARG_PARAM3 = "hu_no";
    private static final String ARG_PARAM4 = "hu_list";
    private static final String ARG_PARAM5 = "Ean_list";
    String URL="";
    String WERKS="";
    String USER="";
    AlertBox box;
    ProgressDialog dialog;
    String requester = "";
    String total_hu_scan_qty = "";
    String hu_no = "";
    String delivery_no = "";
    String EAMMAterial = "";
    String MAterialDesc = "";
    int OpenQty = 0;
    int ScanQty = 0;
    Double ScQty = 0.0;
    Double TOpenQty = 0.0;
    int TAScan = 0;
    int totalQty = 0;
    int sum = 0;
    ArrayList<ArrayList<String>> dtScanMat;
    ArrayList<ArrayList<String>> dtEan;
    ArrayList<ArrayList<String>> dtDel;
    ArrayList<ArrayList<String>> dtMaterial;
    // TODO: Rename and change types of parameters
    ArrayList<Integer> rows_index = new ArrayList<>();
    private String TAG = HU_Material_Scan_Fragment.class.getName();

    Tables tables = new Tables();
    Context con;
    FragmentManager fm;

    Button back;
    Button mat_scan;
    Button save;
    TextView mResponseView;
    EditText hu_no_et;
    EditText del_no_et;
    EditText mat_et;
    EditText sq_et;
    EditText rq_et;
    EditText tsq_et;
    EditText toq_et;

    private OnFragmentInteractionListener mListener;

    public HU_Material_Scan_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Process_Selection_Activity) getActivity())
                .setActionBarTitle("HU Material Scan");
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
    public static HU_Material_Scan_Fragment newInstance(String param1, String param2) {
        HU_Material_Scan_Fragment fragment = new HU_Material_Scan_Fragment();
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


            total_hu_scan_qty    = getArguments().getString(ARG_PARAM2);
            delivery_no = getArguments().getString(ARG_PARAM1);
            hu_no = getArguments().getString(ARG_PARAM3);
            dtDel = (ArrayList<ArrayList<String>>) getArguments().getSerializable(ARG_PARAM4);
            dtEan = (ArrayList<ArrayList<String>>) getArguments().getSerializable(ARG_PARAM5);
            if (delivery_no != null)
                Log.d(TAG, "Delivery No: " + delivery_no);

            if (total_hu_scan_qty != null)
                Log.d(TAG, "total_hu_scan_qty : " + total_hu_scan_qty);

            if (hu_no != null)
                Log.d(TAG, "hu_no : " + hu_no);

            if (dtDel.get(0).size() > 0)
                Log.d(TAG, "del array data : " + dtDel);

            if (dtEan.get(0).size() > 0)
                Log.d(TAG, "dtEan array data : " + dtEan );

        }
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hu_material_scan, container, false);
        con = getContext();
        box=new AlertBox(con);
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

        back = (Button) view.findViewById(R.id.back);
        save = (Button) view.findViewById(R.id.save);
        mat_scan = (Button) view.findViewById(R.id.mat_scan);

        hu_no_et = (EditText) view.findViewById(R.id.hu_no); //huno
        del_no_et = (EditText) view.findViewById(R.id.del_no);//pack
        mat_et = (EditText) view.findViewById(R.id.material_no);//mat
        sq_et = (EditText) view.findViewById(R.id.sq);//ts
        rq_et = (EditText) view.findViewById(R.id.rq);//to
        tsq_et = (EditText) view.findViewById(R.id.tsq);//tsq
        toq_et = (EditText) view.findViewById(R.id.toq);//topenqty

        mat_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String crate = mat_et.getText().toString().trim();
                    if (TextUtils.isEmpty(mat_et.getText().toString().trim()) &&  crate.equals("")) {

                        box.getBox("Alert", "Enter Material No!");

                        return true;
                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mat_et.getWindowToken(), 0);

                        try {
                            setMaterialData();

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


        mResponseView = (TextView) view.findViewById(R.id.response);

        back.setOnClickListener(this);//btnExit
        mat_scan.setOnClickListener(this);
        save.setOnClickListener(this);//save

        try {
             LoadFormData();
        }catch (Exception e)
        {
            box.getErrBox(e);
        }

        return view;

    }

    private void LoadFormData() {
        if (dtScanMat != null)
            dtScanMat = null;
        if (delivery_no != null || delivery_no.length() > 0 || !delivery_no.equals("")) {
            del_no_et.setText(delivery_no);
            if (hu_no != null || hu_no.length() > 0 || !hu_no.equals("")) {
                hu_no_et.setText(hu_no);
                if (total_hu_scan_qty != null || total_hu_scan_qty.length() > 0 || !total_hu_scan_qty.equals("")) {
                    ScQty = Double.parseDouble(total_hu_scan_qty);
                    tsq_et.setText(total_hu_scan_qty);
                    mat_et.requestFocus();
                } else {
                    box.getBox("Alert", "Total HU Scan Quantity Not available!");
                    return;
                }

            } else {
                box.getBox("Alert", "HU No.Not available!");
                return;
            }
        } else {
            box.getBox("Alert", "Delivery No. Not available!");
            return;
        }
        if(dtDel.get(6).size()>0)
        for(String qty:dtDel.get(6))
        {
            TOpenQty+=Double.valueOf(qty);
        }
        toq_et.setText(String.valueOf(TOpenQty));
        dtScanMat = tables.getDeliveryTable();
        dtMaterial = tables.getMATTAble();
        dtMaterial.remove(2);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())

        {
            case R.id.save:
                try {
                      sendDataToServer();
                }catch (Exception e)
                {
                    box.getErrBox(e);
                }

                break;

            case R.id.back:

                fm.popBackStack();
                break;

            case R.id.mat_scan:
                if(CameraCheck.isCameraAvailable(con))
                IntentIntegrator.forSupportFragment(HU_Material_Scan_Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();
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
                try {
                   setMaterialData();
                }catch (Exception e)
                {
                    box.getErrBox(e);
                }

            } else {
                box.getBox("Scanner Err","No Content Received. Please Scan Again");

            }
        }


    }

    private void setMaterialData() {

        String matnr = "";
        String charg = "";
        String werks = "";
        String lgort = "";
        String vrkme = "";
        Double ormng =0.0;

        String del_no = del_no_et.getText().toString().trim();
        if (!TextUtils.isEmpty(del_no_et.getText().toString().trim())&& !del_no.equals(""))
        {
            String mat_no = mat_et.getText().toString().trim();
            if (!TextUtils.isEmpty(mat_et.getText().toString().trim())&& !mat_no.equals(""))
            {
                String arrBarQty[] = mat_no.split("-");
                ArrayList<ArrayList<String>> rows = getEANTableData(arrBarQty);
                if (rows != null && rows.size() > 0 && rows.get(0).size() > 0) {
                    EAMMAterial = rows.get(1).get(0);
                    ScanQty = Integer.valueOf(rows.get(3).get(0));
                }

                if (arrBarQty.length > 1) {
                    ScanQty =  Integer.valueOf(arrBarQty[1]);
                }

                ArrayList<ArrayList<String>> rowsPO = getDeliveryTableData(EAMMAterial);
                if (rowsPO != null && rowsPO.size() > 0 && rowsPO.get(0).size() > 0)
                {
                    matnr = rowsPO.get(1).get(0);
                    charg = rowsPO.get(3).get(0);
                    werks = rowsPO.get(1).get(0);
                    lgort = rowsPO.get(2).get(0);
                    vrkme = rowsPO.get(5).get(0);
                    ormng =Double.valueOf(rowsPO.get(6).get(0));
                    rq_et.setText(String.valueOf(ormng));
                    String str55 = matnr + "," + charg + "," + werks + "," + lgort + "," + del_no_et.getText().toString() + "," + ScanQty + "," + vrkme;
                    Log.d(TAG, "Str55 ->" + str55);

                    int sumqty = 0;
                    int i = 0;
                    int ind = 0;
                    if (dtMaterial != null)
                        if (dtMaterial.get(0).size() > 0)
                        {
                            ArrayList<ArrayList<String>> rowsMAT = tables.getMATTAble();

                            if (dtMaterial.get(0).contains(EAMMAterial))
                            {
                                rowsMAT = getMatTableData(EAMMAterial);
                            }

                            if (rowsMAT.get(0).size() > 0)
                            {

                                i = 0;
                                while (rowsMAT.get(0).size() > i)
                                {
                                    //adding row data
                                    if (rowsMAT.get(0).size() > 0) {
                                        sumqty += Integer.valueOf(rowsMAT.get(1).get(i));
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

                    if (sum <=  ormng.intValue() )
                    {
                        ScQty = ScQty + ScanQty ;
                        tsq_et.setText(String.valueOf(ScQty));
                        dtMaterial.get(0).add(EAMMAterial);
                        dtMaterial.get(1).add(String.valueOf(ScanQty));
                        if (sum == 0) {
                            sq_et.setText(String.valueOf(ScanQty));
                        } else {
                            sq_et.setText(String.valueOf(sum));
                        }

                        dtScanMat.get(0).add(matnr);
                        dtScanMat.get(1).add(charg);
                        dtScanMat.get(2).add(werks);
                        dtScanMat.get(3).add(lgort);
                        dtScanMat.get(4).add(del_no_et.getText().toString().trim());
                        dtScanMat.get(5).add(String.valueOf(ScanQty));
                        dtScanMat.get(6).add(vrkme);

                        mat_et.setText("");
                        mat_et.requestFocus();
                    } else {
                        box.getBox("Msg", "Scanned Qty can't be greated than Open Qty");
                        mat_et.setText("");
                        mat_et.requestFocus();
                    }


                } else {
                    box.getBox("Alert!!", "Invalid Material");
                    mat_et.setText("");
                    mat_et.requestFocus();
                }


            }


        } else {
            box.getBox("Alert", "First enter packaging material");
        }
    }

    private void sendDataToServer() {
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg = null;
                StringBuilder Stuz = new StringBuilder();
                String addrec = null;

                msg = del_no_et.getText().toString().trim() + "," + hu_no_et.getText().toString().trim();
                int i = 0, j = 0;
                int len=0;
                int lenItem=0;
                if(dtScanMat.size()>0 &&dtScanMat.get(0).size()>0) {
                    len = dtScanMat.get(0).size();
                    lenItem = dtScanMat.size();
                }
               /* else {
                    dialog.dismiss();
                    box.getBox("Alert","No Scanned Material Found! Please Scan Data First");
                    return;
                }*/
                while (len > i) {
                    j = 0;
                    while (lenItem > j) {

                        Stuz.append("," + dtScanMat.get(j).get(i));
                        j++;
                    }
                    i++;
                }

                addrec = msg + Stuz.toString();
                requester = "save";
                String valueRequestPayload = "savehudetails#" + addrec + "#<eol>";

                Log.d(TAG, "payload to server -> " + valueRequestPayload);
                try {
                    sendAndRequestResponse(valueRequestPayload);
                }catch (Exception e)
                {   dialog.dismiss();
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

                Log.d(TAG, "response ->" + response);

                if (response == null) {
                    dialog.dismiss();
                    box.getBox("Err","No response from Server");

                }
                if (response.equals("")) {
                    dialog.dismiss();
                    box.getBox("Err","Empty response from Server/ Unable to connect");

                } else if (response.charAt(0) == ('S')) {
                    dialog.dismiss();
                    response = response.substring(2, response.length());

                    Log.d("tag", "  response ->" + response);

                    if (requester.equals("save")) {

                        box.getBox("Response", response);
                        mat_et.setText("");
                        tsq_et.setText("");
                        sq_et.setText("");
                        rq_et.setText("");
                        toq_et.setText("");
                        if (dtMaterial.get(0).size() > 0) {
                            dtMaterial.get(0).clear();
                            dtMaterial.get(1).clear();
                        }
                        if (dtScanMat != null) {
                            dtScanMat = null;
                        }

                        fm.popBackStack();
                    }


                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);

                    response = response.substring(2, response.length());

                    if (requester.equals("save")) {

                        box.getBox("Alert", response);
                        mat_et.setText("");
                        if (dtScanMat != null) {
                            dtScanMat = null;
                        }
                    }


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

    private ArrayList<ArrayList<String>> getEANTableData(String[] arrBarQty) {
        ArrayList<ArrayList<String>> rows = tables.getEANTAble("");
        int i = 0;
        rows_index.clear();
        i = 0;
        if (arrBarQty != null)
            if (dtEan.size() > 3)
                while (dtEan.get(2).size() > i)
                {
                    if (dtEan.get(2).get(i).contains(arrBarQty[0])) {//getting index of row that contains ean11=arrbarqty[0]
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
                if (rows.size() > 3) {
                    rows.get(0).add(dtEan.get(0).get(ind));
                    rows.get(1).add(dtEan.get(1).get(ind));
                    rows.get(2).add(dtEan.get(2).get(ind));
                    rows.get(3).add(dtEan.get(3).get(ind));
                }

                i++;
            }
        Log.d(TAG, " ean11=arrbarqty[0] Dtean  : " + rows);
        return rows;

    }

    private ArrayList<ArrayList<String>> getDeliveryTableData(String EAMMAterial) {
        ArrayList<ArrayList<String>> rowsPO = tables.getDeliveryTable();
        int i = 0;
        rows_index.clear();

        while (dtDel.get(0).size() > i) {
            if (dtDel.get(0).get(i).contains(EAMMAterial)) {
                //getting index of rows that contains matnr=EAMMAterial
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
                if (rowsPO.size() < 8) {
                    rowsPO.get(0).add(dtDel.get(0).get(ind));
                    rowsPO.get(1).add(dtDel.get(1).get(ind));
                    rowsPO.get(2).add(dtDel.get(2).get(ind));
                    rowsPO.get(3).add(dtDel.get(3).get(ind));
                    rowsPO.get(4).add(dtDel.get(4).get(ind));
                    rowsPO.get(5).add(dtDel.get(5).get(ind));
                    rowsPO.get(6).add(dtDel.get(6).get(ind));

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
                }
                i++;
            }

        Log.d(TAG, " matnr=EAMMAterial Dtpo  :" + rowsMAT);
        return rowsMAT;
    }


    /*    * This interface must be implemented by activities that contain this
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
