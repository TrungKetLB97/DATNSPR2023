package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.ADDRESS;
import static com.app.projectfinal_master.utils.Constant.INSERT_ADDRESS;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.AddressAdapter;
import com.app.projectfinal_master.adapter.DeliveryAddressAdapter;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Address;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeliveryAddressActivity extends AppCompatActivity {

    private DeliveryAddressAdapter deliveryAddressAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rcvDeliveryAddress;
    private ProgressBar progressBar;

    private List<Address> addressList;
    private Address address;

    private AppCompatButton btnAdd;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        initView();
        eventClickBackImg();
        setViewRcv();
        setOnClickAddButton();
        callApiGetDeliveryAddress(DeliveryAddressActivity.this);
    }

    private void initView() {
        rcvDeliveryAddress = findViewById(R.id.rcv_delivery_address);
        btnAdd = findViewById(R.id.btn_add);
        imgBack = findViewById(R.id.img_back);
        progressBar = findViewById(R.id.progress);
    }

    private void eventClickBackImg() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setOnClickAddButton() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(DeliveryAddressActivity.this, AddDeliveryAddressActivity.class));
//                finish();
            }
        });
    }

    private void setViewRcv() {
        rcvDeliveryAddress.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        deliveryAddressAdapter = new DeliveryAddressAdapter(Address.itemCallback, this, iCallbackActivity);
        rcvDeliveryAddress.setLayoutManager(layoutManager);
        rcvDeliveryAddress.setAdapter(deliveryAddressAdapter);
    }

    private void addItem(Address address) {
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        deliveryAddressAdapter.submitList(addressList);
    }

    private final ICallbackActivity iCallbackActivity = new ICallbackActivity() {
        @Override
        public void callback(Object object) {
            Intent intent = new Intent();
            intent.putExtra("address", (Address) object);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    private void callApiGetDeliveryAddress(Context context) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
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
                        Address address = new Address(idAddress, receiver, city, district, commune, street, phone_number, set_default);
                        addItem(address);
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

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    callApiGetDeliveryAddress(DeliveryAddressActivity.this);
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        String username = data.getStringExtra("result");
                    }
                }
            });
}