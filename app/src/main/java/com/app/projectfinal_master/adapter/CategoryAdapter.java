package com.app.projectfinal_master.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.ProductArrangeActivity;
import com.app.projectfinal_master.model.Category;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context mContext;
    private List<Category> mList;
    private Category category;

    public CategoryAdapter(Context context, List<Category> mList) {
        this.mContext = context;
        this.mList = mList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView tvTitle;
        private CircleImageView imgCategory;

        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            imgCategory = view.findViewById(R.id.img_category);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, Collections.singletonList(mList), getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, Collections.singletonList(mList),getAdapterPosition(), true);
            return false;
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        category = mList.get(position);
        holder.tvTitle.setText(category.getCategory());
        Glide.with(mContext).load(category.getImage()).into(holder.imgCategory);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, List<Object> list, int position, boolean isLongClick) {
                if (isLongClick) {

                } else {
                    Intent intent = new Intent(mContext, ProductArrangeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id_category", mList.get(position).getIdCategory());
                    bundle.putString("category", mList.get(position).getCategory());
//                    bundle.putString("sex", mList.get(position).getSex());
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
