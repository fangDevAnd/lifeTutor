package com.xiaofangfang.pluginFramework.virtualApk.baseApplication;

import android.app.Application;
import android.content.Context;

import com.didi.virtualapk.PluginManager;

public class BaseMyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
    }


}
