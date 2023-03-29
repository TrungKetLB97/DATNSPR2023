package com.app.projectfinal_master.data;

import android.content.Context;

import com.app.projectfinal_master.model.Product;
import com.app.projectfinal_master.model.User;
import com.google.gson.Gson;

public class DataLocalManager {
    public static final String PREF_OBJECT_USER = "PREF_OBJECT_USER";
    public static final String PREF_OBJECT_PRODUCT = "PREF_OBJECT_PRODUCT";
    public static DataLocalManager instance;
    public MySharedPreferences mySharedPreferences;

    public static void init (Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setUser(User user) {
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(user);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_USER, strJsonUser);
    }

    public static User getUser() {
        String strJsonUser = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_OBJECT_USER);
        Gson gson = new Gson();
        User user = gson.fromJson(strJsonUser, User.class);
        return user;
    }

    public static void setProduct(Product product) {
        Gson gson = new Gson();
        String strJsonSP = gson.toJson(product);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_USER, strJsonSP);
    }

    public static Product getProduct() {
        String strJsonSP = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_OBJECT_USER);
        Gson gson = new Gson();
        Product product = gson.fromJson(strJsonSP, Product.class);
        return product;
    }
}
