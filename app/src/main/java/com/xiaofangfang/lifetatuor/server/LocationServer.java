package com.xiaofangfang.lifetatuor.server;


import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xiaofangfang.lifetatuor.model.LocationInfo;
import com.xiaofangfang.lifetatuor.server.interfaces.LocationCallback;
import com.xiaofangfang.lifetatuor.tools.Looger;

import static com.xiaofangfang.lifetatuor.server.LocationServer.location;

/**
 * 位置服务,用来加载位置的相关信息
 * 使用的是百度地图的开放api
 */
public class LocationServer {
    /**
     * 静态实现,加载1次
     */
    static {

    }

    public static LocationClient location;

    public static void obtainCurrentLocation(Context context,
                                             LocationCallback lc) {
        //获得位置服务
        location = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        location.setLocOption(option);

        //注册位置服务的监听
//        location.registerLocationListener(new MyBDLocationListener(lc));

        location.registerLocationListener(new MyBDLocationListener(lc));
        //开始定位
        if (!location.isStarted()) {
            location.start();
        }
    }


    /**
     * 停止当前的定位服务
     */
    public static void stopLocationServer() {
        if (location != null) {
            location.stop();
        }
    }


}


class MyBDLocationListener extends BDAbstractLocationListener {

    LocationCallback lc;

    public MyBDLocationListener(LocationCallback lc) {
        this.lc = lc;
    }


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        onReceiveLocation(bdLocation, lc);
    }

    /**
     * 接受到信息的回调 ,使用的是适配器模式
     *
     * @param bdLocation
     * @param cl
     */
    public void onReceiveLocation(BDLocation bdLocation,
                                  LocationCallback cl) {

        LocationInfo li = new LocationInfo();

        //传递的信息将会回调到这里
        li.setLatitude(bdLocation.getLatitude() + "");//维度
        li.setAltitude(bdLocation.getAltitude() + "");//获得精度
        li.setCountry(bdLocation.getCountry());//获得国家
        li.setProvince(bdLocation.getProvince());//获得省份
        li.setCity(bdLocation.getCity());//获得市
        li.setDistrict(bdLocation.getDistrict());//区
        li.setStreet(bdLocation.getStreet());//获得街道
        li.setCityCode(bdLocation.getCityCode());//获得城市代码..可以用来查询城市的天气信息
        Looger.d(li.toString());
        Looger.d(bdLocation.getLocType() + "");
//
//        if (li.getCity() == null || li.getCountry() == null) {
//            location.requestLocation();
//            return;
//        }

        cl.locationInfo(li);

    }


}
