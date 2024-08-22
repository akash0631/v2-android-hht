package com.v2retail.dotvik.store;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.v2retail.dotvik.R;
import com.v2retail.util.AlertBox;
import com.v2retail.util.SharedPreferencesData;
import com.v2retail.util.Tables;

import java.util.ArrayList;
import java.util.Calendar;

public class TRFDispToProc extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "TYPE";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private String TAG = TRFDispToProc.class.getName();
    String[] arrBarQty;
    ArrayList<ArrayList<String>> rows1;
    Tables tables = new Tables();
    AlertBox box;
    Context con;
    String URL = "";
    String WERKS = "";
    String USER = "";

    FragmentManager fm;
    Button next;
    Button back;
    String werks = "";
    String Users = "1";
    String TYPE = "";
    TextView mResponseView;
    Spinner pack_mat_spinner;
    ProgressDialog dialog;
    EditText store_name;
    EditText source_sloc;
    EditText dest_sloc;
    EditText date;
    String requester = "";
    ArrayList<ArrayList<String>> dtSourceSloc;
    ArrayList<ArrayList<String>> dtDestSloc;
    ArrayList<ArrayList<String>> dtPacMat;
    ArrayList<String> dtBin;
    ArrayList<ArrayList<String>> dtMaterial;
    private OnFragmentInteractionListener mListener;
    DatePickerDialog datePickerDialog;

    public TRFDispToProc() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home_Activity) getActivity())
                .setActionBarTitle("TRF Display To Process");
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
    public static TRFDispToProc newInstance(String param1, String param2) {
        TRFDispToProc fragment = new TRFDispToProc();
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

            try {
                TYPE = getArguments().getString(ARG_PARAM1);
            } catch (Exception ex) {
                Log.d(TAG, "Exception " + ex.getMessage());
                box.getErrBox(ex);
            }


        }
        fm = getFragmentManager();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trfdisplaytoproc, container, false);


        con = getContext();
        box = new AlertBox(con);

        SharedPreferencesData data = new SharedPreferencesData(con);

        WERKS = data.read("WERKS");

        if (!WERKS.isEmpty())
            Log.d(TAG, "WERKS->" + WERKS);

        dialog = new ProgressDialog(con);
        back = (Button) view.findViewById(R.id.back);
        next = (Button) view.findViewById(R.id.next);

        source_sloc = (EditText) view.findViewById(R.id.source_sloc);
        dest_sloc = (EditText) view.findViewById(R.id.dest_sloc);
        store_name = (EditText) view.findViewById(R.id.store_name);
        date = (EditText) view.findViewById(R.id.date);
        mResponseView = (TextView) view.findViewById(R.id.response);

        date.setFocusable(false);
        date.setClickable(true);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month,
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(con,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "\\"
                                        + (monthOfYear + 1) + "\\" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        store_name.setText(WERKS);
        back.setOnClickListener(this);
        next.setOnClickListener(this);

        next.requestFocus();

        try {
            loadData();
        } catch (Exception e) {
            box.getErrBox(e);
        }
        return view;
    }

    private void loadData() {
        if (TYPE == null || TYPE.equals("") || TYPE.length() < 0) {

            box.getBox("ERR", "TYpe NOt FOund!!!");
            return;
        } else {
            Log.d(TAG, "TYPE ->" + TYPE);
        }

        if (dtSourceSloc != null) {
            dtSourceSloc = null;

        }

        if (dtDestSloc != null) {
            dtDestSloc = null;

        }
        if (TYPE.equals("1"))//Display to process
        {
            source_sloc.setText("0001");
            dest_sloc.setText("0008");
        }
        if (TYPE.equals("2"))//DC TO Process
        {
            source_sloc.setText("0002");
            ;
            dest_sloc.setText("0008");
            ;
        }
        if (TYPE.equals("3"))//Process TO DISPLAY
        {
            source_sloc.setText("0008");
            ;
            dest_sloc.setText("0001");
            ;
        }
        if (TYPE.equals("4"))//Display to reverce process
        {
            source_sloc.setText("0001");
            ;
            dest_sloc.setText("0010");
            ;
        }
        if (TYPE.equals("5"))//Base stock to display
        {
            source_sloc.setText("0009");
            ;
            dest_sloc.setText("0001");
            ;
        }
        if (TYPE.equals("6"))//Display to base stock
        {
            source_sloc.setText("0001");
            ;
            dest_sloc.setText("0009");
            ;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())

        {
            case R.id.next:

                String dateStr = date.getText().toString();
                if (dateStr == null || dateStr.length() < 0 || dateStr.equals("") || dateStr.equals("null")) {
                    box.getBox("Error", "Please Select Date First!!");
                    return;
                }

                Bundle args = new Bundle();
                args.putString("dest", dest_sloc.getText().toString());
                args.putString("source", source_sloc.getText().toString());


                Fragment fragment = new Scan_TRFDispToProc_Fragment();
                fragment.setArguments(args);
                if (fragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.home, fragment, "trf");
                    ft.addToBackStack("trf");
                    ft.commit();
                }


                break;

            case R.id.back:
                checkClick(TYPE);
                break;


        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        //  fm.popBackStackImmediate();
        checkClick(TYPE);
    }

    private void checkClick(String type) {

        Fragment fragment = null;
        switch (type) {
            case "5":
            case "6":
                fragment = new DisplayAreaProcessFragment();
                break;
            case "4":
                fragment = new InboundFragment();
                break;
            default:
                fragment = new OutboundFragment();

        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.home, fragment, "Trf");

            ft.commit();
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
