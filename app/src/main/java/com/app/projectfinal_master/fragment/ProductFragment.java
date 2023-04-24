package com.app.projectfinal_master.fragment;

import static com.app.projectfinal_master.utils.Constant.CATEGORIES;
import static com.app.projectfinal_master.utils.Constant.PRODUCTS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.CartActivity;
import com.app.projectfinal_master.activity.SearchActivity;
import com.app.projectfinal_master.adapter.ProductFragmentAdapter;
import com.app.projectfinal_master.model.Category;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.VolleySingleton;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductFragment extends Fragment {
    private View view;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private AppCompatActivity activity;
    private AHBottomNavigation bottomNavigation;
    private EditText edtSearch;

    private Product product;
    public List<Product> products;

    private Category category;
    private List<Category> categories;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ProgressBar progressBar;
    private ProductFragmentAdapter productFragmentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container, false);
        activity = (AppCompatActivity) getActivity();
        initView();
        onClickEdtSearch();
        setViewActionBar();
        setFragmentManager();
        setContentViewPager();
        getEventSelectedTabLayout();
        return view;
    }

    private void initView() {
        toolbar = (Toolbar) view.findViewById(R.id.toolbarCart);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_select);
        viewPager2 = (ViewPager2) view.findViewById(R.id.view_pager);
        progressBar = view.findViewById(R.id.progress);
        edtSearch = view.findViewById(R.id.edt_search);
    }

    private void onClickEdtSearch() {
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(getContext(), SearchActivity.class));
            }
        });
    }

    private void setViewActionBar() {
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setTitle(null);
    }

    private void setFragmentManager() {
        FragmentManager fragmentManager = getParentFragmentManager();
        productFragmentAdapter = new ProductFragmentAdapter(fragmentManager, getLifecycle());
        getProducts();
        getCategory();
    }

    private void setContentViewPager() {
        viewPager2.setAdapter(productFragmentAdapter);
        viewPager2.setUserInputEnabled(false);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void getEventSelectedTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.home));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.men));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.woman));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_cart:
                Intent intent = new Intent(getContext(), CartActivity.class);
                someActivityResultLauncher.launch(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getProducts() {
        progressBar.setVisibility(View.VISIBLE);
        products = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTS, new Response.Listener<String>() {
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
                        product = new Product(codeProduct, idCategory, name, sex, imageThumb, sellingPrice, quantity, discount);
                        products.add(product);
                    }
                    productFragmentAdapter.setDataProducts(products);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setShouldCache(false);
        VolleySingleton.getInstance(getContext()).getRequestQueue().add(stringRequest);
    }

    private void getCategory() {
        categories = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CATEGORIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                int idCategory = object.getInt("id_category");
                                String categoryTitle = object.getString("category");
                                Log.e("TAG", "onResponse: "+ categoryTitle);
                                String sex = object.getString("sex");
                                String image = object.getString("image");

                                category = new Category(idCategory, categoryTitle, sex, image);
                                categories.add(category);
                            }
                            productFragmentAdapter.setDataCategories(categories);
                        } catch (Exception e) {

                        }
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