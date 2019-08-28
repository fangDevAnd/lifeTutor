package com.example.logcatuploadmodule;

import android.app.Application;

import com.example.logcatuploadmodule.crashHandler.CrashReportedUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        CrashReportedUtils.init(getApplicationContext(), getFilesDir() + "/crash");
        CrashReportedUtils.sendCrashedReportsToServer();


    }
}
