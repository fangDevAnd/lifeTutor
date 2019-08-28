package com.example.jsontosql;

import android.app.Application;
import android.content.SharedPreferences;

import org.litepal.LitePal;

public class MyApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
