package com.app.projectfinal_master.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.model.ImageItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private final List<String> imageItems;
    private final ViewPager2 viewPager2;

    public ImageAdapter(Context context, List<String> imageItems, ViewPager2 viewPager2) {
        this.context = context;
        this.imageItems = imageItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_detail_product,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Glide.with(context).load(imageItems.get(position)).into(holder.img);
        if (position == imageItems.size() - 2) {
            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return imageItems.size() ;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_detail_product);
        }
    }

    private final Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
//            sliderItems.clear();
            imageItems.addAll(imageItems);
            notifyDataSetChanged();
        }
    };

    public void reload()
    {
        imageItems.clear();
        imageItems.addAll(imageItems);
        notifyDataSetChanged();
    }

}
