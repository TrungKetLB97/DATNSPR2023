package com.app.projectfinal_master.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class MySharedPreferences {
    public static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    private Context mContext;

    public MySharedPreferences (Context mContext) {
        this.mContext = mContext;
    }

    public void putStringValue (String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue (String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public void removeData(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
