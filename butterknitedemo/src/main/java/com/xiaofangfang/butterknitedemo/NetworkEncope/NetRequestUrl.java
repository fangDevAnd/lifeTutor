package com.xiaofangfang.butterknitedemo.NetworkEncope;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetRequestUrl {

    /**
     * 请求url的工具类
     *
     * @param url
     * @param callback
     */
    public static void requestUrl(String url, Callback callback) {

        Request request = new Request.Builder().url(url).build();

        OkHttpClient okHttpClient = new OkHttpClient();

        okHttpClient.newCall(request).enqueue(callback);

    }


}
