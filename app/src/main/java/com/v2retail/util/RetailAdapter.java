package com.v2retail.util;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.v2retail.dotvik.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RetailAdapter extends RecyclerView.Adapter<RetailAdapter.MyViewHolder> {

	private Context mContext;
	private List<RetailModal> articleList;
	private List<RetailModal.Ageing> ageingList;
	private RetailModal retailGenericModal;

	public RetailAdapter(Context mContext, ArrayList<RetailModal.Ageing> ageingList) {
		this.mContext = mContext;
		this.ageingList = ageingList;
	}

	public RetailAdapter(Context mContext, List<RetailModal> articleList) {
		this.mContext = mContext;
		this.articleList = articleList;
	}

	public RetailAdapter(Context mContext, RetailModal articleList) {
		this.mContext = mContext;
		this.retailGenericModal = articleList;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = null;
		if (articleList != null) {
			itemView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.retail_view_item, parent, false);
		} else if (ageingList != null) {
			itemView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.ageing_item, parent, false);

		}
		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, int position) {

		DecimalFormat df = new DecimalFormat("0.00");
		//RetailModal list = retailGenericModal;
		if(articleList!=null)
		{
			RetailModal list = articleList.get(position);
//info
			holder.article_et.setText(list.getArticle());
			holder.matDes_et.setText(list.getMatDesc());
			holder.color_et.setText(list.getColor());
			holder.arttype_et.setText(list.getSize1());
			holder.irode.setText(list.getIrode());
//stock
			holder.vo1_et.setText(df.format(list.getVo1()));
			holder.coageing_01.setText(list.getStockageing());
			holder.v_02.setText(list.getVo2());
			holder.ageing.setText(list.getSaleageing());
			holder.one_et.setText(df.format(list.getOne()));
			//holder.total_et.setText(df.format(list.getOne()));

			holder.td.setText(df.format(list.getTd()));
			holder.l7_d.setText(list.getStr_l7());
			holder.mtd.setText(df.format(list.getMtd()));
			holder.l30_d.setText(list.getStr_l30());
//sales
			holder.l7_d_psf.setText(list.getSale_psf_l7());
			holder.str_l7_d.setText(list.getGp_psf_l7());
			holder.l30_d_psf.setText(list.getSale_psf_l30());
			holder.str_l30_d.setText(list.getGp_psf_l30());

			// removing negative value
//			holder.str_td.setText(df.format(list.getStr_td()).replaceFirst("-",""));
//			holder.STR_TDf.setText(df.format(list.getSTR_TDf()).replaceFirst("-",""));
//			holder.str_mtd.setText(df.format(list.getStr_mtd()).replaceFirst("-",""));
//			holder.str_mtdf.setText(df.format(list.getStr_mtdf()).replaceFirst("-",""));
//			holder.str_ytd.setText(df.format(list.getStr_ytd()).replaceFirst("-",""));
//			holder.lmtd.setText(df.format(list.getLmtd()).replaceFirst("-",""));
			holder.ageing.setText(list.getAgeing());
			holder.irode.setText(list.getIrode());
		}else if(ageingList!=null && ageingList.size()>0)
		{
			RetailModal.Ageing ageList=ageingList.get(position);

			holder.matDes_et.setText(ageList.getDesc());
			holder.article_et.setText(ageList.getArticle());
			holder.salesAge.setText(df.format(ageList.getSalesAgeing()));
			holder.stockAge.setText(df.format(ageList.getStockAgeing()));

		}


	}

	@Override
	public int getItemCount() {
		if (articleList != null)
			return articleList.size();
		else if (ageingList != null)
			return ageingList.size();
		else return 1;
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		EditText article_et;
		EditText matDes_et;
		EditText color_et;
		EditText arttype_et;
		EditText irode;

		EditText vo1_et;
		EditText coageing_01;
		EditText v_02;
		EditText ageing;
		EditText one_et;
		EditText total_et;

		EditText td;
		EditText l7_d;
		EditText mtd;
		EditText l30_d;

		EditText l7_d_psf;
		EditText str_l7_d;
		EditText l30_d_psf;
		EditText str_l30_d;

		EditText salesAge;
		EditText stockAge;


		public MyViewHolder(View view) {
			super(view);
			if(articleList!=null) {    //info
				article_et = (EditText) view.findViewById(R.id.article_et);
				matDes_et = (EditText) view.findViewById(R.id.matDesc);//EditText
				color_et = (EditText) view.findViewById(R.id.color_et);
				arttype_et = (EditText) view.findViewById(R.id.arttype_et);
				irode = (EditText) view.findViewById(R.id.retail_view_irode);

				vo1_et = (EditText) view.findViewById(R.id.vo1_et);
				coageing_01 = (EditText) view.findViewById(R.id.three_et);
				v_02 = (EditText) view.findViewById(R.id.msa_et);
				ageing = (EditText) view.findViewById(R.id.four_et);
				one_et = (EditText) view.findViewById(R.id.one_et);
				total_et = (EditText) view.findViewById(R.id.total_et);

				td = (EditText) view.findViewById(R.id.td);
				l7_d = (EditText) view.findViewById(R.id.STR_TDf);
				mtd = (EditText) view.findViewById(R.id.mtd);
				l30_d = (EditText) view.findViewById(R.id.strMtdf);

				l7_d_psf = (EditText) view.findViewById(R.id.td_psf);
				str_l7_d = (EditText) view.findViewById(R.id.STR_L7D);
				l30_d_psf = (EditText) view.findViewById(R.id.mtd_psf);
				str_l30_d = (EditText) view.findViewById(R.id.STR_L30D);

			}
			else if(ageingList!=null)
			{
				article_et = (EditText) view.findViewById(R.id.article_et);
				matDes_et = (EditText) view.findViewById(R.id.matDesc);
				salesAge = (EditText) view.findViewById(R.id.salesageing);
				stockAge = (EditText) view.findViewById(R.id.stockageing);
			}
		}
	}
}