package com.app.projectfinal_master.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.model.Color;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private ICallbackActivity iCallbackActivity;
    private Context mContext;
    private List<Color> mList;
    private Color color;
    List<LinearLayoutCompat> item = new ArrayList<>();

    public ColorAdapter(Context context, List<Color> mList, ICallbackActivity iCallbackActivity) {
        this.mContext = context;
        this.mList = mList;
        this.iCallbackActivity = iCallbackActivity;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private LinearLayoutCompat layoutColor;
        private View viewColor;

        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);
            layoutColor = view.findViewById(R.id.layout_color);
            viewColor = view.findViewById(R.id.view_color);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, Collections.singletonList(mList), getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, Collections.singletonList(mList), getAdapterPosition(), true);
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
        View view = layoutInflater.inflate(R.layout.item_color, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        color = mList.get(position);
//        setBackgroundViewColor(holder.viewColor);
        holder.viewColor.setBackgroundColor(android.graphics.Color.parseColor(color.getCode()));
        item.add(holder.layoutColor);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, List<Object> list, int position, boolean isLongClick) {
                if (isLongClick) {

                } else {
                    for (int i = 0; i < getItemCount(); i++) {
                        if (i == position) {
                            item.get(i).setBackgroundResource(R.drawable.bg_stroke_border_1_selected);
                            iCallbackActivity.callback(mList.get(position));
                        } else {
                            item.get(i).setBackgroundResource(R.drawable.bg_stroke_border_1_un_selected);
                        }
                    }
//                    Intent intent = new Intent(mContext, ProductArrangeActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("size", mList.get(position).getSize());
//                    intent.putExtras(bundle);
//                    view.getContext().startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setBackgroundViewColor(View v, Color color) {
        if (color.getColor().equalsIgnoreCase("black")) {

        }if (color.getColor().equalsIgnoreCase("blue")) {

        }if (color.getColor().equalsIgnoreCase("red")) {

        }if (color.getColor().equalsIgnoreCase("gray")) {

        }if (color.getColor().equalsIgnoreCase("yellow")) {

        }if (color.getColor().equalsIgnoreCase("")) {

        }if (color.getColor().equalsIgnoreCase("red")) {

        }if (color.getColor().equalsIgnoreCase("red")) {

        }if (color.getColor().equalsIgnoreCase("red")) {

        }if (color.getColor().equalsIgnoreCase("red")) {

        }
    }
}
