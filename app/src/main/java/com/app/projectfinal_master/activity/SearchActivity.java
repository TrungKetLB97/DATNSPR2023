package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.SEARCH_PRODUCT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
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
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchActivity extends AppCompatActivity {

    private ProductAdapter2 productAdapter2;
    private RecyclerView.LayoutManager layoutManager;

    private ProgressBar progressBar;
    private RecyclerView rcvProduct;
    private EditText edtSearch;
    private ImageView imgSearch;
    private ImageView imgBack;

    List<Product> products = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        setViewRcv();
        onClickSearch();
        eventClickBackImg();
    }

    private void initView() {
        edtSearch = findViewById(R.id.edt_search);
        imgSearch = findViewById(R.id.img_search);
        rcvProduct = findViewById(R.id.rcv_product);
        progressBar = findViewById(R.id.progress);
        imgBack = findViewById(R.id.img_back);
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
        productAdapter2 = new ProductAdapter2(Product.itemCallback, this, null);
        rcvProduct.setLayoutManager(layoutManager);
        rcvProduct.setAdapter(productAdapter2);
    }

    private void onClickSearch() {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetItem();
                getProductsWithName(edtSearch.getText().toString());
            }
        });
    }

    private void getProductsWithName(String name) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SEARCH_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String codeProduct = object.getString("code_product");
                        int idCategory = object.getInt("id_category");
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
                params.put("name", name);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        VolleySingleton.getInstance(this).getRequestQueue().add(stringRequest);
    }

    private void resetItem() {
        products.clear();
        productAdapter2.submitList(products);
    }

    private void addItem(Product product) {
//        if (product.getIdCategory() == category.getId() && product.getSex().equals(category.getSex())) {
        products.add(product);
        productAdapter2.submitList(products);
//        }
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();

                    }
                }
            });
}