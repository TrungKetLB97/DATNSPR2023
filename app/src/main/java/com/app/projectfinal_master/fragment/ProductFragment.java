package com.app.projectfinal_master.fragment;

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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.CartActivity;
import com.app.projectfinal_master.adapter.ProductFragmentAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.android.material.tabs.TabLayout;

public class ProductFragment extends Fragment {
    private View view;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private AppCompatActivity activity;
    private AHBottomNavigation bottomNavigation;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
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
    }

    private void setViewActionBar() {
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setTitle(null);
    }

    private void setFragmentManager() {
        FragmentManager fragmentManager = getParentFragmentManager();
        productFragmentAdapter = new ProductFragmentAdapter(fragmentManager, getLifecycle());
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
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Men"));
        tabLayout.addTab(tabLayout.newTab().setText("Woman"));
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