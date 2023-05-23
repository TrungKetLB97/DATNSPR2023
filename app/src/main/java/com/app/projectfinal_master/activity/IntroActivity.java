package com.app.projectfinal_master.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.data.DataLocalManager;

public class IntroActivity extends AppCompatActivity {
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initView();
        setEventClickBackImage();
    }

    private void initView() {
        imgBack = findViewById(R.id.img_back);
    }

    private void setEventClickBackImage() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}