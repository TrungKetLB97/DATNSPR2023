package com.app.projectfinal_master.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.data.DataLocalManager;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imgBack;
    private RelativeLayout layoutUpdateNickname, layoutUpdateEmail, layoutUpdatePhoneNumber, layoutUpdateAddress;
    private TextView tvUpdatePassword;
    private TextView tvNickname, tvEmail, tvPhoneNumber, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        getDateExits();
        setEventCallBackActivity();
        setEventClickNicknameLayout();
        setEventClickEmailLayout();
        setEventClickPhoneNumberLayout();
        setEventClickAddressLayout();
    }

    private void initView() {
        imgBack = findViewById(R.id.img_back);
        layoutUpdateNickname = findViewById(R.id.layout_update_nickname);
        layoutUpdateEmail = findViewById(R.id.layout_update_email);
        tvUpdatePassword = findViewById(R.id.tv_update_password);
        layoutUpdatePhoneNumber = findViewById(R.id.layout_update_phone_number);
        layoutUpdateAddress = findViewById(R.id.layout_update_address);
        tvNickname = findViewById(R.id.tv_username);
        tvEmail = findViewById(R.id.tv_email);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        tvAddress = findViewById(R.id.tv_address);
    }

    private void getDateExits() {
        if (DataLocalManager.getUser() != null) {
            setDataUsername(DataLocalManager.getUser().getUsername());
            setDataEmail(DataLocalManager.getUser().getEmail());
            setDataPhoneNumber(DataLocalManager.getUser().getPhone_number());
            setDataAddress(DataLocalManager.getUser().getAddress());
        } else {
            setDataUsername("null");
            setDataEmail("null");
            setDataPhoneNumber("null");
            setDataAddress("null");
        }
    }

    private void setDataUsername(String data) {
        if (!data.equals("null"))
            tvNickname.setText(data);
        else
            tvNickname.setText("");
    }

    private void setDataEmail(String data) {
        if (!data.equals("null"))
            tvEmail.setText(data);
        else
            tvEmail.setText("");
    }

    private void setDataPhoneNumber(String data) {
        if (!data.equals("null"))
            tvPhoneNumber.setText(data);
        else
            tvPhoneNumber.setText("");
    }

    private void setDataAddress(String data) {
        if (!data.equals("null"))
            tvAddress.setText(data);
        else
            tvAddress.setText("");
    }

    private void setEventCallBackActivity() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setEventClickNicknameLayout() {
        layoutUpdateNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(ProfileActivity.this, UpdateUsernameActivity.class));
            }
        });
    }

    private void setEventClickEmailLayout() {
        layoutUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(ProfileActivity.this, UpdateEmailActivity.class));
            }
        });
    }

    private void setEventClickPhoneNumberLayout() {
        layoutUpdatePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(ProfileActivity.this, UpdatePhoneNumberActivity.class));
            }
        });
    }

    private void setEventClickAddressLayout() {
        layoutUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(ProfileActivity.this, UpdateAddressActivity.class));
            }
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                    }
                    getDateExits();
                }
            });
}