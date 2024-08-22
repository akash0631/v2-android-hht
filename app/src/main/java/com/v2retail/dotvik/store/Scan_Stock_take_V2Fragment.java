package com.v2retail.dotvik.store;

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
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Scan_Stock_take_V2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Scan_Stock_take_V2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Scan_Stock_take_V2Fragment extends Fragment implements View.OnClickListener,TextView.OnEditorActionListener, View.OnKeyListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private static final String ARG_PARAM1 = "stockid";
    private static final String ARG_PARAM2 = "dtStockBinPO";
    String URL="";
    String WERKS="";
    String USER="";
    String requester = "";
    String scanner = "";
    String stockId = "";
    ArrayList<ArrayList<String>> dtStockBinPO;
    ArrayList<ArrayList<String>> dtEan;
    ArrayList<ArrayList<String>> dtPO;
    ArrayList<ArrayList<String>> dtMaterial;
    ArrayList<ArrayList<String>> rows1;
    ArrayList< String > data;
    ArrayList< Integer > rows_index=new ArrayList<>();
    HashMap<String,String> articleScanQty=new HashMap<>();
    // TODO: Rename and change types of parameters
    private String TAG = Scan_Stock_take_V2Fragment.class.getName();

    Tables tables = new Tables();
    Context con;
    AlertBox box;
    ProgressDialog dialog;
    FragmentManager fm;

    Button back;
    Button barcode_scan;
    Button save;
    Button bin_scan;
    EditText bin_et;
    EditText sq_et;
    EditText barcode_et;
    EditText tsq_et;
    EditText article_et;
    EditText totalsq_et;
    EditText asq_et;
    int ScanQty = 0;
    int OpenQty = 0;
    int ScQty = 0;
    int sum = 0;
    private OnFragmentInteractionListener mListener;

    public Scan_Stock_take_V2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("Scan Stock Take");
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
    public static Scan_Stock_take_V2Fragment newInstance(String param1, String param2) {
        Scan_Stock_take_V2Fragment fragment = new Scan_Stock_take_V2Fragment();
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

            stockId =   getArguments().getString(ARG_PARAM1);
            dtStockBinPO = (ArrayList<ArrayList<String>>) getArguments().getSerializable(ARG_PARAM2);


        }
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_floor_putway, container, false);
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
        barcode_scan = (Button) view.findViewById(R.id.barcode_scan);
        save = (Button) view.findViewById(R.id.save);
        bin_scan = (Button) view.findViewById(R.id.bin_scan);

        totalsq_et = (EditText) view.findViewById(R.id.total_scan_qty);
        bin_et = (EditText) view.findViewById(R.id.bin_no);
        article_et = (EditText) view.findViewById(R.id.article_no);
        tsq_et = (EditText) view.findViewById(R.id.tsq);
        asq_et = (EditText) view.findViewById(R.id.asq);
        sq_et = (EditText) view.findViewById(R.id.stock_qty);
        barcode_et = (EditText) view.findViewById(R.id.barcode_no);

        back.setOnClickListener(this);
        barcode_scan.setOnClickListener(this);
        bin_scan.setOnClickListener(this);
        save.setOnClickListener(this);

        barcode_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    /*|| (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)*/) {
                    String bin=bin_et.getText().toString().trim();
                    String barcode=barcode_et.getText().toString().trim();
                    if (!bin.equals("")) {
                        if (!barcode.equals("")) {
                            try {
                                setFormData();
                            } catch (Exception e) {

                                box.getErrBox(e);
                                return true;
                            }


                        } else {

                            box.getBox("Alert!!", "First Scan Bar Number");
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(barcode_et.getWindowToken(), 0);
                            barcode_et.requestFocus();
                            return true;
                        }
                    }else{
                        box.getBox("Alert!!", "First Scan Bin Number");
                        bin_et.requestFocus();
                    }


                }
                return false;
            }
        });

        bin_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        ) {

                    String bin = bin_et.getText().toString();
                    if ( TextUtils.isEmpty(bin_et.getText().toString().trim())&& bin.equals("")) {

                        box.getBox("Alert", "Invalid Bin ");
                        bin_et.requestFocus();
                        return true;
                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(bin_et.getWindowToken(), 0);

                        setBinData();
                        return true;
                    }


                }
                return false;
            }
        });

        loadData();
        return view;

    }

    private void loadData() {
        if(dtPO!=null)dtPO=null;
        if(dtEan!=null)dtEan=null;
        if(dtMaterial!=null)dtMaterial=null;

        dtEan=tables.getEANTAble("floor");
        dtMaterial=tables.getMATTAble();

    }

    

    private void setBinData()
    {
            if(dtStockBinPO.get(2).contains(bin_et.getText().toString().trim()))
            {
                barcode_et.requestFocus();
            }
            else {
                    box.getBox("Alert","Invalid Bin");
                    bin_et.setText("");
                    bin_et.requestFocus();
                }


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

            case R.id.bin_scan:
                scanner="bin";
                if(CameraCheck.isCameraAvailable(con))
                IntentIntegrator.forSupportFragment(Scan_Stock_take_V2Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();
                break;
            case R.id.bar_scan:
                scanner="bar";
                if(CameraCheck.isCameraAvailable(con))
                IntentIntegrator.forSupportFragment(Scan_Stock_take_V2Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();
                break;
            case R.id.crate_scan:
                scanner="crate";
                if(CameraCheck.isCameraAvailable(con))
                IntentIntegrator.forSupportFragment(Scan_Stock_take_V2Fragment.this).setBeepEnabled(true).setOrientationLocked(true).setTimeout(10000).initiateScan();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult == null ) {

            Log.d(TAG, TAG + " scanned result...");
            box.getBox("Scanner Err","Unable to receive Data");

        } else {
            Log.d(TAG, "Scan data received");
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            Log.v(TAG, "scanContent = " + scanContent);
            Log.v(TAG, "scanFormat = " + scanFormat);

            if (scanContent != null && !scanContent.equals("null")) {
                switch (scanner)

                {
                    case "bin":
                                     bin_et.setText(scanContent);
                                     Log.v(TAG, "bin code = " + scanContent);
                                     try {
                                         setBinData();
                                     }catch (Exception e)
                                     {
                                         box.getErrBox(e);
                                     }
                                     break;
                    case "bar":
                                     barcode_et.setText(scanContent);
                                  try {
                                      setFormData();
                                  }catch (Exception e)
                                  {
                                      box.getErrBox(e);
                                  }
                                     Log.v(TAG, "barcode code = " + scanContent);

                                    break;

                }


            } else {
                box.getBox("Scanner Err","No Content Received. Please Scan Again");

            }
        }


    }




    private void validateBarcode(final String barcode) {
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String addrec = "";
                addrec =barcode +"#"+WERKS;
                requester = "stockvalidatebarcode";
                String valueRequestPayload = "validatestoreean#" + addrec + "#<eol>";
                Log.d(TAG, "payload to server -> " + valueRequestPayload);
                try {

                    sendAndRequestResponse(valueRequestPayload);
                }catch (Exception e)
                {    dialog.dismiss();
                    box.getErrBox(e);
                    e.printStackTrace();
                }


            }
        }, 2000);
    }
    private void setBarcodeData(String response) {

        String[] arrayEanData=response.split("#");

       
        for(int i=0;i<=arrayEanData.length-5;)
        {
            dtEan.get(0).add(arrayEanData[i]);
            dtEan.get(1).add(arrayEanData[i+1]);
            dtEan.get(2).add(arrayEanData[i+2]);
            dtEan.get(3).add(arrayEanData[i+3]);
            dtEan.get(4).add(arrayEanData[i+4]);
            dtEan.get(5).add("0");
            String temp =arrayEanData[i]+","+arrayEanData[i+1]+","+arrayEanData[i+2]+","+arrayEanData[i+3]+","+arrayEanData[i+4]+", 0";
            Log.d(TAG,"Item Added "+i +" :  "+temp);
            i+=5;
        }

       processData(barcode_et.getText().toString().split("-"));
    }
