package com.app.projectfinal_master.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.app.projectfinal_master.fragment.HomeProductFragment;
import com.app.projectfinal_master.fragment.NotificationFragment;
import com.app.projectfinal_master.fragment.ProductFragment;
import com.app.projectfinal_master.fragment.UserFragment;
import com.app.projectfinal_master.model.Product;

import java.util.List;

public class HomeFragmentAdapter extends MyFragmentAdapter {
    public HomeFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    private List<Product> products;
    private ProductFragment productFragment;

    public void setBaseData(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1)
            return new NotificationFragment();
        else if (position == 2)
            return new UserFragment();
        else {
            productFragment = new ProductFragment();
//            productFragment.setBaseData(products);
            return productFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
