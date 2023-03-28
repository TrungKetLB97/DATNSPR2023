package com.app.projectfinal_master.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.PayAdapter;
import com.app.projectfinal_master.adapter.ProductAdapter;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Cart;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ItemClickListener;

import java.util.List;

public class PayActivity extends AppCompatActivity {

    private List<Cart> carts;
    private Cart cart;
    private Product product;

    private RecyclerView rcvPayCart;
    private RecyclerView.Adapter payAdapter;
    private RecyclerView.LayoutManager payLayout;

    private ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        getItem();
    }

    private void initView() {
        rcvPayCart = findViewById(R.id.rcv_pay_cart);
    }

    private void getItem() {
        carts = DataLocalManager.getCarts();
        payAdapter = new PayAdapter(PayActivity.this, carts, itemClickListener);
        payLayout = new GridLayoutManager(PayActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
        rcvPayCart.setLayoutManager(payLayout);
        rcvPayCart.setAdapter(payAdapter);

    }

}