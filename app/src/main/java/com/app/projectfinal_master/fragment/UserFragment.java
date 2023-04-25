
package com.app.projectfinal_master.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.CartActivity;
import com.app.projectfinal_master.activity.MainActivity;
import com.app.projectfinal_master.activity.PersonalizationActivity;
import com.app.projectfinal_master.activity.ReceiptActivity;
import com.app.projectfinal_master.activity.SettingActivity;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.User;

public class UserFragment extends Fragment {
    private View view;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private AppCompatActivity activity;
    private Intent intent;
    private User user;

    private TextView tvNameUser;

    private LinearLayoutCompat layoutReceipt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_user, container, false);
        activity = (AppCompatActivity) getActivity();
        initView();
        checkExitsData();
        setViewActionBar();
        eventClickReceiptLayout();
        return view;
    }

    private void initView() {
        toolbar = view.findViewById(R.id.toolbar);
        tvNameUser = view.findViewById(R.id.tv_username);
        layoutReceipt = view.findViewById(R.id.layout_receipt);
    }

    private void eventClickReceiptLayout() {
        layoutReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(getActivity(), ReceiptActivity.class));
            }
        });
    }

    private void checkExitsData() {
        if (DataLocalManager.getUser() != null) {
            user = DataLocalManager.getUser();
            tvNameUser.setText(String.format("Hello, %s", user.getUsername()));
            tvNameUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    someActivityResultLauncher.launch(new Intent(getActivity(), PersonalizationActivity.class));
                }
            });
        } else {
            tvNameUser.setText(R.string.out_in);
            tvNameUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    someActivityResultLauncher.launch(new Intent(getActivity(), MainActivity.class));
                }
            });
        }
    }

    private void setViewActionBar() {
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setTitle(null);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_cart:
                intent = new Intent(getContext(), CartActivity.class);
                someActivityResultLauncher.launch(intent);
                break;
            case R.id.act_settings:
                intent = new Intent(getContext(), SettingActivity.class);
                someActivityResultLauncher.launch(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
//                        startActivity(new Intent(getContext(), PersonalizationActivity.class));
                        checkExitsData();
                    }
                }
            });
}