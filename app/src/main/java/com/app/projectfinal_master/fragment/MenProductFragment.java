package com.app.projectfinal_master.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.SliderAdapter;
import com.app.projectfinal_master.model.SliderItem;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class MenProductFragment extends Fragment {

    private View view;
    private ViewPager2 viewPager2;
    private DotsIndicator indicator;
    private final Handler sliderHandler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_woman_product, container, false);
        initView();
        setListSliderItemTest();
        setDataIndicator();
        return view;
    }

    private void initView() {
        viewPager2 = view.findViewById(R.id.view_pager);
        indicator = view.findViewById(R.id.indicator);
    }

    private void setListSliderItemTest()
    {
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
}
