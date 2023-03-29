package com.app.projectfinal_master.fragment;

import static com.app.projectfinal_master.utils.Constant.LOGIN;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInFragment extends Fragment {

    private View view;
    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText edtEmail, edtPassword;
    private TextView tvForgotPassword;
    private Button btnLogin, btnLoginGoogle;

    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initView();
        eventClickLoginButton();
        return view;
    }

    private void initView()
    {
        tilEmail = view.findViewById(R.id.til_email);
        tilPassword = view.findViewById(R.id.til_password);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        tvForgotPassword = view.findViewById(R.id.tv_f_password);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLoginGoogle = view.findViewById(R.id.btn_login_google);
    }

    private void eventClickLoginButton()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(edtEmail.getText());
                String password = String.valueOf(edtPassword.getText());
                signIn(email, password);
            }
        });
    }

    private void signIn(final String email, final String password) {

        mRequestQueue = Volley.newRequestQueue(getContext());

        mStringRequest = new StringRequest(Request.Method.POST, LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject objData = new JSONObject(response);

                    JSONArray array = objData.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        String idUser = object.getString("id_user");
                        String email = object.getString("email");
                        String nameUser = object.getString("name_user");
                        String phoneNumber = object.getString("phone_number");
                        String avatar = object.getString("avatar");
                        String birth = object.getString("birth");
                        String sex = object.getString("sex");

                        User user = new User(idUser, email, nameUser, phoneNumber, avatar, birth, sex);
                        DataLocalManager.setUser(user);
                    }


                    int success = objData.getInt("success");
                    String message = objData.getString("message");

                    if (success == 1) {
                        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);

                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        mRequestQueue.add(mStringRequest);
    }
}