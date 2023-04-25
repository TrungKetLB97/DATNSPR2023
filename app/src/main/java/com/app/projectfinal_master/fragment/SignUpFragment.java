package com.app.projectfinal_master.fragment;

import static com.app.projectfinal_master.utils.Constant.REGISTER;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.utils.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    private View view;
    private TextInputLayout tilEmail, tilPassword, tilConfirmPassword;
    private TextInputEditText edtEmail, edtPassword, edtConfirmPassword;
    private Button btn_register, btnLoginGoogle;
    private ProgressBar progressBar;
    private StringRequest mStringRequest;

    public Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initView();
        context = getContext();
        eventClickRegisterButton();
        return view;
    }

    private void initView()
    {
        tilEmail = view.findViewById(R.id.til_email);
        tilPassword = view.findViewById(R.id.til_password);
        tilConfirmPassword = view.findViewById(R.id.til_r_password);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        edtConfirmPassword = view.findViewById(R.id.edt_r_password);
        btn_register = view.findViewById(R.id.btn_register);
        btnLoginGoogle = view.findViewById(R.id.btn_login_google);
        progressBar = view.findViewById(R.id.progress);
    }

    private void eventClickRegisterButton()
    {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(edtEmail.getText());
                String password = String.valueOf(edtPassword.getText());
                String cPassword = String.valueOf(edtConfirmPassword.getText());
                signUp(email, password, cPassword, "0");
            }
        });
    }

    public void signUp(final String email, final String password, final String rPassword, final String request){
        progressBar.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.POST, REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    if (success == 0) {
                        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context,"Lỗi kết nối. Vui lòng thử lại!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("password1",password);
                params.put("password2",rPassword);
                params.put("request",request);
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(getContext()).getRequestQueue().add(mStringRequest);
    }
}