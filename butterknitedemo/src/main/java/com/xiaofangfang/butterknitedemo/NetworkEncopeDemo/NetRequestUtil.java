package com.xiaofangfang.butterknitedemo.NetworkEncopeDemo;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetRequestUtil {


    public static void sendRequest(String url, Callback callback) {

        Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);

    }


}
