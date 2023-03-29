package com.app.projectfinal_master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.app.projectfinal_master.R;

import java.util.List;

public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeAdapter.SizeViewHolder> {
    private final List<String> sizeItems;
    private String size;

    public ProductSizeAdapter(List<String> sizeItems) {
        this.sizeItems = sizeItems;
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SizeViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_size,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
        size = sizeItems.get(position);
        holder.tvSize.setText(size);
    }

    @Override
    public int getItemCount() {
        return sizeItems.size() ;
    }

    public static class SizeViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSize;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSize = itemView.findViewById(R.id.tv_size);
        }
    }

}
