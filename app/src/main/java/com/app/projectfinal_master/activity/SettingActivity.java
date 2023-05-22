package com.app.projectfinal_master.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.ClearCacheRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.utils.ICallbackActivity;


public class SettingActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvProfile, tvAddress, tvAccountManager, tvFeedback, tvFollow, tvIntroduce;
    private Button btnSignOut;
    private ICallbackActivity iCallbackActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        getExistData();
        setEventClickProfile();
        eventClickBackImg();
        setOnClickButtonSignOut();
    }

    private void initView() {
        imgBack = findViewById(R.id.img_back);
        tvProfile = findViewById(R.id.tv_profile);
        tvAddress = findViewById(R.id.tv_update_address);
//        tvAccountManager = findViewById(R.id.tv_account_manager);
        tvFeedback = findViewById(R.id.tv_feedback);
        tvFollow = findViewById(R.id.tv_follow);
        tvIntroduce = findViewById(R.id.tv_introduce);
        btnSignOut = findViewById(R.id.btn_sign_out);
    }

    private void eventClickBackImg() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getExistData() {
        if (DataLocalManager.getUser() != null) {
            tvProfile.setText(DataLocalManager.getUser().getUsername());
        }
    }

    private void setEventClickProfile() {
        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ProfileActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });
    }

    private void setEventClickAddress() {
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setEventClickAccountManager() {
        tvAccountManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setEventClickFeedback() {
        tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setEventClickFollow() {
        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setEventClickIntroduce() {
        tvIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setOnClickButtonSignOut() {
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataLocalManager.removeDataUser();
                Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
                }
            });
}