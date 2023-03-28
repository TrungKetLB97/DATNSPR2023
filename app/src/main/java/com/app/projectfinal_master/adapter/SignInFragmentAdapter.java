package com.app.projectfinal_master.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.app.projectfinal_master.fragment.SignInFragment;
import com.app.projectfinal_master.fragment.SignUpFragment;
import com.app.projectfinal_master.utils.ICallbackActivity;

public class SignInFragmentAdapter extends MyFragmentAdapter {
    public SignInFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public SignInFragment signInFragment;
    public SignUpFragment signUpFragment;

    private ICallbackActivity iCallbackActivity;

    public void setCallbackActivity(ICallbackActivity callbackActivity) {
        this.iCallbackActivity = callbackActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        signUpFragment = new SignUpFragment();
        signInFragment = new SignInFragment();

        if (position == 1) {
            return signUpFragment;

        } else {
            signInFragment.setCallbackActivity(iCallbackActivity);
            return signInFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
