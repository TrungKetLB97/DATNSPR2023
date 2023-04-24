package com.app.projectfinal_master.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.DetailProductActivity;
import com.app.projectfinal_master.model.Address;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.ItemClickListener;

import java.util.Collections;
import java.util.List;

public class DeliveryAddressAdapter extends ListAdapter<Address, DeliveryAddressAdapter.ViewHolder> {

    private ICallbackActivity iCallbackActivity;
    private Context context;
    private Address address;

    public DeliveryAddressAdapter(@NonNull DiffUtil.ItemCallback<Address> diffCallback, Context context, ICallbackActivity iCallbackActivity) {
        super(diffCallback);
        this.context = context;
        this.iCallbackActivity = iCallbackActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        address = getItem(position);
        holder.tvAddress.setText(address.getAddress());

        holder.bind(address);

        holder.layoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallbackActivity.callback(getItem(position));
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvAddress, tvUsername, tvPhoneNumber, tvStreet;
        private CheckBox checkBox;
        private RelativeLayout layoutAddress;

        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvAddress = view.findViewById(R.id.tv_update_address);
            tvUsername = view.findViewById(R.id.tv_username);
            tvPhoneNumber = view.findViewById(R.id.tv_update_phone_number);
            tvStreet = view.findViewById(R.id.tv_street);
            checkBox = view.findViewById(R.id.check_box);
            layoutAddress = view.findViewById(R.id.layout_address);
        }

        public void setOnClickItem() {
            tvAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailProductActivity.class);
                    Bundle bundle = new Bundle();
//                    bundle.putAddress("id_product", product.getIdProduct());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        public void bind(Address address) {
            tvAddress.setText(address.toString());
            tvUsername.setText(address.getReceiver());
            tvPhoneNumber.setText(address.getPhoneNumber());
            tvStreet.setText(address.getStreet());
            if (address.getSetDefault() == 0) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, Collections.singletonList(getCurrentList()), getAdapterPosition(), false);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
