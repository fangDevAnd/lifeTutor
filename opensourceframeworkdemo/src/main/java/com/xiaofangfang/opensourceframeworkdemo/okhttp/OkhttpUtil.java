package com.xiaofangfang.opensourceframeworkdemo.okhttp;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkhttpUtil {


    /**
     * 发送网络请求的处理类
     * 这个默认是get请求
     *
     * @param url
     * @param callback
     */
    public static void requestUrl(String url, Callback callback) {

        Request request = new Request.Builder().url(url).build();
        OkHttpClient ok = new OkHttpClient();
        ok.newCall(request).enqueue(callback);

    }

    /**
     * 发送post请求
     *
     * @param url
     * @param param
     * @param callback
     */
    public static void requestPost(String url, String param, Callback callback) {

        FormBody.Builder builder = new FormBody.Builder();
        String[] keyValue = param.split("&");
        for (int i = 0; i < keyValue.length; i++) {
            String[] singleKeyValue = keyValue[i].split("=");
            builder.add(singleKeyValue[0], singleKeyValue[1]);
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        OkHttpClient ok = new OkHttpClient();
        ok.newCall(request).enqueue(callback);
    }
}
