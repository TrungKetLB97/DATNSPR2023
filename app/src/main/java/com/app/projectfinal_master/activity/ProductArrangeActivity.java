package com.app.projectfinal_master.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.ProductAdapter;
import com.app.projectfinal_master.adapter.ProductAdapter2;
import com.app.projectfinal_master.utils.ItemClickListener;

import java.util.List;

public class ProductArrangeActivity extends AppCompatActivity implements ItemClickListener {

    private ProductAdapter2 productAdapter2;
    private RecyclerView rcvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_arrange);
        initView();
    }

    private void initView() {
        rcvProduct = findViewById(R.id.rcv_product);
    }

    private void setViewRcv() {
        rcvProduct.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onClick(View view, List<Object> list, int position, boolean isLongClick) {

    }
}