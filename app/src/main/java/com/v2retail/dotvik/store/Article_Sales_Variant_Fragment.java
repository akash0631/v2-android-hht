package com.v2retail.dotvik.store;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.v2retail.dotvik.R;
import com.v2retail.util.ArticleSaleAdapter;
import com.v2retail.util.ArticleSaleModal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Article_Sales_Variant_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Article_Sales_Variant_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Article_Sales_Variant_Fragment extends Fragment {
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
    private String TAG = Article_Sales_Variant_Fragment.class.getName();
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private ArticleSaleAdapter adapter;
    private List<ArticleSaleModal> albumList;
    ArrayList<HashMap<String,String>> dataList;
    public Article_Sales_Variant_Fragment() {
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
    public static Article_Sales_Variant_Fragment newInstance(String param1, String param2) {
        Article_Sales_Variant_Fragment fragment = new Article_Sales_Variant_Fragment();
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
        View view = inflater.inflate(R.layout.article_detail_variant, container, false);
        con = getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        ean_et = (TextView) view.findViewById(R.id.ean_et);

        albumList = new ArrayList<>();
        adapter = new ArticleSaleAdapter(con, albumList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(con,1 );
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
        {
            ArticleSaleModal model=new ArticleSaleModal();
           //model.setEan(args.get("IM_EAN"));
           model.setArticle(args.get("MATNR").replaceFirst("^0+(?!$)", ""));
           model.setYtd_sale_v(args.get("YTD_NVAL" ));
           model.setYtd_sale_q(args.get("YTD_QTY" ));
           model.setTd_sale_q(args.get("TD_QTY" ));
           model.setTd_sale_v(args.get("TD_NVAL" ));
           model.setMtd_sale_v(args.get("MTD_NVAL" ));
           model.setMtd_sale_q(args.get("MTD_QTY" ));
           model.setMatDesc(args.get("MAKTX"));


           albumList.add(model);
        }
        ean_et.setText(dataList.get(0).get("IM_EAN"));
        adapter.notifyDataSetChanged();
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }

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
