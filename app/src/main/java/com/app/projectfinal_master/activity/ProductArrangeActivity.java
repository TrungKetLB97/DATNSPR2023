package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.PRODUCTS_CATEGORY;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.ProductAdapter2;
import com.app.projectfinal_master.model.Category;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductArrangeActivity extends AppCompatActivity implements ItemClickListener {

    private Toolbar toolbar;
    private ActionBar actionBar;

    private ProductAdapter2 productAdapter2;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rcvProduct;

    private Category category;

    private ProgressBar progressBar;
    private TextView tvTitle;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_arrange);
        initView();
        setViewActionBar();
        setViewRcv();
        getDataBundle();
        eventClickBackImg();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        rcvProduct = findViewById(R.id.rcv_product);
        progressBar = findViewById(R.id.progress);
        tvTitle = findViewById(R.id.tv_title);
        imgBack = findViewById(R.id.img_back);
    }

    private void setViewActionBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_arrange, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_cart:
                Intent intent = new Intent(this, CartActivity.class);
                someActivityResultLauncher.launch(intent);
                break;
            case R.id.act_search:
                someActivityResultLauncher.launch(new Intent(this, SearchActivity.class));
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void eventClickBackImg() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setViewRcv() {
        rcvProduct.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutManager = new GridLayoutManager(this, 2);
        productAdapter2 = new ProductAdapter2(Product.itemCallback, this, this);
        rcvProduct.setLayoutManager(layoutManager);
        rcvProduct.setAdapter(productAdapter2);
    }

    private void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        category = new Category();
        category.setIdCategory(bundle.getInt("id_category"));
        category.setCategory(bundle.getString("category"));
        tvTitle.setText(category.getCategory());
//        category.setSex(bundle.getString("sex"));
        getProductsWithCategory(ProductArrangeActivity.this, category.getIdCategory());
    }

    private void addItem(Product product) {
        List<Product> products = new ArrayList<>(productAdapter2.getCurrentList());
//        if (product.getIdCategory() == category.getIdCategory() && product.getSex().equals(category.getSex())) {
//
//        }
        products.add(product);
        productAdapter2.submitList(products);
    }

    private void getProductsWithCategory(Context context, int idCategory) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PRODUCTS_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String codeProduct = object.getString("code_product");
                        String name = object.getString("name");
                        String sex = object.getString("sex");
                        String imageThumb = object.getString("image_thumb");
                        String sellingPrice = object.getString("selling_price");
                        int quantity = object.getInt("quantity");
                        int discount = object.getInt("discount");
                        Product product = new Product(codeProduct, idCategory, name, sex, imageThumb, sellingPrice, quantity, discount);
                        addItem(product);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_category", String.valueOf(idCategory));
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
    }


    @Override
    public void onClick(View view, List<Object> list, int position, boolean isLongClick) {
        List<Product> products = new ArrayList<>(productAdapter2.getCurrentList());

//        products.remove(position);
//        productAdapter2.submitList(products);
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

                    }
                }
            });
}