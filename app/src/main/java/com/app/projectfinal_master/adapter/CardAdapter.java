package com.app.projectfinal_master.adapter;

import static com.app.projectfinal_master.utils.Constant.PRODUCT_DETAIL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Cart;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.app.projectfinal_master.utils.VolleySingleton;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ICallbackActivity iCallbackActivity;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private List<Cart> mList;
    private Cart cart;
    private Product product;

    public CardAdapter(Context context, List<Cart> mList, ICallbackActivity iCallbackActivity, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.mList = mList;
        this.iCallbackActivity = iCallbackActivity;
        this.itemClickListener = itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private Spinner size;
        private TextView tvQuantity, tvPrice, tvName;
        private ImageView imgProduct, imgFavorite;
        private Button btnReduce, btnIncrease;
        private StringRequest mStringRequest;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.check_box);
            size = view.findViewById(R.id.spinner);
            tvQuantity = view.findViewById(R.id.tv_quantity);
            tvPrice = view.findViewById(R.id.tv_price);
            tvName = view.findViewById(R.id.tv_product_name);
            imgProduct = view.findViewById(R.id.img_product);
            imgFavorite = view.findViewById(R.id.img_favorite);
            btnReduce = view.findViewById(R.id.btn_reduce);
            btnIncrease = view.findViewById(R.id.btn_increase);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        cart = mList.get(position);
        product = cart.getProduct();
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getSellingPrice());
        holder.tvQuantity.setText(String.valueOf(cart.getQuantity()));
        Glide.with(mContext).load(product.getImageThumb()).into(holder.imgProduct);
        setOnClickButtonReduce(holder, cart, position);
        setOnClickButtonIncrease(holder, cart, position);
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(holder.itemView, Collections.singletonList(mList), position, false);
            }
        });
    }

    private void setOnClickButtonReduce(@NonNull ViewHolder holder, Cart cart, int position) {
        holder.btnReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.getQuantity() > 1) {
                    cart.setQuantity(cart.getQuantity() - 1);
                    holder.tvQuantity.setText(String.valueOf(cart.getQuantity()));
                    DataLocalManager.changeDataCart(cart, position);
                } else {
                    DataLocalManager.removeDataCart(position);
                    mList.clear();
                    if (DataLocalManager.getCarts().size() == 0) {
                    } else {
                        mList.addAll(DataLocalManager.getCarts());
                    }
                    notifyDataSetChanged();
                }
                iCallbackActivity.callback(cart);
            }
        });
    }

    private void setOnClickButtonIncrease(@NonNull ViewHolder holder, Cart cart, int position) {
        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cart.setQuantity(cart.getQuantity() + 1);
                holder.tvQuantity.setText(String.valueOf(cart.getQuantity()));
                DataLocalManager.changeDataCart(cart, position);
                iCallbackActivity.callback(cart);

            }
        });
    }

    private void getCartProduct(@NonNull ViewHolder holder, String idUser, String idProduct) {
        holder.mStringRequest = new StringRequest(Request.Method.POST, PRODUCT_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        int id_category = object.getInt("id_category");
                        int id_color = object.getInt("id_color");
                        int id_size = object.getInt("id_size");
                        String code = object.getString("code_product");
                        String name = object.getString("name");
                        JSONArray imageThumb = object.getJSONArray("image_thumb");
                        String sellingPrice = object.getString("selling_price");
                        int quantity = object.getInt("quantity");
                        String description = object.getString("description");
                        String rate = object.getString("rate");
                        int discount = object.getInt("discount");


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id_product", idProduct);
                params.put("id_user", idUser);

                return params;
            }
        };

        holder.mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(mContext).getRequestQueue().add(holder.mStringRequest);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
