package com.example.momomusic.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Looper;


import com.orhanobut.logger.Logger;

import org.litepal.LitePal;

public class MyApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Logger.init("test");
    }
}
