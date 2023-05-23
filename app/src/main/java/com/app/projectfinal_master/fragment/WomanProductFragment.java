package com.app.projectfinal_master.fragment;

import static com.app.projectfinal_master.utils.Constant.CATEGORIES;
import static com.app.projectfinal_master.utils.Constant.PRODUCTS;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.CategoryAdapter;
import com.app.projectfinal_master.adapter.ProductAdapter;
import com.app.projectfinal_master.adapter.SliderAdapter;
import com.app.projectfinal_master.model.Category;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.model.SliderItem;
import com.app.projectfinal_master.utils.VolleySingleton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WomanProductFragment extends Fragment {

    private View view;
    private ViewPager2 viewPager2;
    private DotsIndicator indicator;
    private final Handler sliderHandler = new Handler();

    private Product product;
    private List<Product> products = new ArrayList<>();
    private List<Product> saleProducts = new ArrayList<>();
    private List<Category> categories;
    private RecyclerView rcvCategories, rcvBestProducts, rcvSaleProducts;
    private RecyclerView.Adapter productAdapter, categoryAdapter;
    private RecyclerView.LayoutManager bestProductLayout, categoryLayout, saleProductLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_woman_product, container, false);
        initView();
        setListSliderItemTest();
        setDataIndicator();
        setRcvCategories();
        setRcvBestProductList();
        setRcvSaleProductList();
        return view;
    }

    private void initView() {
        viewPager2 = view.findViewById(R.id.view_pager);
        indicator = view.findViewById(R.id.indicator);

        rcvCategories = view.findViewById(R.id.rcv_category);
        rcvBestProducts = view.findViewById(R.id.rcv_best_product);
        rcvSaleProducts = view.findViewById(R.id.rcv_recommend_products);
    }

    public void setDataProducts(@NonNull List<Product> products) {
        this.products = new ArrayList<>();
        for (Product product :
                products) {
            if (product.getSex().equals("Nữ")) this.products.add(product);
        }
        for (Product product :
                this.products) {
            addItemSaleProducts(product);
        }
    }

    public void setDataCategories(@NonNull List<Category> categories) {
        this.categories = new ArrayList<>();
        for (Category category :
                categories) {
            if (category.getSex().equals("Nữ")) this.categories.add(category);
        }
    }

    private void addItemSaleProducts(@NonNull Product product) {
        if (product.getDiscount() == 0)
            saleProducts.add(product);
    }

    private void setRcvCategories() {
        categoryAdapter = new CategoryAdapter(getContext(), categories);
        categoryLayout = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        rcvCategories.setLayoutManager(categoryLayout);
        rcvCategories.setAdapter(categoryAdapter);
    }

    private void setRcvBestProductList() {
        productAdapter = new ProductAdapter(getContext(), products);
        bestProductLayout = new GridLayoutManager(getContext(), 2);
        rcvBestProducts.setLayoutManager(bestProductLayout);
        rcvBestProducts.setAdapter(productAdapter);
    }

    private void setRcvSaleProductList() {
        productAdapter = new ProductAdapter(getContext(), saleProducts);
        saleProductLayout = new GridLayoutManager(getContext(), 2);
        rcvSaleProducts.setLayoutManager(saleProductLayout);
        rcvSaleProducts.setAdapter(productAdapter);
    }

    private void setListSliderItemTest() {
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.banner_1));
        sliderItems.add(new SliderItem(R.drawable.banner_2));
        sliderItems.add(new SliderItem(R.drawable.banner_3));
        sliderItems.add(new SliderItem(R.drawable.banner_4));
        sliderItems.add(new SliderItem(R.drawable.banner_5));

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
}