//uPDATES

    private void setFormData() {

       String barcode[] = barcode_et.getText().toString().split("-");
        rows1 = getEANTableData(barcode[0]);
        if (rows1.get(0).size() > 0) {
            try {
                processData(barcode);
            } catch (Exception e) {
                box.getErrBox(e);
            }
        } else {
            try {
                validateBarcode(barcode_et.getText().toString().trim());

            } catch (Exception e) {
                box.getErrBox(e);
            }


        }


    }

    private void processData( String[] arrBarQty) {
        String EANNR = null;
        String Str55 = null;
        String EAMMAterial = null;

        int i = 0;
        int ind = 0;
        rows1=getEANTableData(arrBarQty[0]);
        if (rows1.get(2).size() < 0) {
            box.getBox("Err","Article not found: "+arrBarQty[0]);
            return;
        }

        EAMMAterial = rows1.get(1).get(0);
        ScanQty = Integer.valueOf(rows1.get(4).get(0));
        //arrBarQty = txt_Article.Text.split('-');
        if (arrBarQty.length > 1)
            ScanQty = Integer.valueOf(arrBarQty[1]);

        article_et.setText(EAMMAterial.replaceFirst("^0+(?!$)", ""));
        sq_et.setText(rows1.get(5).get(0));
        if(rows1.get(5).get(0).length()>0)
        OpenQty = (int) Math.floor(Double.valueOf(sq_et.getText().toString()));
        else OpenQty=0;

        Str55 = EAMMAterial + "," + ScanQty + "," + bin_et.getText().toString();

        if (dtMaterial != null)
            if (dtMaterial.get(0).size() > 0) {
                ArrayList<ArrayList<String>> rowsMAT = tables.getMATTAble();

                if (dtMaterial.get(0).contains(EAMMAterial)) {
                    rowsMAT = getMatTableData(EAMMAterial);
                }

                if (rowsMAT.get(0).size() > 0) {

                    i = 0;
                    int sumqty = 0;
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

        /* if (sum <= OpenQty) {*/
            ScQty = ScQty + ScanQty;
            asq_et.setText(String.valueOf(sum));
        /*    dtMaterial.get(2).add(EAMMAterial);
            dtMaterial.get(0).add(String.valueOf(ScanQty));
            dtMaterial.get(1).add(bin_et.getText().toString());*/
		dtMaterial.get(0).add(EAMMAterial);
		dtMaterial.get(1).add(String.valueOf(ScanQty));
		dtMaterial.get(2).add(bin_et.getText().toString());

            totalsq_et.setText(String.valueOf(ScQty));
            if (sum == 0) {
                tsq_et.setText(String.valueOf(ScanQty));
            } else {
                tsq_et.setText(String.valueOf(sum));
            }


            barcode_et.setText("");
            barcode_et.requestFocus();
        /*} else {
            box.getBox("Alert", "Scanned Qty can't be greater than Open Qty");

            barcode_et.setText("");
            barcode_et.requestFocus();

        } */
    }
    private void sendDataToServer() {
        if(dtMaterial.get(0).size()<0 )
        {
            box.getBox("Err","No Data Scanned");
            return;
        }
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
             //  String key = scrate_et.getText().toString().trim()+bin_et.getText().toString().trim()+article_et.getText().toString().trim();
             //   String key="";
                 msg =   WERKS + ","+stockId+","+bin_et.getText().toString()+","+USER;
                int i = 0, j = 0;
                int len =0,lenItem=0;
                if(dtMaterial.size()>0 && dtMaterial.get(0).size()>0 ) {
                    len = dtMaterial.get(0).size();
                   // lenItem = dtScanedStocktake.size();
                }
                Log.d(TAG,"articleScanQty-> "+articleScanQty);
                while (len > i) {
					    Stuz.append( ",");
					    Stuz.append( stockId);
                        Stuz.append( ",");
                        Stuz.append( dtMaterial.get(2).get(i));
                        Stuz.append( ",");
                        Stuz.append( dtMaterial.get(0).get(i) );
                         Stuz.append( ",");//client
                        Stuz.append( dtMaterial.get(1).get(i));//stock take


                        i++;
                }

                msg=msg+Stuz.toString();
                //addrec=msg.substring(0,msg.length()-1);
                addrec=msg;

                requester = "save";
                String valueRequestPayload = "storestidpost#" + addrec + "#<eol>";

                Log.d(TAG, "payload to server -> " + valueRequestPayload);
                try {
                    sendAndRequestResponse(valueRequestPayload);
                }catch (Exception e)
                {   dialog.dismiss();
                    box.getErrBox(e);
                    e.printStackTrace();
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
                dialog.dismiss();
                Log.d(TAG, "response ->" + response);

                if (response == null) {

                    box.getBox("Err","No response from Server");

                }
                if (response.equals("")|| response.equals("null")) {

                    box.getBox("Err","Empty response from Server/ Unable to connect");

                } else if (response.charAt(0) == ('S'))
                {

                    response = response.substring(2, response.length());

                    Log.d("tag", "  response ->" + response);

                    if (requester.equals("save"))
                    {

                        box.getBox("Response", response);

                        tsq_et.setText("");
                        sq_et.setText("");
                        bin_et.setText("");
                        barcode_et.setText("");
                        article_et.setText("");
                        bin_et.setText("");
                        asq_et.setText("");
                        totalsq_et.setText("");
                        ScanQty=0;


                        if (dtMaterial.get(0).size() > 0)
                        {
                            dtMaterial.clear();

                        }

                        fm.popBackStack();
                    }
                    if(requester.equals("stockvalidatebarcode"))
                    {
                       try {
                           setBarcodeData(response);
                       }catch (Exception e)
                       {
                           box.getErrBox(e);
                       }
                    }


                } else if (response.charAt(0) == ('E')) {
                    Log.d(TAG, " Response is Failure -> Invalid Invoice :" + response);

                    response = response.substring(2, response.length());
                    box.getBox("Err",response);

                } else {

                    Log.d(TAG, " Response is unknown :" + response);

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

    private ArrayList<ArrayList<String>> getEANTableData(String barcode) {
        ArrayList<ArrayList<String>> rows = tables.getEANTAble("floor");
        int i = 0;
        //   i=dtEan.get(2).size();
        i = 0;
        rows_index.clear();
        if (barcode != null)
            if (dtEan.size() > 3)
                while (dtEan.get(2).size() > i) {
                    if (dtEan.get(2).get(i).equals(barcode)) {//getting index of row that contains ean11=arrbarqty[0]
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
                if (dtEan.size() >5) {

                    rows.get(0).add(dtEan.get(0).get(ind));

                    rows.get(1).add(dtEan.get(1).get(ind));

                    rows.get(2).add(dtEan.get(2).get(ind));

                    rows.get(3).add(dtEan.get(3).get(ind));
                    rows.get(4).add(dtEan.get(4).get(ind));
                    rows.get(5).add(dtEan.get(5).get(ind));



                }
                i++;
            }
        Log.d(TAG, " ean11=arrbarqty[0] Dtean  : " + rows);
        return rows;

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

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
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
