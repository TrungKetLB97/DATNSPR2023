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
import com.app.projectfinal_master.model.Receipt;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

public class ReceiptAdapter extends ListAdapter<Receipt, ReceiptAdapter.ViewHolder> {

    private ICallbackActivity iCallbackActivity;
    private Context context;
    private Receipt receipt;

    public ReceiptAdapter(@NonNull DiffUtil.ItemCallback<Receipt> diffCallback, Context context, ICallbackActivity iCallbackActivity) {
        super(diffCallback);
        this.context = context;
        this.iCallbackActivity = iCallbackActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        receipt = getItem(position);
        holder.bind(receipt);
        holder.setOnClickItem(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvProductsName, tvPrice, tvSumPrice, tvInfo, tvStatus, tvPayStatus;
        private LinearLayoutCompat layoutReceipt;

//        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_product);
            tvProductsName = view.findViewById(R.id.tv_product_name);
            tvSumPrice = view.findViewById(R.id.tv_sum_price);
            tvInfo = view.findViewById(R.id.tv_info);
            tvPrice = view.findViewById(R.id.tv_price);
            tvPayStatus = view.findViewById(R.id.tv_pay_status);
            tvStatus = view.findViewById(R.id.tv_status);
            layoutReceipt = view.findViewById(R.id.layout_receipt);

    }

        public void setOnClickItem(Receipt receipt) {
            layoutReceipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, DetailProductActivity.class);
//                    intent.putExtra("receipt", receipt);
//                    context.startActivity(intent);
                    iCallbackActivity.callback(receipt);
//                    itemClickListener.onClick(null, null, getAdapterPosition(), false);
                }
            });
        }

        public void bind(Receipt receipt) {
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            tvPrice.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(receipt.getItemizedReceipt().getPrice())))));
            tvSumPrice.setText(receipt.getQuantity() + " mặt hàng: " + String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(receipt.getSumPrice())))));
            tvProductsName.setText(receipt.getItemizedReceipt().getName());
            tvStatus.setText(receipt.getReceiptStatus());
            tvPayStatus.setText(receipt.getPayStatus());
            tvInfo.setText(receipt.getItemizedReceipt().getColor() + ", " + receipt.getItemizedReceipt().getSize());
            Glide.with(itemView.getContext()).load(receipt.getItemizedReceipt().getImage_thumb()).into(imgProduct);
        }
    }
}
