package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.FAVORITE;
import static com.app.projectfinal_master.utils.Constant.INSERT_CART;
import static com.app.projectfinal_master.utils.Constant.PRODUCT_DETAIL;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.ImageAdapter;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Cart;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.AnimationUtil;
import com.app.projectfinal_master.utils.ArrayUtil;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DetailProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private StringRequest mStringRequest;
    private Product detailProduct;

    private Cart cart;
    private List<Cart> carts;

    private ViewPager2 viewPager2;
    private RecyclerView rcvDetailProduct;
    private RecyclerView.Adapter detailProductAdapter;
    private RecyclerView.LayoutManager detailProductLayout;

    private TextView tvName, tvPrice;
    private Button btnAddCart;
    private ImageView imgAddFavorite, imgReturn, imgViewAnim, imgStartAnim;
    private View imgEndAnim;
    private Handler setDelay = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initView();
        getCart();
        getProductDetail(this, getIdProduct());
        setViewActionBar();
        setEventClickButtonBuy();
        setEventClickButtonFavorite();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) findViewById(R.id.tv_product_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        viewPager2 = (ViewPager2) findViewById(R.id.vp2_img_details);
        imgAddFavorite = (ImageView) findViewById(R.id.img_add_favorite);
        imgStartAnim = (ImageView) findViewById(R.id.img_start_anim);
        imgViewAnim = (ImageView) findViewById(R.id.img_view_anim);
        btnAddCart = (Button) findViewById(R.id.btn_add_cart);
    }

    private void getCart() {
        carts = DataLocalManager.getCarts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_product, menu);
        imgEndAnim = findViewById(menu.getItem(0).getItemId());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_cart:
                startActivity(new Intent(DetailProductActivity.this, CartActivity.class));
                break;
            case R.id.a:

                break;
            case R.id.b:

                break;
            case R.id.c:

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViewActionBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
    }

    private String getIdProduct() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return null;
        String idProduct = bundle.getString("id_product");
        return idProduct;
    }

    private void setEventClickButtonFavorite() {
        imgAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavorite("1");
            }
        });
    }


    private void setEventClickButtonBuy() {
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddCart.setEnabled(false);
                cart = new Cart(detailProduct, 1);
                addCart(DetailProductActivity.this, carts, cart);
                AnimationUtil.translateAnimation(imgViewAnim, imgStartAnim, imgEndAnim, new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Runnable startDelay = new Runnable() {
                    @Override
                    public void run() {
                        btnAddCart.setEnabled(true);
                    }
                };
                setDelay.postDelayed(startDelay, AnimationUtil.ANIMATION_DURATION + 100);
            }
        });
    }

    public void addCart(Context context, List<Cart> carts, Cart cart) {
        if (carts != null) {
            for (int i = 0; i < carts.size(); i++) {
                if (Objects.equals(cart.getProduct().getIdProduct(), carts.get(i).getProduct().getIdProduct())) {
                    carts.get(i).setQuantity(carts.get(i).getQuantity() + 1);
                    addCart(context, cart.getProduct().getIdProduct(), carts.get(i).getQuantity());
                    DataLocalManager.changeDataCart(carts.get(i), i);
                    return;
                }
            }
            carts.add(cart);
            DataLocalManager.addCarts(carts);
            addCart(context, cart.getProduct().getIdProduct(), 1);
        }
    }

    public void addCart(Context context, String idProduct, int quantity) {
        if (DataLocalManager.getUser() != null)
            mStringRequest = new StringRequest(Request.Method.POST, INSERT_CART, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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
                    params.put("id_product", idProduct);
                    params.put("quantity", String.valueOf(quantity));
                    return params;
                }
            };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
    }

    public Product getProductDetail(Context context, String idProduct) {
        mStringRequest = new StringRequest(Request.Method.POST, PRODUCT_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int idCategory = object.getInt("id_category");
                        String name = object.getString("name");
                        String sex = object.getString("sex");
                        String imageThumb = object.getString("image_thumb");
                        JSONArray imageLarge = object.getJSONArray("image_large");
                        String sellingPrice = object.getString("selling_price");
                        String color = object.getString("color");
                        String size = object.getString("size");
                        String quantity = object.getString("quantity");
                        double rate = object.getDouble("rate");
                        String description = object.getString("description");
                        int discount = object.getInt("discount");

                        detailProduct = new Product(idProduct, idCategory, name, sex, imageThumb, imageLarge, sellingPrice, color, size, quantity, rate, description, discount);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
                setViewProductDetail();
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
        VolleySingleton.getInstance(DetailProductActivity.this).getRequestQueue().add(mStringRequest);

        return detailProduct;
    }

    private void setViewProductDetail() {
        tvName.setText(detailProduct.getName());
        tvPrice.setText(detailProduct.getSellingPrice());
        viewPager2.setAdapter(new ImageAdapter(DetailProductActivity.this, getListImageLarge(), viewPager2));
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void addFavorite(final String id_user) {
        mStringRequest = new StringRequest(Request.Method.POST, FAVORITE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailProductActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);
                params.put("id_product", detailProduct.getIdProduct());

                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(DetailProductActivity.this).getRequestQueue().add(mStringRequest);
    }

    private List<String> getListImageLarge() {
        Log.e("TAG", "getListImageLarge: " + detailProduct.getImageLarge());
        List<Object> img = ArrayUtil.convert(detailProduct.getImageLarge());

        List<String> imageItems = new ArrayList<>();
        for (Object item : img) {
            imageItems.add(item.toString());
        }
        return imageItems;
    }
}