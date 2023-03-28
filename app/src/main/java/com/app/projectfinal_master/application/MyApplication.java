package com.app.projectfinal_master.application;

import android.app.Application;

import com.app.projectfinal_master.data.DataLocalManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
