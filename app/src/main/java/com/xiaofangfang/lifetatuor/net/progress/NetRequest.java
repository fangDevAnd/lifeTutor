package com.xiaofangfang.lifetatuor.net.progress;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 网路请求的类,提供了对网络的访问,以及数据的回调
 */
public class NetRequest {


    public static  void request(String url, Callback callback) {

        OkHttpClient okHttpClient = new OkHttpClient();

        /**
         * 这里我们发现在构建请求的时候使用的是Builder模式
         * 原因是请求需要构建的参数过多,
         */
        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(callback);

    }


}

