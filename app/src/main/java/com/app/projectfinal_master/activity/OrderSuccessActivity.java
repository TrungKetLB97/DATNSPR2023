package com.app.projectfinal_master.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.app.projectfinal_master.R;

public class OrderSuccessActivity extends AppCompatActivity {

    private Button btnReturnShopping, btnReceiptView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        initView();

        setOnClickReceiptViewButton();
        setOnClickReturnShoppingButton();
    }

    private void initView() {
        btnReceiptView = findViewById(R.id.btn_receipt_view);
        btnReturnShopping = findViewById(R.id.btn_return_shopping);
    }

    private void setOnClickReceiptViewButton() {
        btnReceiptView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                finish();
            }
        });
    }

    private void setOnClickReturnShoppingButton() {
        btnReturnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderSuccessActivity.this, HomeActivity.class));
            }
        });
    }
}