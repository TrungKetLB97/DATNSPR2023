package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.ITEMIZED_RECEIPT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.ItemizedReceiptAdapter;
import com.app.projectfinal_master.model.ItemizedReceipt;
import com.app.projectfinal_master.model.Receipt;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemizedReceiptActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvUsername, tvPhoneNumber, tvAddress, tvSumReceipt, tvTransportFee, tvSumPrice, tvDiscount;
    private TextView tvCodeReceipt, tvOrderDate, tvPayMethod, tvDeliveringDate, tvConfirmationDate;
    private ProgressBar progressBar;

    private List<ItemizedReceipt> itemizedReceiptList = new ArrayList<>();

    private RecyclerView rcvItemizedReceipt;
    private RecyclerView.Adapter itemizedReceiptAdapter;
    private RecyclerView.LayoutManager itemizedReceiptLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemized_receipt);

        initView();
        callApiGetItemizedReceipt(this);
        setContentView();
        eventClickBackImg();
    }

    private void initView() {
        tvUsername = findViewById(R.id.tv_username);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        tvSumReceipt = findViewById(R.id.tv_sum_receipt);
        tvTransportFee = findViewById(R.id.tv_transport_fee);
        tvSumPrice = findViewById(R.id.tv_sum_price);
        tvDiscount = findViewById(R.id.tv_discount);
        tvAddress = findViewById(R.id.tv_address);
        tvCodeReceipt = findViewById(R.id.tv_code_receipt);
        tvPayMethod = findViewById(R.id.tv_pay_method);
        tvOrderDate = findViewById(R.id.tv_order_date);
        tvDeliveringDate = findViewById(R.id.tv_delivering_date);
        tvConfirmationDate = findViewById(R.id.tv_confirmation_date);
        progressBar = findViewById(R.id.progress);
        rcvItemizedReceipt = findViewById(R.id.rcv_itemized_receipt);
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

    private Receipt getReceipt() {
        Intent intent = getIntent();
        Receipt receipt = (Receipt) intent.getSerializableExtra("receipt");
        return receipt;
    }

    private void setContentView() {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvUsername.setText(getReceipt().getUsername());
        tvPhoneNumber.setText(getReceipt().getPhoneNumber());
        tvAddress.setText(getReceipt().getAddress());
        int sumReceipt = Integer.valueOf(getReceipt().getSumPrice()) - 20000;
        tvSumReceipt.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(sumReceipt)))));
        tvTransportFee.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(20000)))));
        tvDiscount.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(0)))));
        tvSumPrice.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(getReceipt().getSumPrice())))));
        tvCodeReceipt.setText(getReceipt().getCodeReceipt());
        tvPayMethod.setText("Thanh toán trực tiếp");
        if (getReceipt().getOrderDate().equals("null"))
            tvOrderDate.setText("Đang xử lý");
        else
            tvOrderDate.setText(getReceipt().getOrderDate());
        if (getReceipt().getDeliveringDate().equals("null"))
            tvDeliveringDate.setText("Đang xử lý");
        else
            tvDeliveringDate.setText(getReceipt().getDeliveringDate());
        if (getReceipt().getConfirmationDate().equals("null"))
            tvConfirmationDate.setText("Đang xử lý");
        else
            tvConfirmationDate.setText(getReceipt().getConfirmationDate());
    }

    private ICallbackActivity iCallbackActivity = new ICallbackActivity() {
        @Override
        public void callback(Object object) {
            finish();
        }
    };

    private void setRcvReceipt() {
        itemizedReceiptAdapter = new ItemizedReceiptAdapter(this, itemizedReceiptList, iCallbackActivity);
        itemizedReceiptLayout = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        rcvItemizedReceipt.setLayoutManager(itemizedReceiptLayout);
        rcvItemizedReceipt.setAdapter(itemizedReceiptAdapter);
    }

    public void callApiGetItemizedReceipt(Context context) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, ITEMIZED_RECEIPT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String codeProduct = object.getString("code_product");
                        String name = object.getString("name");
                        String color = object.getString("color");
                        String size = object.getString("size");
                        String imageThumb = object.getString("image_thumb");
                        String price = object.getString("price");
                        int quantity = object.getInt("quantity");

                        ItemizedReceipt itemizedReceipt = new ItemizedReceipt(codeProduct, quantity, price, name, imageThumb, color, size);
                        itemizedReceipt.setName(name);
//                        receipts.add(receipt);
                        itemizedReceiptList.add(itemizedReceipt);
                    }
                    setRcvReceipt();

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
                params.put("code_receipt", getReceipt().getCodeReceipt());
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
    }
}