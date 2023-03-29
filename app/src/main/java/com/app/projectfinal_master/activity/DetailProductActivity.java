package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.FAVORITE;
import static com.app.projectfinal_master.utils.Constant.PRODUCT;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ArrayUtil;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private StringRequest mStringRequest;
    private Product detailProduct;

    private ViewPager2 viewPager2;
    private RecyclerView rcvDetailProduct;
    private RecyclerView.Adapter detailProductAdapter;
    private RecyclerView.LayoutManager detailProductLayout;

    private TextView tvName, tvPrice;
    private Button btnAddCart;
    private ImageView imgAddFavorite, imgReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initView();
        setViewActionBar();
        getProductDetail(getDataBundle());
        setEventClickButtonBuy();
        setEventClickButtonFavorite();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) findViewById(R.id.tv_product_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        viewPager2 = (ViewPager2) findViewById(R.id.vp2_img_details);
        imgAddFavorite = (ImageView) findViewById(R.id.img_add_favorite);
//        imgReturn = (ImageView) findViewById(R.id.img_return);
        btnAddCart = (Button) findViewById(R.id.btn_add_cart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_product, menu);
        return true;
    }

    private void setViewActionBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
    }

    private String getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return null;
        String code = bundle.getString("code_product");
        return code;
    }

    private void setEventClickButtonFavorite(){
        imgAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavorite("1", getDataBundle());
            }
        });
    }

    private void setEventClickButtonBuy(){
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this, CartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("code_product", getDataBundle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getProductDetail(final String code_product) {
        mStringRequest = new StringRequest(Request.Method.POST, PRODUCT, new Response.Listener<String>() {
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
                        JSONArray image_large = object.getJSONArray("image_large");
                        String selling_price = object.getString("selling_price");
                        int quantity = object.getInt("quantity");
                        String description = object.getString("description");
                        String rate = object.getString("rate");
                        int discount = object.getInt("discount");

                        tvName.setText(name);
                        tvPrice.setText(selling_price);
                        viewPager2.setAdapter(new ImageAdapter(DetailProductActivity.this, getListImageLarge(image_large), viewPager2));
//                        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                        Product product;
                        if (discount == 0)
                            product = new Product(id_category, id_color, id_size, code, name, image_large, selling_price, quantity, description, rate, false);
                        else
                            product = new Product(id_category, id_color, id_size, code, name, image_large, selling_price, quantity, description, rate, true);
                        detailProduct = product;
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
                params.put("code_product", code_product);

                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(DetailProductActivity.this).getRequestQueue().add(mStringRequest);
    }

    private void getProductSize(final String code) {
        mStringRequest = new StringRequest(Request.Method.POST, PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        int id_category = object.getInt("id_category");
                        int id_color = object.getInt("id_color");
                        int id_size = object.getInt("id_size");
                        String code = object.getString("code");
                        String name = object.getString("name");
                        JSONArray image_large = object.getJSONArray("image_large");
                        String selling_price = object.getString("selling_price");
                        int quantity = object.getInt("quantity");
                        String description = object.getString("description");
                        String rate = object.getString("rate");
                        int discount = object.getInt("discount");

                        tvName.setText(name);
                        tvPrice.setText(selling_price);
                        viewPager2.setAdapter(new ImageAdapter(DetailProductActivity.this, getListImageLarge(image_large), viewPager2));
//                        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                        Product product;
                        if (discount == 0)
                            product = new Product(id_category, id_color, id_size, code, name, image_large, selling_price, quantity, description, rate, false);
                        else
                            product = new Product(id_category, id_color, id_size, code, name, image_large, selling_price, quantity, description, rate, true);
                        detailProduct = product;
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
                params.put("code", code);

                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(DetailProductActivity.this).getRequestQueue().add(mStringRequest);
    }

    private void addFavorite(final String id_user, final String code_product) {
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
                params.put("code_product", code_product);

                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(DetailProductActivity.this).getRequestQueue().add(mStringRequest);
    }

    private List<String> getListImageLarge(JSONArray image_large){
        List<Object> img = ArrayUtil.convert(image_large);

        List<String> imageItems = new ArrayList<>();
        for (Object item : img) {
            imageItems.add(item.toString());
        }
        return imageItems;
    }
}