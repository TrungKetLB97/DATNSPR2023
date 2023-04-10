package com.app.projectfinal_master.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.HomeFragmentAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class HomeActivity extends AppCompatActivity {

    private AHBottomNavigation bottomNavigation;
    private HomeFragmentAdapter homeFragmentAdapter;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        setFragmentManager();
        setContentViewPager();
    }

    private void initView()
    {
        viewPager2 = (ViewPager2) findViewById(R.id.view_pager);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.AHBottomNavigation);
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
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                tabLayout.selectTab(tabLayout.getTabAt(position));
//            }
//        });
        setBottomBar();

    }

    private void setBottomBar() {
        AHBottomNavigationItem home = new AHBottomNavigationItem(R.string.home, R.drawable.custom_drawable_bottom_nav_home, R.color.black);
        AHBottomNavigationItem cart = new AHBottomNavigationItem(R.string.notifications, R.drawable.custom_drawable_bottom_nav_notifications, R.color.black);
        AHBottomNavigationItem user = new AHBottomNavigationItem(R.string.user, R.drawable.custom_drawable_bottom_nav_user, R.color.black);

        bottomNavigation.addItem(home);
        bottomNavigation.addItem(cart);
        bottomNavigation.addItem(user);

        bottomNavigation.setNotificationTextColorResource(R.color.white);
        bottomNavigation.setDefaultBackgroundResource(R.color.white);
        bottomNavigation.setTitleActiveColor(0, R.color.black);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager2.setCurrentItem(position);
                bottomNavigation.setIconActiveColor(position, R.color.black);
                bottomNavigation.setTitleActiveColor(position, R.color.black);
                return true;
            }
        });
    }
}