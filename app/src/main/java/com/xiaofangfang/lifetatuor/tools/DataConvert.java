package com.xiaofangfang.lifetatuor.tools;

import com.xiaofangfang.lifetatuor.Activity.fragment.weather.Fragment.weatherInfoRichuViewPagerFragment.RichuPagerFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 进行一些数据的转换工作
 */
public class DataConvert {


    /**
     * 将list数据转化为Object的数组
     *
     * @param list
     * @return
     */
    public static String[] list2Array(List<String> list) {
        String[] strs = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            strs[i] = list.get(i);
        }
        return strs;
    }


    /**
     * 计算某时刻的贝塞尔所处的值
     *
     * @param t      时间(0-1)
     * @param values 贝塞尔点的集合 返回所处的点
     */
    public static  float calculateBuzier(float t, float... values) {
        // 采用双层循环
        final int len = values.length;
        for (int i = len - 1; i > 0; i--) {
            // 外层
            for (int j = 0; j < i; j ++ ) {
            // 计算
                values[j] = values[j] + (values[j + 1] - values[j]) * t;
            }
        }
            // 运算时结构保存在第一位
            // 所以我们返回第一位
        return values[0];
    }


    /**
     * 计算时差  保存信息到DaySunTime.totalTime域中
     * @return
     */
    public static  RichuPagerFragment.DaySunTime dateCal(RichuPagerFragment.DaySunTime da){
        String startTime=da.shengTime;
        String endTime=da.luoTime;
        int startHour= Integer.parseInt(startTime.split(":")[0]);
        int startSecond= Integer.parseInt(startTime.split(":")[1]);

        int endHour= Integer.parseInt(endTime.split(":")[0]);
        int endSecond= Integer.parseInt(endTime.split(":")[1]);

        int hour;
        int second;

        if(endHour>=startHour){
            hour=endHour-startHour;
        }else {
            hour=24-(startHour-endHour);
        }

        if(endSecond>=startSecond){
            second=endSecond-startSecond;
        }else {
            second=endSecond+60-startSecond;
            hour--;
        }

        da.totalTime=hour*60+second;
        return da;

    }

    /**
     * 获得当前的时间
     * 将当前的时间存放在  的luoTime时间里
     * @return
     */
    public static  RichuPagerFragment.DaySunTime obtainCurrentTime(){

        Date day=new Date();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        String time=df.format(day);


        RichuPagerFragment.DaySunTime daySunTime=
                new RichuPagerFragment.DaySunTime();

        daySunTime.luoTime=time;

        return daySunTime;
    }

    /**
     * 获得当前的年月份
     * @return
     */
    public static String obtainCurrentDate(){
        Date day=new Date();

        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        return df.format(day);
    }



}
