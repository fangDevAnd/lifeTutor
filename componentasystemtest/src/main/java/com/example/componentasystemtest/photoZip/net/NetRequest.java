package com.example.componentasystemtest.photoZip.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 发送网络请求的类
 */
public class NetRequest {


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


    //加载图片
    public static void getURLimage(final String url, final ImageLoadCallback callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = null;
                try {
                    URL myurl = new URL(url);
                    // 获得连接
                    HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
                    conn.setConnectTimeout(6000);//设置超时
                    conn.setDoInput(true);
                    conn.setUseCaches(false);//不缓存
                    conn.connect();
                    InputStream is = conn.getInputStream();//获得图片的数据流
                    bmp = BitmapFactory.decodeStream(is);
                    is.close();
                    callback.onImageLoadSucessful(bmp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //加载图片
    public static Bitmap getURLimageSync(final String url) {

        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }


    public interface ImageLoadCallback {
        void onImageLoadSucessful(Bitmap bitmap);

    }


}
