package com.app.projectfinal_master.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.DetailProductActivity;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter2 extends ListAdapter<Product, ProductAdapter2.ViewHolder> {

    private ItemClickListener itemClickListener;

    protected ProductAdapter2(@NonNull DiffUtil.ItemCallback<Product> diffCallback, ItemClickListener itemClickListener) {
        super(diffCallback);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = getItem(position);
        holder.bind(product);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvProductsName, tvPrice;
        private LinearLayoutCompat linearLayoutCompat;

//        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_product);
            tvProductsName = view.findViewById(R.id.tv_product_name);
            tvPrice = view.findViewById(R.id.tv_price);
            linearLayoutCompat = view.findViewById(R.id.layout_product);
            linearLayoutCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(null, null, getAdapterPosition(), false);
                }
            });
        }

        public void bind(Product product) {
            tvProductsName.setText(product.getName());
            tvPrice.setText(product.getSellingPrice());
            Glide.with(itemView.getContext()).load(product.getImageThumb()).into(imgProduct);
        }
    }
}
