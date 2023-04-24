package com.app.projectfinal_master.fragment;

import static com.app.projectfinal_master.utils.Constant.LOGIN;
import static com.app.projectfinal_master.utils.Constant.REGISTER;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.activity.CartActivity;
import com.app.projectfinal_master.activity.UpdateUsernameActivity;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.User;
import com.app.projectfinal_master.utils.CheckStateNetwork;
import com.app.projectfinal_master.utils.ICallbackActivity;
import com.app.projectfinal_master.utils.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class SignInFragment extends Fragment {

    private View view;
    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText edtEmail, edtPassword;
    private TextView tvForgotPassword;
    private Button btnLogin;
    private ProgressBar progressBar;

    private StringRequest mStringRequest;

    private ICallbackActivity iCallbackActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        initView();
        eventClickLoginButton();
        return view;
    }

    public void setCallbackActivity(ICallbackActivity iCallbackActivity) {
        this.iCallbackActivity = iCallbackActivity;
    }

    private void initView() {
        tilEmail = view.findViewById(R.id.til_email);
        tilPassword = view.findViewById(R.id.til_password);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        tvForgotPassword = view.findViewById(R.id.tv_f_password);
        btnLogin = view.findViewById(R.id.btn_login);
        progressBar = view.findViewById(R.id.progress);
    }

    private void eventClickLoginButton() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckStateNetwork.isNetworkAvailable(getContext())) return;
                String email = String.valueOf(edtEmail.getText());
                String password = String.valueOf(edtPassword.getText());
                signIn(email, password, randomUsername(), 0);
            }
        });
    }

    private String randomUsername() {
        StringBuilder username = new StringBuilder("User");
        String randomNumber;
        final Random myRandom = new Random();
        for (int i = 0; i < 6; i++) {
            randomNumber = String.valueOf(myRandom.nextInt(9));
            username.append(randomNumber);
        }
        return username.toString();
    }

    public void signIn(final String email, final String password, final String username, final int request) {
        progressBar.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.POST, LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject objData = new JSONObject(response);
                    int success = objData.getInt("success");
                    String message = objData.getString("message");
                    if (success == 0) {
                        progressBar.setVisibility(View.GONE);
                        signUpGoogleAccount(email, username, 1);
                    } else if (success == 2) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        JSONArray array = objData.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);

                            String idUser = object.getString("id_user");
                            String email = object.getString("email");
                            String password = object.getString("password");
                            String username = object.getString("username");
                            String phoneNumber = object.getString("phone_number");
                            String address = object.getString("address");
                            String birth = object.getString("birth");
                            String sex = object.getString("sex");

                            User user = new User(idUser, email, password, username, phoneNumber, address, birth, sex);
                            DataLocalManager.setUser(user);
                            iCallbackActivity.callback(null);
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
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("request", String.valueOf(request));
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(getContext()).getRequestQueue().add(mStringRequest);
    }

    private void signUpGoogleAccount(final String email, final String username, final int request){
        progressBar.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.POST, REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    progressBar.setVisibility(View.GONE);
                    if (success == 1) {
                        signIn(email, "", username, 1);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("username",username);
                params.put("request", String.valueOf(request));
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(getContext()).getRequestQueue().add(mStringRequest);
    }

}