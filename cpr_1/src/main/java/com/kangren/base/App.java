package com.kangren.base;

import android.app.Application;

import com.kangren.cpr.AppConfig;
import com.lyc.socket.client.Client;

/**
 * This class is an extension of App.
 * The function of this class is to define the initialization of program crash.
 */
public class App extends Application {

    public Client client;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        AppConfig.context = this;

    }
}
