package com.app.projectfinal_master.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.app.projectfinal_master.fragment.SignInFragment;
import com.app.projectfinal_master.fragment.SignUpFragment;

public class MainFragmentAdapter extends MyFragmentAdapter {
    public MainFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1)
            return new SignUpFragment();
        return new SignInFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
