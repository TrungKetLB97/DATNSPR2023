package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.INSERT_ADDRESS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Address;
import com.app.projectfinal_master.utils.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AddDeliveryAddressActivity extends AppCompatActivity {

    private AppCompatButton btnAccept;
    private ImageView imgBack;

    private TextInputLayout tilUsername, tilPhoneNumber, tilStreet;
    private TextInputEditText edtUsername, edtPhoneNumber, edtStreet;
    private TextView tvAddress;

    private SwitchCompat switchDefault;
    private ProgressBar progressBar;

    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_address);
        initView();
        eventClickBackImg();
        setOnClickAcceptButton();
        eventClickViewAddress();
    }

    private void initView() {
        btnAccept = findViewById(R.id.btn_accept);
        imgBack = findViewById(R.id.img_back);
        tilUsername = findViewById(R.id.til_username);
        tilPhoneNumber = findViewById(R.id.til_phone_number);
        tilStreet = findViewById(R.id.til_street);
        edtUsername = findViewById(R.id.edt_username);
        edtPhoneNumber = findViewById(R.id.edt_phone_number);
        edtStreet = findViewById(R.id.edt_street);
        tvAddress = findViewById(R.id.tv_update_address);
        switchDefault = findViewById(R.id.switch_default);
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

    private void eventClickViewAddress() {
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(AddDeliveryAddressActivity.this, AddressActivity.class));
            }
        });
    }

    private void setOnClickAcceptButton() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tilStreet.setError("Vui lòng nhập địa chỉ");
                if (validateUserName() && validatePhoneNumber() && validateStreet())
                    callAddDeliveryAddress(AddDeliveryAddressActivity.this);
            }
        });
    }

    private boolean validateUserName() {
        if (edtUsername.getText().length() == 0) {
            tilUsername.setError("Vui lòng nhập tên người nhận.");
            return false;
        } else if (edtUsername.getText().length() < 3) {
            tilUsername.setError("Họ tên quá ngắn. Họ tên phải ít nhất 3 ký tự hoặc hơn.");
            return false;
        } else {
            tilUsername.setError("");
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String validatePhoneNumber = "/(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\\b/";
        if (edtPhoneNumber.getText().length() == 0) {
            tilPhoneNumber.setError("Vui lòng nhập số điện thoại");
            return false;
        } else if (edtPhoneNumber.getText().toString().matches(validatePhoneNumber)) {
            tilPhoneNumber.setError("Số điện thoại không hợp lệ");
            return false;
        } else {
            tilPhoneNumber.setError("");
            return true;
        }
    }

    private boolean validateStreet() {
        if (edtStreet.getText().length() == 0) {
            tilStreet.setError("Vui lòng nhập địa chỉ");
            return false;
        } else {
            tilStreet.setError("");
            return true;
        }
    }

    private void callAddDeliveryAddress(Context context) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, INSERT_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject object = new JSONObject(response);
                    finish();
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
                params.put("receiver", edtUsername.getText().toString());
                params.put("city", address.getCity());
                params.put("district", address.getDistrict());
                params.put("commune", address.getCommune());
                params.put("street", edtStreet.getText().toString());
                params.put("phone_number", edtPhoneNumber.getText().toString());
                if (switchDefault.isChecked())
                    params.put("set_default", "0");
                else
                    params.put("set_default", "1");
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
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        address = (Address) data.getSerializableExtra("result");
                        tvAddress.setText(address.toString());
                    }
                }
            });
}