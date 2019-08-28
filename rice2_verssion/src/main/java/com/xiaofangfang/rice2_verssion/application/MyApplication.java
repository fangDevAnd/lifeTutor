package com.xiaofangfang.rice2_verssion.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.xiaofangfang.rice2_verssion.tool.Tools;

import org.litepal.LitePal;

public class MyApplication extends Application {


    public SharedPreferences setting;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        setting = Tools.getSystemSeeting(this);
    }
}
