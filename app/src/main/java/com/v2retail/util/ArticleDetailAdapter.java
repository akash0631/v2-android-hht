package com.v2retail.util;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.v2retail.dotvik.R;

import java.util.List;

public class ArticleDetailAdapter   extends RecyclerView.Adapter<ArticleDetailAdapter.MyViewHolder> {

private Context mContext;
private List<ArticleDetailModal> articleList;

public class MyViewHolder extends RecyclerView.ViewHolder {
	public TextView title, count;
	EditText article_et;
	TextView matDes_et;
	EditText ean_et;
	EditText vo1_et;
	EditText total_et;
	EditText one_et;
	EditText four_et;
	EditText three_et;
	EditText five_et;
	EditText six_et;
	EditText seven_et;
	EditText eight_et;
	EditText msa_et;
	EditText nine_et;
	EditText ten_et;
	public MyViewHolder(View view) {
		super(view);
		matDes_et = (TextView) view.findViewById(R.id.matDesc);
		vo1_et = (EditText) view.findViewById(R.id.vo1_et);
		total_et = (EditText) view.findViewById(R.id.total_et);
		one_et = (EditText) view.findViewById(R.id.one_et);
		three_et = (EditText) view.findViewById(R.id.three_et);
		four_et = (EditText) view.findViewById(R.id.four_et);
		five_et = (EditText) view.findViewById(R.id.five_et);
		six_et = (EditText) view.findViewById(R.id.six_et);
		seven_et = (EditText) view.findViewById(R.id.seven_et);
		eight_et = (EditText) view.findViewById(R.id.eight_et);
		msa_et = (EditText) view.findViewById(R.id.msa_et);
		article_et = (EditText) view.findViewById(R.id.article_et);
		nine_et = (EditText) view.findViewById(R.id.nine_et);
		ten_et = (EditText) view.findViewById(R.id.ten_et);
		//ean_et = (EditText) view.findViewById(R.id.ean_et);
	}
}


	public ArticleDetailAdapter(Context mContext, List<ArticleDetailModal> articleList) {
		this.mContext = mContext;
		this.articleList = articleList;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.retail_view_item, parent, false);

		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, int position) {
		ArticleDetailModal list = articleList.get(position);
		//holder.ean_et.setText(list.getEan());
		holder.matDes_et.setText(list.getMatDesc());
		holder.article_et.setText(list.getArticle());
		holder.one_et.setText(list.getOne());
		holder.three_et.setText(list.getThree());
		holder.four_et.setText(list.getFour());
		holder.five_et.setText(list.getFive());
		holder.six_et.setText(list.getSix());
		holder.seven_et.setText(list.getSeven());
		holder.eight_et.setText(list.getEight());
		holder.nine_et.setText(list.getNine());
		holder.ten_et.setText(list.getTen());
		holder.vo1_et.setText(list.getVo1());
		holder.msa_et.setText(list.getMsa());
		holder.total_et.setText(list.getTotal());



		// loading album cover using Glide library
//		Glide.with(mContext).load(list.getThumbnail()).into(holder.thumbnail);

		/*holder.overflow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//showPopupMenu(holder.overflow);
			}
		});*/
	}




	@Override
	public int getItemCount() {
		return articleList.size();
	}
}