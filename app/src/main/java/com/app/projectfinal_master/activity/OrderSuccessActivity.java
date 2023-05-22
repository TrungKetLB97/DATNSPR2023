package com.app.projectfinal_master.activity;

import static com.app.projectfinal_master.utils.Constant.INSERT_NOTIFICATION;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Address;
import com.app.projectfinal_master.model.Notification;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrderSuccessActivity extends AppCompatActivity {

    private Button btnReturnShopping, btnReceiptView;
    private ProgressBar progressBar;

    private String title = "Đặt hàng thành công";
    private String content = "Cảm ơn bạn đã đặt hàng tại shop!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        initView();
        createNotificationManager();
        setOnClickReceiptViewButton();
        setOnClickReturnShoppingButton();
        callApiNotification(this);

    }

    private void createNotificationManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification() {
        final String CHANNEL_ID = "channel1";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_filled)
                .setContentTitle(title)
                .setContentText(content);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(1, builder.build());
    }

    private void initView() {
        btnReceiptView = findViewById(R.id.btn_receipt_view);
        btnReturnShopping = findViewById(R.id.btn_return_shopping);
        progressBar = findViewById(R.id.progress);
    }

    private void setOnClickReceiptViewButton() {
        btnReceiptView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderSuccessActivity.this, ReceiptActivity.class));
                finish();
            }
        });
    }

    private void setOnClickReturnShoppingButton() {
        btnReturnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderSuccessActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private void callApiNotification(Context context) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, INSERT_NOTIFICATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("receiver");
                    int success = object.getInt("success");

                    if (success == 1) {
                        createNotification();
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
//                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", DataLocalManager.getUser().getIdUser());
                params.put("title", title);
                params.put("content", content);
                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(mStringRequest);
    }
}