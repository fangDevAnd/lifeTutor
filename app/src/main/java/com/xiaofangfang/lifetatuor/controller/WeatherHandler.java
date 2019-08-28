package com.xiaofangfang.lifetatuor.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.xiaofangfang.lifetatuor.model.LocationInfo;
import com.xiaofangfang.lifetatuor.net.WeatherRequest;
import com.xiaofangfang.lifetatuor.net.progress.NetRequest;
import com.xiaofangfang.lifetatuor.server.FileVisitServer;
import com.xiaofangfang.lifetatuor.server.LocationServer;
import com.xiaofangfang.lifetatuor.server.interfaces.LocationCallback;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.Looger;

import java.util.HashSet;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * 天气相关信息的处理类
 * 下面是我们制定的更新策略
 * <p>
 * 在存放天气相关信息的时候我们同事存放天气的事件,如果进行刷新或者使用加载的过程
 * 我们检测到当前的时间就是当前的时间,(以天为代表)就不去更新,反之就去更新当前的信息,并将更新的相关信息
 * 存放到数据库
 */
public class WeatherHandler {


    /**
     * 获得被绑定的位置的列表
     *
     * @param context
     * @return
     */
    public static HashSet<String> getBindedtLocationInfo(Context context) {

        SharedPreferences sp = FileVisitServer.getWeatherSettingPrivate(context);

        HashSet hashSet = new HashSet();

        hashSet = (HashSet) sp.getStringSet(
                SettingStandard.Weather.LOCATION_SET, hashSet);

        return hashSet;
    }

    /**
     * 从新设置位置信息的存储
     * @param context
     * @param set
     */
    public static void setBindedLacationInfo(Context context,HashSet set){
        SharedPreferences sp = FileVisitServer.getWeatherSettingPrivate(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.putStringSet(SettingStandard.Weather.LOCATION_SET,set);
        editor.commit();
    }


    /**
     * 查找当前的相关位置
     *
     * @param context
     */
    public static void locationFind(Context context, LocationCallback lc) {
        getLocation(context, lc);
    }


    /**
     * 存放天气的相关信息到数据库
     */
    public static void saveWeatherInfo() {


    }

    /**
     * 获得当前的位置信息
     *
     * @param context
     * @return
     */
    private static void getLocation(Context context, LocationCallback lc) {

        LocationServer.obtainCurrentLocation(context, lc);
    }

    /**
     * 获得天气信息,通过位置信息获得
     * 当我们获得天气信息的时候应该存储对应的信息
     *
     * @param locationInfo
     */
    public static void obtainWeatherInfoWithLocation(
            LocationInfo locationInfo, Callback callback) {

        WeatherRequest.getWeatherInfo(locationInfo.getCity(),callback);

    }
}
