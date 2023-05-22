package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.ADDRESS;
import static com.app.projectfinal_master.utils.Constant.CREATE_RECEIPT;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.PayAdapter;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Address;
import com.app.projectfinal_master.model.Cart;
import com.app.projectfinal_master.model.CreateOrder;
import com.app.projectfinal_master.model.ItemizedReceipt;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ItemClickListener;
import com.app.projectfinal_master.utils.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PayActivity extends AppCompatActivity {

    private List<Cart> carts;
    private List<Address> addressList = new ArrayList<>();
    private Cart cart;
    private Product product;
    private Address address;

    private RecyclerView rcvPayCart;
    private RecyclerView.Adapter payAdapter;
    private RecyclerView.LayoutManager payLayout;

    private ItemClickListener itemClickListener;

    private ProgressBar progressBar;

    private LinearLayoutCompat layoutDirectPayment, layoutZaloPayment;
    private CheckBox cbDirectPayment, cbZaloPayment;

    private RelativeLayout layoutContentAddress;
    private RelativeLayout layoutAddress;
    private ImageView imgBack;
    private TextView tvNoneAddress, tvProductCount, tvSumPriceTmp, tvRetailPrice,
            tvProvisional, tvTransportFee, tvGuaranteedShipping, tvDiscount, tvSumPrice;
    private TextView tvAddress, tvUsername, tvPhoneNumber, tvStreet;

    private Button btnOrder;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        getItem();
        eventClickBackImg();
        callApiGetDeliveryAddress(this);
        eventClickContentAddressLayout();
        setOnClickOrderButton();

        changeStatePaymentMethod();

        //register zalo pay thread
        registerZaloThread();
    }

    private void initView() {
        rcvPayCart = findViewById(R.id.rcv_pay_cart);
        layoutContentAddress = findViewById(R.id.layout_content_address);
        layoutAddress = findViewById(R.id.layout_address);
        layoutDirectPayment = findViewById(R.id.layout_direct_payment);
        layoutZaloPayment = findViewById(R.id.layout_momo_payment);
        progressBar = findViewById(R.id.progress);
        imgBack = findViewById(R.id.img_back);
        tvNoneAddress = findViewById(R.id.tv_none_address);
        tvProductCount = findViewById(R.id.tv_product_count);
        tvSumPriceTmp = findViewById(R.id.tv_sum_price_tmp);
        tvRetailPrice = findViewById(R.id.tv_retail_price);
        tvProvisional = findViewById(R.id.tv_provisional);
        tvTransportFee = findViewById(R.id.tv_transport_fee);
        tvGuaranteedShipping = findViewById(R.id.tv_guaranteed_shipping);
        tvDiscount = findViewById(R.id.tv_discount);
        tvSumPrice = findViewById(R.id.tv_sum_price);
        tvAddress = findViewById(R.id.tv_update_address);
        tvUsername = findViewById(R.id.tv_username);
        tvPhoneNumber = findViewById(R.id.tv_update_phone_number);
        tvStreet = findViewById(R.id.tv_street);
        btnOrder = findViewById(R.id.btn_order);
        cbDirectPayment = findViewById(R.id.cb_direct_payment);
        cbZaloPayment = findViewById(R.id.cb_zalo_payment);
    }

    private void registerZaloThread() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);
    }

    private void changeStatePaymentMethod() {
        if (DataLocalManager.getPayMethodDefault().equals("")) {
            cbZaloPayment.setChecked(false);
            cbDirectPayment.setChecked(true);
        }
        else {
            cbZaloPayment.setChecked(true);
            cbDirectPayment.setChecked(false);
        }

        cbDirectPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    DataLocalManager.removePayMethodDefault();
                    cbZaloPayment.setChecked(false);
                    cbZaloPayment.setEnabled(true);
                }
                else
                {
                    cbZaloPayment.setChecked(true);
                    cbZaloPayment.setEnabled(false);
                }
            }
        });
        cbZaloPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    DataLocalManager.setPayMethodDefault("zalo");
                    cbDirectPayment.setChecked(false);
                    cbDirectPayment.setEnabled(true);
                }
                else
                {
                    cbDirectPayment.setChecked(true);
                    cbDirectPayment.setEnabled(false);
                }
            }
        });
    }

    private void eventClickBackImg() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setDeliveryAddress(Address address) {
        tvAddress.setText(address.toString());
        tvUsername.setText(address.getReceiver());
        tvPhoneNumber.setText(address.getPhoneNumber());
        tvStreet.setText(address.getStreet());
    }

    private void setOnClickOrderButton() {
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbDirectPayment.isChecked()) {
                    if (address == null) {
                        Toast.makeText(PayActivity.this, "Xin vui lòng chọn địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        String title = "Xác nhận!";
                        String message = "Xác nhận đặt hàng.";
                        openDialog(PayActivity.this, title, message);
                    }
                } else {
                    createZaloMethod();
                }
            }
        });
    }

    private void createZaloMethod() {
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(String.valueOf(sumPrice));
            Log.d("Amount", String.valueOf(sumPrice));
            String code = data.getString("return_code");
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(PayActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                new AlertDialog.Builder(PayActivity.this)
//                                        .setTitle("Payment Success")
//                                        .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
//                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                            }
//                                        })
//                                        .setNegativeButton("Cancel", null).show();
//                            }
//
//                        });
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
//                        new AlertDialog.Builder(PayActivity.this)
//                                .setTitle("User Cancel Payment")
//                                .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                                .setNegativeButton("Cancel", null).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
//                        new AlertDialog.Builder(PayActivity.this)
//                                .setTitle("Payment Fail")
//                                .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                                .setNegativeButton("Cancel", null).show();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDialog(Context context, String title, String message) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_accept);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Xac dinh vi tri cua dialog
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        //Click ngoai dialog
        dialog.setCancelable(false);

        //init view dialog

        TextView tvTitleDialog = dialog.findViewById(R.id.tv_title_dialog);
        TextView tvMessageDialog = dialog.findViewById(R.id.tv_message_dialog);

        //set content dialog
        tvTitleDialog.setText(title);
        tvMessageDialog.setText(message);

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnAccept = dialog.findViewById(R.id.btn_accept);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call action method
                callApiCreateReceipt(context);
                DataLocalManager.removeDataCarts();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void eventClickContentAddressLayout() {
        layoutContentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(PayActivity.this, DeliveryAddressActivity.class));
            }
        });
    }

    private void getItem() {
        Intent intent = getIntent();
        carts = (List<Cart>) intent.getSerializableExtra("carts");
        int productQuantity = 0;
        int sumPrice = 0;
        for (Cart cart :
                carts) {
            productQuantity += cart.getQuantity();
            sumPrice += Integer.parseInt(cart.getProduct().getSellingPrice()) * cart.getQuantity();
        }
        int discount = 0;

        setDetailPrice(productQuantity, sumPrice, discount);

        payAdapter = new PayAdapter(PayActivity.this, carts, itemClickListener);
        payLayout = new GridLayoutManager(PayActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
        rcvPayCart.setLayoutManager(payLayout);
        rcvPayCart.setAdapter(payAdapter);
    }

    int sumPrice;

    private void setDetailPrice(int productQuantity, int price, int discount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvProductCount.setText(String.format("%s sản phẩm", productQuantity));
        int feeShip = 20000;
        int guaranteedShip = 0;
        sumPrice = price + feeShip + guaranteedShip - discount;
        tvSumPriceTmp.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(price)))));
        tvRetailPrice.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(price)))));
        tvProvisional.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(price)))));
        tvTransportFee.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(feeShip)))));
        tvGuaranteedShipping.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(guaranteedShip)))));
        tvDiscount.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(discount)))));
        tvSumPrice.setText(String.format("%s đ", formatter.format(Double.parseDouble(String.valueOf(sumPrice)))));
    }

    private void callApiGetDeliveryAddress(Context context) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    if (array.length() == 0) {
                        layoutAddress.setVisibility(View.GONE);
                        tvNoneAddress.setVisibility(View.VISIBLE);
                    } else {
                        layoutAddress.setVisibility(View.VISIBLE);
                        tvNoneAddress.setVisibility(View.GONE);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            int idAddress = object.getInt("id_address");
                            String receiver = object.getString("receiver");
                            String city = object.getString("city");
                            String district = object.getString("district");
                            String commune = object.getString("commune");
                            String street = object.getString("street");
                            String phone_number = object.getString("phone_number");
                            int set_default = object.getInt("set_default");
                            if (set_default == 0) {
                                address = new Address(idAddress, receiver, city, district, commune, street, phone_number, set_default);
                                setDeliveryAddress(address);
                                return;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
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

    private void callApiCreateReceipt(Context context) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, CREATE_RECEIPT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    int success = object.getInt("success");
                    if (success == 1) {
                        startActivity(new Intent(PayActivity.this, OrderSuccessActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, "Đặt hàng thất bại. Xin thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params = createReceipt(address.toAddress(), address.getPhoneNumber(), carts.size(), sumPrice);
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
    }

    private Map<String, String> createReceipt(String address, String phoneNumber, int quantity, int sumPrice) {
        Map<String, String> params = new HashMap<>();
        params.put("id_user", DataLocalManager.getUser().getIdUser());
        params.put("username", DataLocalManager.getUser().getUsername());
        params.put("address", address);
        params.put("phone_number", phoneNumber);
        params.put("quantity", String.valueOf(quantity));
        params.put("sum_price", String.valueOf(sumPrice));
        if (cbDirectPayment.isChecked())
            params.put("pay_status", "Chưa thanh toán");
        params.put("itemized_receipt", createItemizedReceipt());
        return params;
    }

    private String createItemizedReceipt() {
        List<ItemizedReceipt> itemizedReceipts = new ArrayList<>();
        for (Cart cart :
                carts) {
            ItemizedReceipt itemizedReceipt = new ItemizedReceipt(cart.getProduct().getCodeProduct(), cart.getProduct().getIdColor(), cart.getProduct().getIdSize(), cart.getQuantity(), cart.getProduct().getSellingPrice());
            itemizedReceipts.add(itemizedReceipt);
        }
        return new Gson().toJson(itemizedReceipts);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        address = (Address) data.getSerializableExtra("address");
                        setDeliveryAddress(address);
                        layoutAddress.setVisibility(View.VISIBLE);
                        tvNoneAddress.setVisibility(View.GONE);
                    }
                }
            });

}