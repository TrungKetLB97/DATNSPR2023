package com.app.projectfinal_master.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.HomeFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private HomeFragmentAdapter homeFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        setFragmentManager();
        setContentViewPager();
        getEventSelectedTabLayout();
    }

    private void initView()
    {
        tabLayout = (TabLayout) findViewById(R.id.tab_select);
        viewPager2 = (ViewPager2) findViewById(R.id.view_pager);
    }

    private void setFragmentManager()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        homeFragmentAdapter = new HomeFragmentAdapter(fragmentManager, getLifecycle());

    }

    private void setContentViewPager()
    {
        viewPager2.setAdapter(homeFragmentAdapter);
        viewPager2.setUserInputEnabled(false);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void getEventSelectedTabLayout()
    {
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
}