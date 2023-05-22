package com.app.projectfinal_master.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.app.projectfinal_master.fragment.HomeProductFragment;
import com.app.projectfinal_master.fragment.MenProductFragment;
import com.app.projectfinal_master.fragment.WomanProductFragment;
import com.app.projectfinal_master.model.Category;
import com.app.projectfinal_master.model.Product;

import java.util.List;

public class ProductFragmentAdapter extends MyFragmentAdapter {
    private List<Category> categories;
    private List<Product> products;
    private HomeProductFragment homeProductFragment;
    private MenProductFragment menProductFragment;
    private WomanProductFragment womanProductFragment;

    public void setDataProducts(List<Product> products) {
        this.products = products;
        homeProductFragment.setDataProducts(products);
    }

    public void setDataCategories(List<Category> categories) {
        this.categories = categories;
        homeProductFragment.setDataCategories(categories);
    }

    public ProductFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        menProductFragment = new MenProductFragment();
        womanProductFragment = new WomanProductFragment();
        homeProductFragment = new HomeProductFragment();
        if (position == 1) {
            menProductFragment.setDataProducts(products);
            menProductFragment.setDataCategories(categories);
            return menProductFragment;
        } else if (position == 2) {
            womanProductFragment.setDataProducts(products);
            womanProductFragment.setDataCategories(categories);
            return womanProductFragment;
        } else {
            return homeProductFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
