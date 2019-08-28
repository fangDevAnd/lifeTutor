package com.rcs.nchumanity.application;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.orhanobut.logger.Logger;
import com.rcs.nchumanity.crashHandler.CrashReportedUtils;

import org.litepal.LitePal;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 数据库框架的初始化
         */
        LitePal.initialize(this);
        /**
         * 日志框架的
         */
        Logger.init("test");

        context = getApplicationContext();

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);


        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush


        /**
         * 日志上报模块的实现
         */
//        CrashReportedUtils.init(getApplicationContext(), getFilesDir() + "/crash");
//        CrashReportedUtils.sendCrashedReportsToServer();

    }

    public static Context getContext() {
        return context;
    }

}
