package com.app.projectfinal_master.fragment;

import static com.app.projectfinal_master.utils.Constant.CATEGORIES;
import static com.app.projectfinal_master.utils.Constant.PRODUCTS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.CartActivity;
import com.app.projectfinal_master.activity.DetailProductActivity;
import com.app.projectfinal_master.adapter.CategoryAdapter;
import com.app.projectfinal_master.adapter.ProductAdapter;
import com.app.projectfinal_master.adapter.SliderAdapter;
import com.app.projectfinal_master.model.Category;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.model.SliderItem;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.app.projectfinal_master.utils.VolleySingleton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeProductFragment extends Fragment {

    private View view;
    private ViewPager2 viewPager2;
    private DotsIndicator indicator;
    private SliderAdapter sliderAdapter;
    private List<SliderItem> sliderItems;
    private List<Product> newProducts, saleProducts;
    private List<Category> categories;
    private RecyclerView rcvCategories, rcvNewProducts, rcvSaleProducts;
    private RecyclerView.Adapter productAdapter, productSaleAdapter, categoryAdapter;
    private RecyclerView.LayoutManager newProductLayout, categoryLayout, saleProductLayout;
    private final Handler sliderHandler = new Handler();
    private Product product;
    private int currentIndex = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_home_product, container, false);
        initView();
        setListSliderItemTest();
        setDataIndicator();
        getCategory();
        getNewProducts();
        return view;
    }

    private void initView() {
        viewPager2 = view.findViewById(R.id.view_pager);
        indicator = view.findViewById(R.id.indicator);
        rcvCategories = view.findViewById(R.id.rcv_category);
        rcvNewProducts = view.findViewById(R.id.rcv_new_products);
        rcvSaleProducts = view.findViewById(R.id.rcv_sale_products);
        newProductLayout = new GridLayoutManager(getContext(), 2);
        saleProductLayout = new GridLayoutManager(getContext(), 2);
        categoryLayout = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
    }

    private void setListSliderItemTest() {
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.test));
        sliderItems.add(new SliderItem(R.drawable.test1));
        sliderItems.add(new SliderItem(R.drawable.test2));
        sliderItems.add(new SliderItem(R.drawable.test3));
        sliderItems.add(new SliderItem(R.drawable.test4));

        setDataViewPager(sliderItems);
    }

    private void setDataViewPager(List<SliderItem> sliderItems) {
        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });
    }

    private void setDataIndicator() {
        indicator.attachTo(viewPager2);
        indicator.setClipChildren(false);
        indicator.setClipToPadding(false);
        indicator.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    private ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void onClick(View view, List<Object> list, int position, boolean isLongClick) {
            Intent intent = new Intent(getContext(), DetailProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", (Serializable) list.get(position));
            intent.putExtras(bundle);
            someActivityResultLauncher.launch(intent);
        }
    };

    private void getNewProducts() {
        newProducts = new ArrayList<>();
        saleProducts = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTS, new Response.Listener<String>() {
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
                        String sellingPrice = object.getString("selling_price");
                        int discount = object.getInt("discount");
                        if (discount == 0) {
                            product = new Product(idProduct, idCategory, name, imageThumb, sellingPrice, false);
                        } else {
                            product = new Product(idProduct, idCategory, name, imageThumb, sellingPrice, true);
                            saleProducts.add(product);
                        }
                        newProducts.add(product);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

                productAdapter = new ProductAdapter(getContext(), newProducts, itemClickListener);
                rcvNewProducts.setLayoutManager(newProductLayout);
                rcvNewProducts.setAdapter(productAdapter);

                productAdapter = new ProductAdapter(getContext(), saleProducts, itemClickListener);
                rcvSaleProducts.setLayoutManager(saleProductLayout);
                rcvSaleProducts.setAdapter(productAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        VolleySingleton.getInstance(getContext()).getRequestQueue().add(stringRequest);
    }

    private void getCategory() {
        categories = new ArrayList<>();
//        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CATEGORIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("TAG", "onResponse: " + response);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                String title = object.getString("title");
                                String image = object.getString("image");

                                Category category = new Category(id, title, image);
                                categories.add(category);
                            }
                        } catch (Exception e) {
//                            Log.d("listsssssssssssssssss", String.valueOf(categories.size()));
//                            Log.d("loiiiiiiiiiiiiiii", String.valueOf(e));
                        }

                        categoryAdapter = new CategoryAdapter(getContext(), categories);
                        rcvCategories.setLayoutManager(categoryLayout);
                        rcvCategories.setAdapter(categoryAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(getContext()).getRequestQueue().add(stringRequest);
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
