package com.rcs.nchumanity.ul.basicMap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.net.URISyntaxException;

public class MapStartParam {

    /**
     * 腾讯地图 Uri 标识
     */
    public final static String TECENT_BASE_URL = "qqmap://map/";

    /**
     * 调用腾讯地图app驾车导航
     * (此处输入方法执行任务.)
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2017/11/9,15:31
     * <h3>UpdateTime</h3> 2017/11/9,15:31
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     * @param name    必 目标地址名称
     * @param tocoord 必 目标经纬度 39.9761,116.3282
     */
    public static void invokeTecentNavi(Context context, @NonNull String name, @NonNull String tocoord) {
        StringBuffer stringBuffer = new StringBuffer(TECENT_BASE_URL).append("routeplan?");

        stringBuffer.append("&type=drive");
        if (!TextUtils.isEmpty(name)) {
            stringBuffer.append("&to=").append(name);
        }
        if (!TextUtils.isEmpty(tocoord)) {
            stringBuffer.append("&tocoord=").append(tocoord);
        }
        stringBuffer.append("&policy=1");
        stringBuffer.append("&referer=com.rcs.nchumanity");

        Intent intent = new Intent();
        intent.setData(Uri.parse(stringBuffer.toString()));
        context.startActivity(intent);
    }


    public static String BAIDU_BASE_URL = "baidumap://map/navi?";

    /**
     * @param context
     * @param elat    维度
     * @param elong   精度
     * @throws URISyntaxException
     */
    public static void invokeBaiduNavi(Context context, double elat, double elong) throws URISyntaxException {

        StringBuffer stringBuffer = new StringBuffer(BAIDU_BASE_URL);
        stringBuffer.append("location=" + elat + "," + elong + "&coord_type=bd09ll&src=com.rcs.nchumanity");
        Intent i1 = new Intent();
        i1.setData(Uri.parse(stringBuffer.toString()));
        context.startActivity(i1);
    }


    public static String GAODE_BASE_URL = "androidamap://route?sourceApplication=com.rcs.nchumanity";

    /**
     * 高德地图导航
     *
     * @param context 上下文
     * @param elat    终点纬度
     * @param elong   终点的精度
     * @param name    终点的名称
     */
    public static void invokeGaoDeNavi(Context context, double elat, double elong, String name) {

        StringBuffer stringBuffer = new StringBuffer(GAODE_BASE_URL);
        Intent intent = new Intent();
        stringBuffer.append("&dlat=").append(elat);
        stringBuffer.append("&elon").append(elong);
        stringBuffer.append("&name=").append(name);
        stringBuffer.append("dev=0&t=1");
        intent.setData(Uri.parse(stringBuffer.toString()));
        context.startActivity(intent);
    }


}
