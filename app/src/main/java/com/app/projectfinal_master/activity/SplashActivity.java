package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.LOGIN;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.fragment.SignInFragment;
import com.app.projectfinal_master.model.User;
import com.app.projectfinal_master.utils.CheckStateNetwork;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private StringRequest mStringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadData();
    }

    private void loadData() {
        if (CheckStateNetwork.isNetworkAvailable(this)) {
            //Network connected
            if (DataLocalManager.getUser() != null){
                User user = DataLocalManager.getUser();
                signIn(user.getEmail(), user.getPassword());
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
            }, 3000);
        } else {
            //Network disconnected
            Toast.makeText(this, "Network disconnected", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn(final String email, final String password) {
        Log.e("TAG", "onResponse: success: " + password );
        mStringRequest = new StringRequest(Request.Method.POST, LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objData = new JSONObject(response);
                    Log.e("TAG", "onResponse: success" + objData.getString("message") );
                    JSONArray array = objData.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        String idUser = object.getString("id_user");
                        String email = object.getString("email");
                        String password = object.getString("password");
                        String nameUser = object.getString("name_user");
                        String phoneNumber = object.getString("phone_number");
                        String avatar = object.getString("avatar");
                        String birth = object.getString("birth");
                        String sex = object.getString("sex");

                        User user = new User(idUser, email, password, nameUser, phoneNumber, avatar, birth, sex);
                        DataLocalManager.setUser(user);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(SplashActivity.this).getRequestQueue().add(mStringRequest);
    }
}