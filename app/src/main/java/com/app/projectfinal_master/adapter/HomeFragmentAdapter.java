package com.app.projectfinal_master.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.app.projectfinal_master.fragment.HomeProductFragment;
import com.app.projectfinal_master.fragment.MenProductFragment;
import com.app.projectfinal_master.fragment.WomanProductFragment;

public class HomeFragmentAdapter extends MyFragmentAdapter {
    public HomeFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1)
            return new MenProductFragment();
        else if (position == 2)
            return new WomanProductFragment();
        return new HomeProductFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
