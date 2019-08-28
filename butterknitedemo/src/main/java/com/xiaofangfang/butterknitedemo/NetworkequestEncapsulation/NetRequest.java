package com.xiaofangfang.butterknitedemo.NetworkequestEncapsulation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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

    public static void onDown(String url, String savePath, String fileName, DownListener downListener) {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient ok = new OkHttpClient();
        File file;
        ok.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                downListener.failth(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                File file = null;
                BufferedInputStream bfi = null;
                BufferedOutputStream bfo = null;
                try {
                    ResponseBody rb = response.body();
                    InputStream is = rb.byteStream();

                    int totalLength = (int) rb.contentLength();

                    bfi = new BufferedInputStream(is);
                    String fileName1 = NetRequest.getHeaderFileName(response);
                    if (TextUtils.isEmpty(fileName1)) {
                        file = new File(savePath + File.pathSeparatorChar + fileName);
                    } else {
                        file = new File(savePath + File.pathSeparatorChar + fileName1);
                    }

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    bfo = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] b = new byte[1024];
                    int progre = 0;
                    response.body().bytes();
                    while (bfi.read(b, 0, b.length) != -1) {
                        bfo.write(b, 0, b.length);
                        progre += b.length;
                        int progress = (int) (progre * 1.0f / totalLength * 100);
                        downListener.downProgress(progress);
                    }
                    bfo.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    downListener.failth(e);
                } finally {
                    bfo.close();
                    bfi.close();
                }
                downListener.success(file.getAbsolutePath());
            }
        });


    }


    public static interface DownListener {

        void failth(Exception e);

        void downProgress(int progress);

        void success(String filePath);
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


    public interface ImageLoadCallback {

        void onImageLoadSucessful(Bitmap bitmap);

    }


    /**
     * 解析文件头
     * Content-Disposition:attachment;filename=FileName.txt
     * Content-Disposition: attachment; filename*="UTF-8''%E6%9B%BF%E6%8D%A2%E5%AE%9E%E9%AA%8C%E6%8A%A5%E5%91%8A.pdf"
     */
    private static String getHeaderFileName(Response response) {
        String dispositionHeader = response.header("Content-Disposition");
        if (!TextUtils.isEmpty(dispositionHeader)) {
            dispositionHeader.replace("attachment;filename=", "");
            dispositionHeader.replace("filename*=utf-8", "");
            String[] strings = dispositionHeader.split("; ");
            if (strings.length > 1) {
                dispositionHeader = strings[1].replace("filename=", "");
                dispositionHeader = dispositionHeader.replace("\"", "");
                return dispositionHeader;
            }
            return "";
        }
        return "";
    }


    public static void downFile(int url, String savePath, DownListener downListener) {


    }




}
