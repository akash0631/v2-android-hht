package com.v2retail.dotvik.store;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.v2retail.dotvik.R;
import com.v2retail.util.ArticleDetailAdapter;
import com.v2retail.util.ArticleDetailModal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Article_Detail_Variant_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Article_Detail_Variant_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Article_Detail_Variant_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "data";
    private static final String ARG_PARAM2 = "param2";
    Button close;
    FragmentManager fm;
    Context con;
    TextView ean_et;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = Article_Detail_Variant_Fragment.class.getName();
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private ArticleDetailAdapter adapter;
    private List<ArticleDetailModal> albumList;
    ArrayList<HashMap<String,String>> dataList;
    public Article_Detail_Variant_Fragment() {
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
    public static Article_Detail_Variant_Fragment newInstance(String param1, String param2) {
        Article_Detail_Variant_Fragment fragment = new Article_Detail_Variant_Fragment();
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
                .setActionBarTitle("Article Detail");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataList = ( ArrayList<HashMap<String,String>>) getArguments().getSerializable("data");


         
        }
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_stock_sales_detail, container, false);
        con = getContext();


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        ean_et = (TextView) view.findViewById(R.id.ean_et);

        albumList = new ArrayList<>();
        adapter = new ArticleDetailAdapter(con, albumList);

        recyclerView.setAdapter(adapter);
        close = (Button) view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStack();
            }
        });
        prepareArticle();

        /*try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        return view;
    }

    private void prepareArticle() {
        int temp=0;
        if(dataList!=null)
        for(HashMap<String,String> args :dataList)
        { ArticleDetailModal model=new ArticleDetailModal();
           model.setEan(args.get("IM_EAN"));
           model.setArticle(args.get("MATNR").replaceFirst("^0+(?!$)", ""));
           model.setMatDesc(args.get("MAKTX" ));
           model.setVo1(args.get("V01" ));
           model.setOne(args.get("0001" ));
           model.setThree(args.get("0003" ));
           model.setFour(args.get("0004" ));
           model.setFive(args.get("0005" ));
           model.setSix(args.get("0006" ));
           model.setSeven(args.get("0007" ));
           model.setEight(args.get("0008" ));
           model.setNine(args.get("0009" ));
           model.setTen(args.get("0010" ));
           model.setMsa(args.get("MSA" ));
            temp=0;


                temp+=Double.valueOf(args.get("V01" )).intValue();
                temp+=Double.valueOf(args.get("0001" )).intValue();
                temp+=Double.valueOf(args.get("V04" )).intValue();
                temp+=Double.valueOf(args.get("0004" )).intValue();
                temp+=Double.valueOf(args.get("0003" )).intValue();
                temp+=Double.valueOf(args.get("0005" )).intValue();
                temp+=Double.valueOf(args.get("0006" )).intValue();
                temp+=Double.valueOf(args.get("0007" )).intValue();
                temp+=Double.valueOf(args.get("0008" )).intValue();
                temp+=Double.valueOf(args.get("0009" )).intValue();
                temp+=Double.valueOf(args.get("0010" )).intValue();
                temp+=Double.valueOf(args.get("MSA" )).intValue();


            model.setTotal(String.valueOf(temp));

           albumList.add(model);
        }
        ean_et.setText(dataList.get(0).get("IM_EAN"));
        adapter.notifyDataSetChanged();
    }




    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }  // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        //fm.popBackStackImmediate();
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
