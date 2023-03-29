package com.app.projectfinal_master.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.MainFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MainFragmentAdapter mainFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        mainFragmentAdapter = new MainFragmentAdapter(fragmentManager, getLifecycle());

    }

    private void setContentViewPager()
    {
        viewPager2.setAdapter(mainFragmentAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void getEventSelectedTabLayout()
    {
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Register"));
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