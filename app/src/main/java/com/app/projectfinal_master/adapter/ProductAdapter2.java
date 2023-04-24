package com.app.projectfinal_master.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.DetailProductActivity;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

public class ProductAdapter2 extends ListAdapter<Product, ProductAdapter2.ViewHolder> {

    private ItemClickListener itemClickListener;
    private Context context;
    private Product product;

    public ProductAdapter2(@NonNull DiffUtil.ItemCallback<Product> diffCallback, Context context, ItemClickListener itemClickListener) {
        super(diffCallback);
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        product = getItem(position);
        holder.bind(product);
        holder.setOnClickItem(product);
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

        }

        public void setOnClickItem(Product product) {
            linearLayoutCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailProductActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("code_product", product.getCodeProduct());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
//                    itemClickListener.onClick(null, null, getAdapterPosition(), false);
                }
            });
        }

        public void bind(Product product) {
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            tvProductsName.setText(product.getName());
            tvPrice.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(product.getSellingPrice())))));
            Glide.with(itemView.getContext()).load(product.getImageThumb()).into(imgProduct);
        }
    }
}
