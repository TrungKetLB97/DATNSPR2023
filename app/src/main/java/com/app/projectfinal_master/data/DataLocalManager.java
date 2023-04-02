package com.app.projectfinal_master.data;

import android.content.Context;
import android.util.Log;

import com.app.projectfinal_master.model.Cart;
import com.app.projectfinal_master.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataLocalManager {
    public static final String PREF_OBJECT_USER = "PREF_OBJECT_USER";
    public static final String PREF_OBJECT_CART = "PREF_OBJECT_CART";
    public static final String PREF_OBJECT_CARTS = "PREF_OBJECT_CARTS";
    public static DataLocalManager instance;
    public MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
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

    public static void addCart(Cart cart) {
        Gson gson = new Gson();
        String strJsonCart = gson.toJson(cart);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_CART, strJsonCart);
    }

    public static void addCarts(List<Cart> carts) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(carts).getAsJsonArray();
        String strJsonCart = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_CARTS, strJsonCart);
    }

    public static List<Cart> getCarts() {
        String strJsonCart = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_OBJECT_CARTS);
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();

        Type type = new TypeToken<ArrayList<Cart>>() {
        }.getType();

        List<Cart> carts = gson.fromJson(strJsonCart, type);
        if (carts == null) {
            carts = new ArrayList<>();
        }
        return carts;
    }

    public static void changeDataCart(Cart cart, int position) {
        List<Cart> carts = getCarts();
        carts.set(position, cart);
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(carts).getAsJsonArray();
        String strJsonCart = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_CARTS, strJsonCart);
        addCarts(carts);
    }

    public static void removeDataCart(int position) {
        List<Cart> carts = getCarts();
        carts.remove(position);
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(carts).getAsJsonArray();
        String strJsonCart = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_CARTS, strJsonCart);
    }

    public static void removeDataCarts() {
        List<Cart> carts = getCarts();
        carts.clear();
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(carts).getAsJsonArray();
        String strJsonCart = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_CARTS, strJsonCart);
    }
}
