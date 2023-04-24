package com.app.projectfinal_master.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.SignInFragmentAdapter;
import com.app.projectfinal_master.utils.CheckStateNetwork;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private AppCompatImageView imgClose;
    private FragmentManager fragmentManager;
    private SignInFragmentAdapter signInFragmentAdapter;

    private LinearLayoutCompat layoutLoginWithGoogle;
    public GoogleSignInOptions gso;
    public GoogleSignInClient gsc;
    public GoogleSignInAccount gsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setFragmentManager();
        setContentViewPager();
        setOnClickLoginGoogleButton();
        getEventSelectedTabLayout();
        registerGoogleRequest();
        eventClickCloseButton();
    }

    private void initView()
    {
        tabLayout = findViewById(R.id.tab_select);
        viewPager2 = findViewById(R.id.view_pager);
        layoutLoginWithGoogle = findViewById(R.id.layout_login_with_google);
        imgClose = findViewById(R.id.img_close);
    }

    private void eventClickCloseButton() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setFragmentManager()
    {
        fragmentManager = getSupportFragmentManager();
        signInFragmentAdapter = new SignInFragmentAdapter(fragmentManager, getLifecycle());
        setDataCallBackActivity();
    }

    private void setContentViewPager()
    {
        viewPager2.setAdapter(signInFragmentAdapter);
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

    private void setDataCallBackActivity() {
        signInFragmentAdapter.setCallbackActivity(new ICallbackActivity() {
            @Override
            public void callback(Object object) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", (String) gsa.getDisplayName());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private void registerGoogleRequest() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
    }

    private void setOnClickLoginGoogleButton() {
        layoutLoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckStateNetwork.isNetworkAvailable(MainActivity.this)) return;
                signInWithGoogle();
            }
        });
    }

    private void signInWithGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                gsa = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                assert gsa != null;
                signInFragmentAdapter.signInFragment.signIn(gsa.getEmail(), "", gsa.getDisplayName(), 1);
//                setDataCallBackActivity();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
}