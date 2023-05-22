package com.app.projectfinal_master.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.model.Address;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.ItemClickListener;

public class AddressAdapter extends ListAdapter<Address, AddressAdapter.ViewHolder> {

    private ICallbackActivity iCallbackActivity;
    private Context context;
    private Address address;

    public AddressAdapter(@NonNull DiffUtil.ItemCallback<Address> diffCallback, Context context, ICallbackActivity iCallbackActivity) {
        super(diffCallback);
        this.context = context;
        this.iCallbackActivity = iCallbackActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        address = getItem(position);
        holder.tvAddress.setText(address.getAddress());

        holder.tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallbackActivity.callback(getItem(position));
            }
        });
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, List<Object> list, int position, boolean isLongClick) {
////                onBindViewHolder(holder, position);
//            }
//        });
//        holder.itemClickListener.onClick(null, null, position, false);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvAddress;
        private LinearLayoutCompat linearLayoutCompat;

        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvAddress = view.findViewById(R.id.tv_update_address);
//            itemView.setOnClickListener(this);
        }

        public void bind(Address address) {
//            tvProductsName.setText(product.getName());
//            tvPrice.setText(product.getSellingPrice());
        }

//        @Override
//        public void onClick(View v) {
//            itemClickListener.onClick(v, Collections.singletonList(getCurrentList()), getAdapterPosition(), false);
//        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
