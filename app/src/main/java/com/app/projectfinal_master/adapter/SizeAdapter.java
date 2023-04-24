package com.app.projectfinal_master.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.model.Size;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {
    private ICallbackActivity iCallbackActivity;
    private Context mContext;
    private List<Size> mList;
    private Size size;
    List<TextView> item = new ArrayList<>();

    public SizeAdapter(Context context, List<Size> mList, ICallbackActivity iCallbackActivity) {
        this.mContext = context;
        this.mList = mList;
        this.iCallbackActivity = iCallbackActivity;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView tvSize;

        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);
            tvSize = view.findViewById(R.id.tv_size);

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
        View view = layoutInflater.inflate(R.layout.item_size, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        size = mList.get(position);
        holder.tvSize.setText(size.getSize());
        item.add(holder.tvSize);
        holder.tvSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mList.size(); i++) {
                    if (i == position) {
                        item.get(i).setBackgroundResource(R.drawable.bg_stroke_border_1_selected);
                        iCallbackActivity.callback(mList.get(position));
                    } else {
                        item.get(i).setBackgroundResource(R.drawable.bg_stroke_border_1_un_selected);
                    }
                }
            }
        });

//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, List<Object> list, int position, boolean isLongClick) {
//                if (isLongClick) {
//
//                } else {
//                    for (int i = 0; i < mList.size(); i++) {
//                        if (i == position) {
//                            Log.e("TAG", "onClick: " );
//                            holder.tvSize.setBackgroundResource(R.drawable.bg_stroke_border_1_slected);
//                        } else {
//                            holder.tvSize.setBackgroundResource(R.drawable.bg_stroke_border_1_un_slected);
//                        }
//                    }
////                    Intent intent = new Intent(mContext, ProductArrangeActivity.class);
////                    Bundle bundle = new Bundle();
////                    bundle.putString("size", mList.get(position).getSize());
////                    intent.putExtras(bundle);
////                    view.getContext().startActivity(intent);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
