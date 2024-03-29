package com.app.projectfinal_master.adapter;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.DetailProductActivity;
import com.app.projectfinal_master.model.Product;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private View view;
    private Context context;
    private List<Product> mList;
    private Product product;

    public ProductAdapter(Context context, List<Product> mList) {
        this.context = context;
        this.mList = mList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvProductsName, tvPrice;
        private LinearLayoutCompat linearLayoutCompat;

//        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_product);
            tvProductsName = view.findViewById(R.id.tv_product_name);
            tvPrice = view.findViewById(R.id.tv_price);
            linearLayoutCompat = view.findViewById(R.id.layout_product);

//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            itemClickListener.onClick(v, getAdapterPosition(), false);
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            itemClickListener.onClick(v, getAdapterPosition(), true);
//            return false;
//        }
//
//        public void setItemClickListener(ItemClickListener itemClickListener) {
//            this.itemClickListener = itemClickListener;
//        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        product = mList.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.tvProductsName.setText(product.getName());
        holder.tvPrice.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(product.getSellingPrice())))));
        Glide.with(context).load(product.getImageThumb()).into(holder.imgProduct);
        holder.linearLayoutCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = mList.get(position);
                Intent intent = new Intent(context, DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("code_product", product.getCodeProduct());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                if (isLongClick) {
//
//                } else {
//                    Intent intent = new Intent(context, DetailProductActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("product", mList.get(position));
//                    intent.putExtras(bundle);
//                    view.getContext().startActivity(intent);
//                }
//            }
//        });

//        itemClickListener.onClick(holder.itemView, position, false);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
