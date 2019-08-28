package com.xiaofangfang.lifetatuor.net;

import com.xiaofangfang.lifetatuor.net.progress.NetRequest;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.Looger;

import okhttp3.Call;
import okhttp3.Callback;

/*
获得天气相关的信息
 */
public class WeatherRequest {

    /**
     * 根据请求的城市名获得相关的数剧
     *
     * @param cityName
     * @param callback
     */
    public static void getWeatherInfo(String cityName, Callback callback) {
        if (cityName.contains("市")) {
           cityName=cityName.substring(0, cityName.length() - 1);
        }
        ;
        Looger.d("城市名称"+cityName);
        //这里将url改错,返回的数据使用我们自己的一次的数据做测试
        String url = SettingStandard.Weather.WEATHER_URL + cityName;
        NetRequest.request(url, callback);

    }

}
