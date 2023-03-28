package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.UPDATE_NAME_USER;

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

public class NameUserActivity extends AppCompatActivity {
    private StringRequest mStringRequest;
    private Button btnAccept;
    private EditText edtNickname;
    private ImageView imgBack, imgDelete;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_user);
        initView();
        getDataExist();
        addTextChangedListenerEditTextName();
        setViewButton();
        setEventClickBackImage();
    }

    private void initView() {
        user = DataLocalManager.getUser();
        btnAccept = findViewById(R.id.btn_accept);
        edtNickname = findViewById(R.id.edt_nickname);
        imgBack = findViewById(R.id.img_back);
        imgDelete = findViewById(R.id.img_delete);
    }

    private void getDataExist() {
        edtNickname.setText(user.getNameUser());
    }

    private void setViewButton() {
        String nickname = edtNickname.getText().toString().trim();
        if (!nickname.isEmpty() && !edtNickname.getText().toString().equals(user.getNameUser())) {
            btnAccept.setBackgroundResource(R.color.black);
            setOnClickButtonAccept();
            return;
        }
        btnAccept.setBackgroundResource(R.color.gray_dark);
        btnAccept.setClickable(false);

    }

    private void setImageViewEditText() {
        String nickname = edtNickname.getText().toString().trim();
        if (nickname.isEmpty()) {
            imgDelete.setVisibility(View.INVISIBLE);
        } else {
            imgDelete.setVisibility(View.VISIBLE);
        }
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
                if (!CheckStateNetwork.isNetworkAvailable(NameUserActivity.this)) return;
                user.setNameUser(edtNickname.getText().toString());
                DataLocalManager.setUser(user);
                updateNickname(NameUserActivity.this, user.getIdUser(), user.getNameUser());
                finish();
            }
        });
    }


    private void addTextChangedListenerEditTextName() {
        edtNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setImageViewEditText();
                setViewButton();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setImageViewEditText();
                setViewButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void updateNickname(Context context, String idUser, String data) {
        mStringRequest = new StringRequest(Request.Method.POST,
                UPDATE_NAME_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    if (success == 1) {
//                        mProgress.setVisibility(View.GONE);
                        User user = DataLocalManager.getUser();
                        user.setNameUser(data);
                        DataLocalManager.setUser(user);
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        finish();
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
                params.put("name_user", data);
                return params;
            }
        };
        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(this).getRequestQueue().add(mStringRequest);
    }
}