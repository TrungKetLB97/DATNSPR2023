
package com.app.projectfinal_master.fragment;

import static com.app.projectfinal_master.utils.Constant.GET_NOTIFICATION;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.projectfinal_master.R;
import com.app.projectfinal_master.adapter.NotificationAdapter;
import com.app.projectfinal_master.adapter.ProductAdapter2;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.model.Category;
import com.app.projectfinal_master.model.Notification;
import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationFragment extends Fragment {
    private View view;
    private ProgressBar progressBar;
    private RecyclerView rcvNotification;
    private List<Notification> notifications;
    private NotificationAdapter notificationAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private LinearLayoutCompat layoutEmptyNotification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null)
            view = inflater.inflate(R.layout.fragment_notification, container, false);

        initView();
        setViewRcv();
        callApiGetNotification();
        return view;
    }

    private void initView() {
        rcvNotification = view.findViewById(R.id.rcv_notification);
        progressBar = view.findViewById(R.id.progress);
        layoutEmptyNotification = view.findViewById(R.id.layout_empty_notification);
    }

    private void setViewRcv() {
        rcvNotification.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        layoutManager = new GridLayoutManager(getContext(), 1);
        notificationAdapter = new NotificationAdapter(Notification.itemCallback, getContext(), null);
        rcvNotification.setLayoutManager(layoutManager);
        rcvNotification.setAdapter(notificationAdapter);
    }

    private void addItem(Notification product) {
        List<Notification> notifications = new ArrayList<>(notificationAdapter.getCurrentList());
//        if (product.getIdCategory() == category.getId() && product.getSex().equals(category.getSex())) {
        notifications.add(product);
        notificationAdapter.submitList(notifications);
//        }
    }

    private void callApiGetNotification() {
        progressBar.setVisibility(View.VISIBLE);
        notifications = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_NOTIFICATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        layoutEmptyNotification.setVisibility(View.GONE);
                        JSONObject object = array.getJSONObject(i);
                        String title = object.getString("title");
                        String content = object.getString("content");
                        String time = object.getString("time");

                        Notification notification = new Notification(title, content, time);
//                        notifications.add(notification);
                        addItem(notification);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", DataLocalManager.getUser().getIdUser());
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        VolleySingleton.getInstance(getContext()).getRequestQueue().add(stringRequest);
    }
}