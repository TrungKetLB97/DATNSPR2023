package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.CART;
import static com.app.projectfinal_master.utils.Constant.COLOR;
import static com.app.projectfinal_master.utils.Constant.INSERT_CART;
import static com.app.projectfinal_master.utils.Constant.SIZE;
import static com.app.projectfinal_master.utils.Constant.UPDATE_ITEM_CART;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.app.projectfinal_master.adapter.CartAdapter;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Cart;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.model.Size;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ProgressBar progressBar;

    private Button btnBuyNow, btnPay;
    private TextView tvSumPrice;
    private ImageView imgBack;
    private CheckBox checkBox;

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
        setOnClickButtonPay();
        setOnClickButtonBuyNow();
        eventClickCheckBox();
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
        progressBar = findViewById(R.id.progress);
        checkBox = findViewById(R.id.check_box);
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

    private void eventClickCheckBox() {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Cart cart :
                        carts) {
                    cart.setChose(checkBox.isChecked());
                }
                setViewCart();
                setSumPriceCart(carts);
                changeStateButtonPay(carts);
            }
        });
    }

    private void setOnClickButtonPay() {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataLocalManager.getUser() != null) {
                    Intent intent = new Intent(CartActivity.this, PayActivity.class);
                    intent.putExtra("carts", (Serializable) carts);
                    someActivityResultLauncher.launch(intent);
                    finish();
                } else
                    someActivityResultLauncher.launch(new Intent(CartActivity.this, MainActivity.class));
            }
        });
    }

    private void getDataCart() {
        carts = new ArrayList<>();
        if (DataLocalManager.getUser() != null) {
//            DataLocalManager.removeDataCarts();
            callApiGetCart(DataLocalManager.getUser().getIdUser());
            setViewCart();
            return;
        }
        carts = DataLocalManager.getCarts();
        setViewCart();
        setSumPriceCart(carts);
    }

    private void setViewCart() {
        if (carts!= null && carts.size() == 0) {
            cartEmptyLayout.setVisibility(View.VISIBLE);
            cartItemLayout.setVisibility(View.INVISIBLE);
        } else {
            cartEmptyLayout.setVisibility(View.INVISIBLE);
            cartItemLayout.setVisibility(View.VISIBLE);
            cartAdapter = new CartAdapter(this, carts, iCallbackActivity, itemClickListener);
            cartLayout = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
            rcvCart.setLayoutManager(cartLayout);
            rcvCart.setAdapter(cartAdapter);
        }
        changeStateButtonPay(carts);
    }

    private void changeStateButtonPay(List<Cart> carts) {
        int countProduct = 0;
        for (Cart cart :
                carts) {
            if (cart.isChose())
                countProduct += cart.getQuantity();
        }
        btnPay.setText(String.format("Thanh toán (%s)", countProduct));
        if (countProduct == 0) {
            btnPay.setClickable(false);
            btnPay.setFocusable(false);
            btnPay.setEnabled(false);
        } else {
            btnPay.setClickable(true);
            btnPay.setFocusable(true);
            btnPay.setEnabled(true);
        }
    }

    private final ICallbackActivity iCallbackActivity = new ICallbackActivity() {
        @Override
        public void callback(Object object) {
            setSumPriceCart(carts);
            changeStateButtonPay(carts);
            if (carts.size() == 0) {
                cartEmptyLayout.setVisibility(View.VISIBLE);
                cartItemLayout.setVisibility(View.INVISIBLE);
            }
            Cart cart = (Cart) object;
            int chose;
            if (cart.isChose())
                chose = 0;
            else
                chose = 1;
            callApiUpdateItemCart(CartActivity.this, cart.getProduct().getCodeProduct(), cart.getProduct().getIdColor(), cart.getProduct().getIdSize(), cart.getQuantity(), chose);
        }
    };

    private void setSumPriceCart(List<Cart> carts) {
        int sumPrice = 0;
        for (Cart cart : carts) {
            if (cart.isChose())
                sumPrice += Integer.parseInt(cart.getProduct().getSellingPrice()) * cart.getQuantity();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvSumPrice.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(sumPrice)))));
    }

    private final ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void onClick(View view, List<Object> list, int position, boolean isLongClick) {
            Intent intent = new Intent(CartActivity.this, DetailProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("code_product", carts.get(position).getProduct().getCodeProduct());
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
                }
                finish();
            }
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        String username = data.getStringExtra("result");
//                        UpdateUsernameActivity nameUserActivity = new UpdateUsernameActivity();
//                        nameUserActivity.updateUsername(CartActivity.this, DataLocalManager.getUser().getIdUser(), username);
                        if (DataLocalManager.getCarts().size() > 0) {
                            for (Cart cartLocal : DataLocalManager.getCarts()) {
                                callApiAddCart(CartActivity.this, cartLocal.getProduct().getCodeProduct(), cartLocal.getProduct().getIdColor(), cartLocal.getProduct().getIdSize(), cartLocal.getQuantity());
                            }
                            getDataCart();
                        }
                    }
                }
            });

    private void callApiGetCart(String idUser) {
        progressBar.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.POST, CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String codeProduct = object.getString("code_product");
                        int idColor = object.getInt("id_color");
                        int idSize = object.getInt("id_size");
                        int quantityC = object.getInt("quantityC");
                        int chose = object.getInt("chose");
                        String name = object.getString("name");
                        String color = object.getString("color");
                        String size = object.getString("size");
                        String imageThumb = object.getString("image_thumb");
                        String sellingPrice = object.getString("selling_price");
                        int quantityP = object.getInt("quantityP");
                        int discount = object.getInt("discount");
                        product = new Product(codeProduct, idColor, idSize, name, imageThumb, sellingPrice, quantityP, discount);
                        product.setColor(color);
                        product.setSize(size);
                        Log.e("TAG", "onResponse: "+chose );
                        if (chose == 0)
                            addItemCart(product, quantityC, true);
                        else
                            addItemCart(product, quantityC, false);
                    }
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

    public void callApiAddCart(Context context, String codeProduct, int idColor, int idSize, int quantity) {
        progressBar.setVisibility(View.VISIBLE);
        if (DataLocalManager.getUser() != null)
            mStringRequest = new StringRequest(Request.Method.POST, INSERT_CART, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject object = new JSONObject(response);
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
                    params.put("id_user", DataLocalManager.getUser().getIdUser());
                    params.put("code_product", codeProduct);
                    params.put("id_color", String.valueOf(idColor));
                    params.put("id_size", String.valueOf(idSize));
                    params.put("quantity", String.valueOf(quantity));
                    return params;
                }
            };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
    }

    public void callApiUpdateItemCart(Context context, String codeProduct, int idColor, int idSize, int quantity, int chose) {
        progressBar.setVisibility(View.VISIBLE);
        if (DataLocalManager.getUser() != null)
            mStringRequest = new StringRequest(Request.Method.POST, UPDATE_ITEM_CART, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject object = new JSONObject(response);
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
                    params.put("id_user", DataLocalManager.getUser().getIdUser());
                    params.put("code_product", codeProduct);
                    params.put("id_color", String.valueOf(idColor));
                    params.put("id_size", String.valueOf(idSize));
                    params.put("quantity", String.valueOf(quantity));
                    params.put("chose", String.valueOf(chose));
                    return params;
                }
            };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
    }

    public void callApiSize(Context context, int idSize, Cart cart) {
        progressBar.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.POST, SIZE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int id = object.getInt("id_size");
                        String size = object.getString("size");
                        Size sizeModel = new Size(id, size);
                        cart.getProduct().setSize(size);
                    }
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
                params.put("id_size", String.valueOf(idSize));
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
    }

    public void callApiColor(Context context, int idColor, Cart cart) {
        progressBar.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.POST, COLOR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int id = object.getInt("id_color");
                        String color = object.getString("color");
                        String code = object.getString("code");
                        cart.getProduct().setColor(color);
                    }
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
                params.put("id_color", String.valueOf(idColor));
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
    }

    private void addItemCart(Product product, int quantity, boolean chose) {
        cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(quantity);
        cart.setChose(chose);
        carts.add(cart);
//        DataLocalManager.addCarts(carts);
        setViewCart();
        setSumPriceCart(carts);
    }
}
