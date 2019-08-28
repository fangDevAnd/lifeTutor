package com.xiaofangfang.filterrice.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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


    static String ipAddress = "129.204.82.25";


    String ipAddress2 = "10.100.19.220";

    String ipAddress3 = "10.109.2.164";

    public static String serverMain = "http://" + ipAddress + ":80/Rice";

    /**
     * 请求登录的地址
     */
    public static String LOGIN_SERVER_URL = "http://" + ipAddress + ":80/Rice/LoginAction";
    /**
     * 请求注册的地址
     */
    public static String REGISTER_SERVER_URL = "http://" + ipAddress + ":80/Rice/RegisterAction";

    public static String UserCenter = "http://" + ipAddress + ":80/Rice/UserCenter";
    /**
     * 请求网络数据的节点地址
     */
    public static String APP_LAYOUT_SERVER_URL = "http://" + ipAddress + ":80/Rice/DataCenterAction";

    /**
     * 商品过滤的列表,用来查询商品的数据分类
     */

    public static String productFilterList = "http://" + ipAddress + ":80/Rice/ProductFilterClass";

    /**
     * 卡的数据筛选界面
     */
    public static String cardDataFilter = "http://" + ipAddress + ":80/Rice/CardDataFilter";


    /**
     * 商品显示界面的数据调用接口URL
     * 这个接口的作用是通过返回的数据的信息动态的调整整个界面的小时效果
     */
    public static String productSalePageAction = "http://" + ipAddress + ":80/Rice/ProductSalePageAction";


    /**
     * 主页的数据请求url
     */
    public static String mainFragmentAction = "http://" + ipAddress + ":80/Rice/MainFragmentAction";

    /**
     * 自己视图的数据请求的url
     */
    public static String mySelfFragmentAction = "http://" + ipAddress + ":80/Rice/MySelfFragmentAction";

    /**
     * 业务界面的显示请求的Url
     */
    public static String businessFragmentAction = "http://" + ipAddress + ":80/Rice/BusinessFragmentAction";

    /**
     * 手机展示界面的数据请求的uRL
     */
    public static String phoneFragmentAction = "http://" + ipAddress + ":80/Rice/PhoneFragmentAction";


    /**
     *销售界面的数据请求地址
     *
     */

    /**
     *
     */
    public static String liangHaoRequestUrl = "http://" + ipAddress +
            ":80/Rice/LiangHaoAction";

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

        Bitmap bmp = null;

        URL myurl = null;
        try {
            myurl = new URL(url);
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


    public interface ImageLoadCallback {

        void onImageLoadSucessful(Bitmap bitmap);

    }


}
