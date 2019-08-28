package com.xiaofangfang.lifetatuor.server;


import android.content.Context;
import android.content.SharedPreferences;

import com.xiaofangfang.lifetatuor.set.SettingStandard;

import java.io.File;

/**
 * 文件访问的服务,提供了对内部文件,外部文件的快速访问
 */
public class FileVisitServer {


    /**
     * 获得天气的设置的私有访问方式
     *
     * @param context
     * @return
     */
    public static SharedPreferences getWeatherSettingPrivate(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                SettingStandard.Weather.WEATHER,
                Context.MODE_PRIVATE);
        return sp;
    }

    /**
     * 获得缓存文件夹   /data/data/com..package..name/cache
     *
     * @param context
     * @return
     */
    public static File getCacheFileDir(Context context) {
        return context.getCacheDir();
    }


    /**
     * 如果需要,创建一个文件夹,用来存放自定义的数据
     * /data/data/com...package..name/
     *
     * @param name  文件名称
     * @param model 取值是context的常量
     * @return
     */
    public static File getDir(String name, int model, Context context) {
        return context.getDir(name, model);
    }


    /**
     * 创建天气的文件夹
     *
     * @param context
     * @return
     */
    public static File createWeatherDir(Context context) {
        return getDir("weather", Context.MODE_PRIVATE, context);
    }

    public static File createNewsDir(Context context) {
        return getDir("news", Context.MODE_PRIVATE, context);
    }

    public static File createJokeDir(Context context) {
        File file = getDir("joke", Context.MODE_PRIVATE, context);

        return file;
    }


}
