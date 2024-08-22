package com.v2retail.dotvik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.v2retail.dotvik.R;
import com.v2retail.dotvik.modal.picklist.CrateModel;
import com.v2retail.dotvik.modal.picklist.EanModel;
import com.v2retail.dotvik.modal.picklist.MaterialModel;

import java.util.ArrayList;

public class CrateAdapter extends RecyclerView.Adapter<CrateAdapter.MyViewHolder> {

    private ArrayList<CrateModel> list;
    private Context context;
    private OnItemClickListener listener;

    public CrateAdapter(Context context, ArrayList<CrateModel> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()) .inflate(R.layout.item_crate, parent, false);
        ((RecyclerView)itemView.findViewById(R.id.recycler_view)).setLayoutManager(new LinearLayoutManager(context));
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CrateModel item = list.get(position);
        holder.text_crate_number.setText(item.ID);

        ArrayList<EanModel> eanList = new ArrayList();
        for (MaterialModel materialModel : item.materialList) {
            eanList.addAll(materialModel.scannedEAN);
        }
        holder.recycler_view.setAdapter(new EanAdapter(eanList));

        holder.button_add_new_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddArticleClick(item);
            }
        });

        holder.button_scan_new_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onScanArticleClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_crate_number;
        public RecyclerView recycler_view;
        public Button button_add_new_article, button_scan_new_article;

        public MyViewHolder(View view) {
            super(view);
            text_crate_number = view.findViewById(R.id.text_crate_number);
            recycler_view = view.findViewById(R.id.recycler_view);
            button_add_new_article = view.findViewById(R.id.button_add_new_article);
            button_scan_new_article = view.findViewById(R.id.button_scan_new_article);
        }
    }

    public interface OnItemClickListener {
        void onAddArticleClick(CrateModel item);
        void onScanArticleClick(CrateModel item);
    }
}
