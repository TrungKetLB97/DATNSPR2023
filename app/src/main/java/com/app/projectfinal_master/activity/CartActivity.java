package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.CART;
import static com.app.projectfinal_master.utils.Constant.PRODUCT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.CardAdapter;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Cart;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionBar;

    private Button btnBuyNow, btnPay;
    private TextView tvSumPrice;
    private ImageView imgBack;

    private RecyclerView rcvCart;
    private RecyclerView.Adapter cartAdapter;
    private RecyclerView.LayoutManager cartLayout;

    public List<Cart> carts;
    private Cart cart;
    private Product product;
    private DetailProductActivity detailProductActivity;

    private LinearLayoutCompat cartEmptyLayout;
    private RelativeLayout cartItemLayout;

    private StringRequest mStringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        eventClickBackImg();
        getDataCart();
        setViewActionBar();
        setSumPriceCart();
        setOnClickButtonPay();
        setOnClickButtonBuyNow();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        rcvCart = findViewById(R.id.rcv_cart);
        cartEmptyLayout = findViewById(R.id.ll_cart_empty);
        cartItemLayout = findViewById(R.id.rl_item_cart);
        btnBuyNow = findViewById(R.id.btn_buy_now);
        btnPay = findViewById(R.id.btn_pay);
        tvSumPrice = findViewById(R.id.tv_sum_price);
        imgBack = findViewById(R.id.img_back);

    }

    private void setViewActionBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
    }

    private void eventClickBackImg() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setOnClickButtonPay() {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, PayActivity.class));
            }
        });
    }

    private void getDataCart() {
        carts = DataLocalManager.getCarts();
        if (DataLocalManager.getUser() != null) {
            getCartData(DataLocalManager.getUser().getIdUser());
//            DataLocalManager.removeDataCarts();
        }
        setViewCart();
    }

    private void setViewCart() {
        if (carts.size() == 0) {
            cartEmptyLayout.setVisibility(View.VISIBLE);
            cartItemLayout.setVisibility(View.INVISIBLE);
        } else {
            cartEmptyLayout.setVisibility(View.INVISIBLE);
            cartItemLayout.setVisibility(View.VISIBLE);
            cartAdapter = new CardAdapter(this, carts, iCallbackActivity, itemClickListener);
            cartLayout = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
            rcvCart.setLayoutManager(cartLayout);
            rcvCart.setAdapter(cartAdapter);
        }
        btnPay.setText(String.format("Thanh toán (%s)", carts.size()));

    }

    private ICallbackActivity iCallbackActivity = new ICallbackActivity() {
        @Override
        public void callback(Object object) {
            setSumPriceCart();
            btnPay.setText(String.format("Thanh toán (%s)", carts.size()));
            if (carts.size() == 0) {
                cartEmptyLayout.setVisibility(View.VISIBLE);
                cartItemLayout.setVisibility(View.INVISIBLE);
            }
            DetailProductActivity detailProductActivity = new DetailProductActivity();
            Cart cart = (Cart) object;
            detailProductActivity.addCart(CartActivity.this, cart.getProduct().getIdProduct(), cart.getQuantity());
        }
    };

    private void setSumPriceCart() {
        int sumPrice = 0;
        for (Cart cart : carts) {
            int price = Integer.parseInt(cart.getProduct().getSellingPrice().split(",")[0]);
            int quantity = cart.getQuantity();
            int unit = (cart.getProduct().getSellingPrice().split(",").length - 1) * 1000;
            sumPrice += price * quantity * unit;
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvSumPrice.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(sumPrice)))));
    }

    private ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void onClick(View view, List<Object> list, int position, boolean isLongClick) {
            Intent intent = new Intent(CartActivity.this, DetailProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", (Serializable) list);
            intent.putExtras(bundle);
            someActivityResultLauncher.launch(intent);
        }
    };

    private void setOnClickButtonBuyNow() {
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataLocalManager.getUser() == null) {
                    someActivityResultLauncher.launch(new Intent(CartActivity.this, MainActivity.class));
                } else {
                    someActivityResultLauncher.launch(new Intent(CartActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e("TAG", "onActivityResult: ");

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        String userName = data.getStringExtra("result");
                        NameUserActivity nameUserActivity = new NameUserActivity();
                        nameUserActivity.updateNickname(CartActivity.this, DataLocalManager.getUser().getIdUser(), userName);
                        getCartData(DataLocalManager.getUser().getIdUser());
                    }
                }
            });

    private void getCartData(String idUser) {
        mStringRequest = new StringRequest(Request.Method.POST, CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String idProduct = object.getString("id_product");
                        int quantity = object.getInt("quantityC");
                        getProductCart(CartActivity.this, idProduct);
                        cart.setQuantity(quantity);
                        carts.add(cart);
                    }
                    DataLocalManager.addCarts(carts);
                    setViewCart();
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", idUser);
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(mStringRequest);
    }

    public void getProductCart(Context context, String idProduct) {
        mStringRequest = new StringRequest(Request.Method.POST, PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String idProduct = object.getString("id_product");
                        int idCategory = object.getInt("id_category");
                        String name = object.getString("name");
                        String imageThumb = object.getString("image_thumb");
                        JSONArray imageLarge = object.getJSONArray("image_large");
                        String sellingPrice = object.getString("selling_price");
                        String color = object.getString("color");
                        String size = object.getString("size");
                        String quantity = object.getString("quantity");
                        String description = object.getString("description");
                        String rate = object.getString("rate");
                        int discount = object.getInt("discount");
                        if (discount == 0)
                            product = new Product(idProduct, idCategory, name, imageThumb, imageLarge, sellingPrice, color, size, quantity, description, rate, false);
                        else
                            product = new Product(idProduct, idCategory, name, imageThumb, imageLarge, sellingPrice, color, size, quantity, description, rate, true);
                    }
                    cart = new Cart(product, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id_product", idProduct);

                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
    }
}
