package com.example.componentasystemtest.application;

import android.app.Application;
import android.content.SharedPreferences;

import org.litepal.LitePal;

public class MyApplication extends Application {


    public SharedPreferences setting;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
