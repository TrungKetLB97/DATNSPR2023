package com.app.projectfinal_master.utils;

import static com.app.projectfinal_master.utils.Constant.LOGIN;
import static com.app.projectfinal_master.utils.Constant.PRODUCTS;
import static com.app.projectfinal_master.utils.Constant.CATEGORIES;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.projectfinal_master.activity.HomeActivity;
import com.app.projectfinal_master.data.DataLocalManager;
import com.app.projectfinal_master.fragment.SignUpFragment;
import com.app.projectfinal_master.model.Category;
import com.app.projectfinal_master.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConnectedDatabase {


}
