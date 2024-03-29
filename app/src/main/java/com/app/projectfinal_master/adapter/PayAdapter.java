package com.app.projectfinal_master.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.model.Cart;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.ViewHolder> {

    private ItemClickListener itemClickListener;
    private Context mContext;
    private List<Cart> mList;
    private Cart cart;
    private Product product;

    public PayAdapter(Context context, List<Cart> mList, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.mList = mList;
        this.itemClickListener = itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuantity;
        private ImageView imgProduct;

        public ViewHolder(View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_product);
            tvQuantity = view.findViewById(R.id.tv_quantity);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_pay_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        cart = mList.get(position);
        product = cart.getProduct();
        holder.tvQuantity.setText(String.format("x%s",cart.getQuantity()));
        Glide.with(mContext).load(product.getImageThumb()).into(holder.imgProduct);
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                itemClickListener.onClick(holder.itemView, Collections.singletonList(mList), position, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
