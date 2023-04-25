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
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.DetailProductActivity;
import com.app.projectfinal_master.model.ItemizedReceipt;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class ItemizedReceiptAdapter extends RecyclerView.Adapter<ItemizedReceiptAdapter.ViewHolder> {
    private View view;
    private Context context;
    private List<ItemizedReceipt> mList;
    private ItemizedReceipt itemizedReceipt;
    private ICallbackActivity iCallbackActivity;

    public ItemizedReceiptAdapter(Context context, List<ItemizedReceipt> mList, ICallbackActivity iCallbackActivity) {
        this.context = context;
        this.mList = mList;
        this.iCallbackActivity = iCallbackActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvProductsName, tvPrice, tvInfo, tvQuantity, tvSumPrice;
        private LinearLayoutCompat linearLayoutCompat;

//        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_product);
            tvProductsName = view.findViewById(R.id.tv_product_name);
            tvInfo = view.findViewById(R.id.tv_info);
            tvPrice = view.findViewById(R.id.tv_price);
            tvSumPrice = view.findViewById(R.id.tv_sum_price);
            tvQuantity = view.findViewById(R.id.tv_quantity);
            linearLayoutCompat = view.findViewById(R.id.layout_receipt);

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
        view = layoutInflater.inflate(R.layout.item_itemized_receipt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        itemizedReceipt = mList.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.tvProductsName.setText(itemizedReceipt.getName());
        int sumPrice = itemizedReceipt.getQuantity() * Integer.valueOf(itemizedReceipt.getPrice());
        holder.tvSumPrice.setText("Tổng tiền: " + String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(sumPrice)))));
        holder.tvQuantity.setText("x" + itemizedReceipt.getQuantity());
        holder.tvInfo.setText(itemizedReceipt.getColor() + ", " + itemizedReceipt.getSize());
        holder.tvPrice.setText("Đơn giá: " + String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(itemizedReceipt.getPrice())))));
        Glide.with(context).load(itemizedReceipt.getImage_thumb()).into(holder.imgProduct);
        holder.linearLayoutCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemizedReceipt = mList.get(position);
                Intent intent = new Intent(context, DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("code_product", itemizedReceipt.getCode_product());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
//                iCallbackActivity.callback();
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
