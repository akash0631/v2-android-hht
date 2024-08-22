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

public class ArticleSaleAdapter extends RecyclerView.Adapter<ArticleSaleAdapter.MyViewHolder> {

private Context mContext;
private List<ArticleSaleModal> articleList;

public class MyViewHolder extends RecyclerView.ViewHolder {
	public TextView title, count;
	EditText article_et;
	TextView matDes_et;
	EditText ytd_sale_v;
	EditText ytd_sale_q;
	EditText mtd_sale_v;
	EditText mtd_sale_q;
	EditText td_sale_v;
	EditText td_sale_q;
	public MyViewHolder(View view) {
		super(view);
		matDes_et = (TextView) view.findViewById(R.id.matDesc);
		td_sale_q = (EditText) view.findViewById(R.id.tdSaleq);
		td_sale_v = (EditText) view.findViewById(R.id.tdSalev);
		mtd_sale_q = (EditText) view.findViewById(R.id.mtdSaleq);
		mtd_sale_v = (EditText) view.findViewById(R.id.mtdSalev);
		ytd_sale_q = (EditText) view.findViewById(R.id.ytdSaleq);
		ytd_sale_v = (EditText) view.findViewById(R.id.ytdSalev);

		article_et = (EditText) view.findViewById(R.id.article_et);
	}
}


	public ArticleSaleAdapter(Context mContext, List<ArticleSaleModal> articleList) {
		this.mContext = mContext;
		this.articleList = articleList;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.stock_sales_variant_item, parent, false);

		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, int position) {
		ArticleSaleModal list = articleList.get(position);
		//holder.ean_et.setText(list.getEan());
	//	holder.matDes_et.setText(list.getMatDesc());
		holder.article_et.setText(list.getArticle());
		holder.td_sale_q.setText(list.getTd_sale_q());
		holder.td_sale_v.setText(list.getTd_sale_v());
		holder.mtd_sale_q.setText(list.getMtd_sale_q());
		holder.mtd_sale_v.setText(list.getMtd_sale_v());
		holder.ytd_sale_q.setText(list.getYtd_sale_q());
		holder.ytd_sale_v.setText(list.getYtd_sale_v());
		holder.matDes_et.setText(list.getMatDesc());


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