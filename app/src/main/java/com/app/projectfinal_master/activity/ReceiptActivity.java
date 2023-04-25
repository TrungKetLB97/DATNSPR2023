package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.RECEIPT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.ReceiptAdapter;
import com.app.projectfinal_master.adapter.ReceiptFragmentAdapter;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.ItemizedReceipt;
import com.app.projectfinal_master.model.Receipt;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.VolleySingleton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ProgressBar progressBar;
    private ImageView imgBack;

    private ReceiptFragmentAdapter receiptFragmentAdapter;

    private RecyclerView rcvReceipt;
    private ReceiptAdapter receiptAdapter;
    private RecyclerView.LayoutManager receiptLayout;

    public List<Receipt> receipts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        initView();
        setRcvReceipt();
        callApiGetReceipt(this);
        eventClickBackImg();
//        setFragmentManager();
//        setContentViewPager();
//        getEventSelectedTabLayout();
    }

    private void initView() {
        tabLayout = findViewById(R.id.tab_select);
        viewPager2 = findViewById(R.id.view_pager);
        progressBar = findViewById(R.id.progress);
        rcvReceipt = findViewById(R.id.rcv_receipt);
        imgBack = findViewById(R.id.img_back);
    }

    private void eventClickBackImg() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setRcvReceipt() {
        receiptAdapter = new ReceiptAdapter(Receipt.itemCallback, this, iCallbackActivity);
        receiptLayout = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        rcvReceipt.setLayoutManager(receiptLayout);
        rcvReceipt.setAdapter(receiptAdapter);
    }

    private void addItem(Receipt receipt) {
        List<Receipt> receipts = new ArrayList<>(receiptAdapter.getCurrentList());
        receipts.add(receipt);
//        if (product.getIdCategory() == category.getId() && product.getSex().equals(category.getSex())) {
        receiptAdapter.submitList(receipts);
    }

//    private void setFragmentManager() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        receiptFragmentAdapter = new ReceiptFragmentAdapter(fragmentManager, getLifecycle());
//    }
//
//    private void setContentViewPager() {
//        viewPager2.setAdapter(receiptFragmentAdapter);
//        viewPager2.setUserInputEnabled(false);
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                tabLayout.selectTab(tabLayout.getTabAt(position));
//            }
//        });
//    }

//    private void getEventSelectedTabLayout() {
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.all_receipt));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.receipt_processing));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.receipt_delivering));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.receipt_delivered));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.receipt_canceled));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.receipt_returns));
//
//        tabLayout.setScrollContainer(false);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager2.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }
//
//    private ICallbackActivity iCallbackActivity = new ICallbackActivity() {
//        @Override
//        public void callback(Object object) {
//
//        }
//    };

    public void callApiGetReceipt(Context context) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, RECEIPT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String codeReceipt = object.getString("code_receipt");
                        String idUser = object.getString("id_user");
                        String username = object.getString("username");
                        String address = object.getString("address");
                        String phoneNumber = object.getString("phone_number");
                        int quantityReceipt = object.getInt("quantity_receipt");
                        String sumPrice = object.getString("sum_price");
                        String receiptStatus = object.getString("receipt_status");
                        String payStatus = object.getString("pay_status");
                        String orderDate = object.getString("order_date");
                        String deliveringDate = object.getString("delivering_date");
                        String confirmationDate = object.getString("confirmation_date");
                        String codeProduct = object.getString("code_product");
                        int quantityItem = object.getInt("quantity_item");
                        String price = object.getString("price");
                        String name = object.getString("name");
                        String imageThumb = object.getString("image_thumb");
                        String color = object.getString("color");
                        String size = object.getString("size");
                        ItemizedReceipt itemizedReceipt = new ItemizedReceipt(codeProduct, quantityItem, price, name, imageThumb, color, size);
                        itemizedReceipt.setName(name);
                        Receipt receipt = new Receipt(codeReceipt, username, address, phoneNumber, quantityReceipt, sumPrice, receiptStatus, payStatus, orderDate, deliveringDate, confirmationDate, itemizedReceipt);
//                        receipts.add(receipt);
                        addItem(receipt);
                    }

//                    receiptFragmentAdapter.setDataReceipt(receipts);
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
//                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", DataLocalManager.getUser().getIdUser());
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
    }

    private ICallbackActivity iCallbackActivity = new ICallbackActivity() {
        @Override
        public void callback(Object object) {
            Receipt receipt = (Receipt) object;
            Intent intent = new Intent(ReceiptActivity.this, ItemizedReceiptActivity.class);
            intent.putExtra("receipt", receipt);
            someActivityResultLauncher.launch(intent);
        }
    };

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