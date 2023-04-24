package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.UPDATE_EMAIL;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.User;
import com.app.projectfinal_master.utils.CheckStateNetwork;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateEmailActivity extends AppCompatActivity {
    private StringRequest mStringRequest;
    private Button btnAccept;
    private EditText edtEmail;
    private ImageView imgBack;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        initView();
        getDataExist();
        addTextChangedListenerEditTextName();
        setEventClickBackImage();
    }

    private void initView() {
        user = DataLocalManager.getUser();
        btnAccept = findViewById(R.id.btn_accept);
        edtEmail = findViewById(R.id.edt_email);
        imgBack = findViewById(R.id.img_back);
    }

    private void getDataExist() {
        edtEmail.setText(user.getEmail());
    }

    private void setViewButton() {
        String nickname = edtEmail.getText().toString().trim();
        if (!nickname.isEmpty() && !edtEmail.getText().toString().equals(user.getEmail())) {
            btnAccept.setBackgroundTintList(this.getColorStateList(R.color.black));
            setOnClickButtonAccept();
            return;
        }
        btnAccept.setBackgroundTintList(this.getColorStateList(R.color.gray));
        btnAccept.setClickable(false);

    }

    private void setEventClickBackImage() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setOnClickButtonAccept() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckStateNetwork.isNetworkAvailable(UpdateEmailActivity.this)) return;
                String username = edtEmail.getText().toString();
                updateUsername(UpdateEmailActivity.this, user.getIdUser(), username);
//                finish();
            }
        });
    }

    private void ValidatePhoneNumber() {

    }

    private void addTextChangedListenerEditTextName() {
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setViewButton();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setViewButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void updateUsername(Context context, String idUser, String username) {
        mStringRequest = new StringRequest(Request.Method.POST,
                UPDATE_EMAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    if (success == 1) {
//                        mProgress.setVisibility(View.GONE);
                        user = DataLocalManager.getUser();
                        user.setUsername(username);
                        DataLocalManager.setUser(user);
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                        finish();
                    }
                    if (success == 0) {
//                        mProgress.setVisibility(View.GONE);
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    mProgress.setVisibility(View.GONE);
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mProgress.setVisibility(View.GONE);
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", idUser);
                params.put("username", username);
                return params;
            }
        };
        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(this).getRequestQueue().add(mStringRequest);
    }
}