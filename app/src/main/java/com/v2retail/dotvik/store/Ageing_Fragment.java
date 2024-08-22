package com.v2retail.dotvik.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.v2retail.dotvik.R;
import com.v2retail.util.AlertBox;
import com.v2retail.util.RetailAdapter;
import com.v2retail.util.RetailModal;
import com.v2retail.util.RetailModal;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Ageing_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Ageing_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ageing_Fragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "data";
	private static final String ARG_PARAM2 = "param2";


	DecimalFormat df = new DecimalFormat("0.00");
	Context con;
	EditText ean_et;
	String code;
	ProgressDialog dialog;
	// TODO: Rename and change types of parameters
	Button close;
	Button search;
	Button back;
	Button scan;
	Button rescan;
	Button dc;
	Button ageing;
	LinearLayout lay;
	AlertBox box;
	FragmentManager fm;
	String URL = "";
	String WERKS = "";
	String USER = "";
	private static final String TAG = "Ageing_Fragment";
	ArrayList<RetailModal.Ageing> modal;
	private OnFragmentInteractionListener mListener;
	private RecyclerView recyclerView;
	private RetailAdapter adapter;

	public Ageing_Fragment() {
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
	public static Ageing_Fragment newInstance(String param1, String param2) {
		Ageing_Fragment fragment = new Ageing_Fragment();
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
				.setActionBarTitle("Ageing");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			modal = (ArrayList<RetailModal.Ageing>) getArguments().getSerializable("ageingList");
		}
		fm = getFragmentManager();
	}




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.article_ageing_detail, container, false);

		con = getContext();
		dialog = new ProgressDialog(con);
		box = new AlertBox(con);


		back = (Button) view.findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				fm.popBackStack();
			}
		});
		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		RetailAdapter adapter = new RetailAdapter(con, modal);

		recyclerView.setAdapter(adapter);


		return view;
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
}
