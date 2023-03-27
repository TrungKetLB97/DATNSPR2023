package com.app.projectfinal_master.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.utils.ICallbackActivity;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvNickname, tvNameUser, tvIntroduce, tvCategoriesFavorite, tvSize;
    private ICallbackActivity iCallbackActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        getDateExits();
        setEventCallBackActivity();
        setEventClickNickname();
    }

    private void initView() {
        imgBack = findViewById(R.id.img_back);
        tvNickname = findViewById(R.id.tv_nickname);
        tvIntroduce = findViewById(R.id.tv_introduce);
        tvCategoriesFavorite = findViewById(R.id.tv_category_favorite);
        tvSize = findViewById(R.id.tv_size);
        tvNameUser = findViewById(R.id.tv_name_user);
    }

    private void getDateExits() {
        if (DataLocalManager.getUser().getNameUser() == null) {
            tvNameUser.setVisibility(View.GONE);
            return;
        }
        tvNameUser.setText(DataLocalManager.getUser().getNameUser());
        tvNameUser.setVisibility(View.VISIBLE);
    }

    private void setEventCallBackActivity() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setEventClickNickname() {
        tvNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, NameUserActivity.class));
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

    private void setEventClickCategoriesFavorite() {
        tvCategoriesFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setEventClickSize() {
        tvSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}