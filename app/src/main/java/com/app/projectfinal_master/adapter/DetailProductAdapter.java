package com.app.projectfinal_master.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.DetailProductActivity;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.List;

public class DetailProductAdapter extends RecyclerView.Adapter<DetailProductAdapter.ViewHolder> {

    private Context context;
    private List<String> mList;

    public DetailProductAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView imgDetailProduct;

        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);
            imgDetailProduct = view.findViewById(R.id.img_product);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
//            itemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String img = mList.get(position);

        Glide.with(context).load(img).into(holder.imgDetailProduct);

//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                if (isLongClick) {
//
//                } else {
//
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
