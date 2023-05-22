package com.app.projectfinal_master.adapter;

import android.content.Context;
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
import com.app.projectfinal_master.model.Notification;
import com.app.projectfinal_master.model.Receipt;
import com.app.projectfinal_master.utils.ICallbackActivity;

public class NotificationAdapter extends ListAdapter<Notification, NotificationAdapter.ViewHolder> {

    private ICallbackActivity iCallbackActivity;
    private Context context;
    private Notification notification;

    public NotificationAdapter(@NonNull DiffUtil.ItemCallback<Notification> diffCallback, Context context, ICallbackActivity iCallbackActivity) {
        super(diffCallback);
        this.context = context;
        this.iCallbackActivity = iCallbackActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        notification = getItem(position);
        holder.bind(notification);
//        holder.setOnClickItem(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvTitle, tvContent, tvTime;
        private LinearLayoutCompat layoutReceipt;

//        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvContent = view.findViewById(R.id.tv_content);
            tvTime = view.findViewById(R.id.tv_time);
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

        public void bind(Notification notification) {
            tvTitle.setText(notification.getTitle());
            tvContent.setText(notification.getContent());
            tvTime.setText(notification.getTime());
        }
    }
}
