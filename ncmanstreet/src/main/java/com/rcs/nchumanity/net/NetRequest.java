package com.rcs.nchumanity.net;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.IntDef;

import com.rcs.nchumanity.application.MyApplication;
import com.rcs.nchumanity.entity.PersistenceData;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 发送网络请求的类
 * 网络请求的封装实现
 */
public class NetRequest {


    public static final int MODE_FORCE = 201;

    public static final int MODE_SILENCE = 202;

    public static final int MODE_SOFT = 203;


    @IntDef(flag = true, value = {
            MODE_FORCE,
            MODE_SILENCE,
            MODE_SOFT,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadCase {
    }


    public static int connectTimeout = 10;

    /**
     * 发送网络请求的处理类
     * 这个默认是get请求
     *
     * @param url
     * @param callback
     */
    public static void requestUrl(String url, Callback callback) {

        Request.Builder builder = new Request.Builder();
        String sessionId = PersistenceData.getSessionId(MyApplication.getContext());
        if (!sessionId.equals(PersistenceData.DEF_VAL)) {
            builder.addHeader("cookie", sessionId);
        }
        Request request = builder.url(url).build();
        Log.d("test", "requestHead: " + request.headers());
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS).build();

        ok.newCall(request).enqueue(callback);
    }


    /**
     * @param url          下载的链接
     * @param savePath     保存文件的位置 ，如果保存到sdcard，请申请权限，建议在软件启动的时候进行权限的申请
     * @param fileName     保存文件的名称
     * @param downListener 下载过程的监听
     */
    public static void onDown(String url, final String savePath, final String fileName, final DownListener downListener) {
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


    /**
     * 下载的接口回调
     */
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
    public static void requestPost(String url, Map<String, String> param, Callback callback) {

        FormBody.Builder builder = new FormBody.Builder();

        Set<String> keys = param.keySet();
        for (String key : keys) {
            builder.add(key, param.get(key));
        }


        RequestBody requestBody = builder.build();
        Request.Builder requestBuilder = new Request.Builder();
        String sessionId = PersistenceData.getSessionId(MyApplication.getContext());
        requestBuilder.url(url).post(requestBody);
        if (!sessionId.equals(PersistenceData.DEF_VAL)) {
            requestBuilder.addHeader("cookie", sessionId);
        }

        requestBuilder.addHeader("Content-Type","application/x-www-form-urlencoded");

        Request request = requestBuilder.build();

        Log.d("test", "requestHead: " + request.headers());
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS).build();
        ok.newCall(request).enqueue(callback);
    }


    /**
     * post json 数据
     *
     * @param url
     * @param json
     * @param callback
     */
    public static void requestPostJson(String url, String json, Callback callback) {
        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);

        Request.Builder builder = new Request.Builder();

        String sessionId = PersistenceData.getSessionId(MyApplication.getContext());
        if (!sessionId.equals(PersistenceData.DEF_VAL)) {
            builder.addHeader("cookie", sessionId);
        }

        Request request = builder.url(url).post(requestBody).build();
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS).build();
        ok.newCall(request).enqueue(callback);

    }


    private static String imageName = "photo";

    public static void setImageName(String imageName) {
        NetRequest.imageName = imageName;
    }


    public static void postImage(String url, String imagePath, Map<String, String> param, Callback callback) {


        File file = new File(imagePath);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(imageName, imageName + System.currentTimeMillis() + ".jpg",
                        RequestBody.create(MediaType.parse("image/png"), file));
        Set<String> keys = param.keySet();
        for (String key : keys) {
            builder.addFormDataPart(key, param.get(key));
        }

        //请求体
        RequestBody requestBody = builder.build();

        //构建请求头
        Request.Builder builder1 = new Request.Builder();

        String sessionId = PersistenceData.getSessionId(MyApplication.getContext());
        if (!sessionId.equals(PersistenceData.DEF_VAL)) {
            builder1.addHeader("cookie", sessionId);
        }


        Request request = builder1.url(url).post(requestBody).build();


        Log.d("test", "postImage: " + request.headers());

        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS).build();
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


}
