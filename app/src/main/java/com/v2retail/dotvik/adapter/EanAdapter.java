package com.v2retail.dotvik.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.v2retail.dotvik.R;
import com.v2retail.dotvik.modal.picklist.EanModel;

import java.util.ArrayList;

public class EanAdapter extends RecyclerView.Adapter<EanAdapter.MyViewHolder> {

    private ArrayList<EanModel> list;

    public EanAdapter(ArrayList<EanModel> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()) .inflate(R.layout.item_ean, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final EanModel item = list.get(position);
        holder.text_ean_number.setText(item.EAN);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_ean_number;

        public MyViewHolder(View view) {
            super(view);
            text_ean_number = view.findViewById(R.id.text_ean_number);
        }
    }
}
