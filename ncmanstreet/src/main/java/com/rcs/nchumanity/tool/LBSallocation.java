package com.rcs.nchumanity.tool;

import android.Manifest;
import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.rcs.nchumanity.fragment.ParentFragment;
import com.rcs.nchumanity.ul.ParentActivity;

public class LBSallocation {

    /**
     * 获得当前的位置
     * @param context
     * @param listener
     */
    public static void getCurrentLocation(Context context, BDAbstractLocationListener listener){

        LocationClient locationClient;
        locationClient = new LocationClient(context);
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if(!hasLoca){
                    hasLoca=true;
                    listener.onReceiveLocation(bdLocation);
                    locationClient.stop();
                }
            }
        });
        hasLoca = false;
        LocationClientOption lp = new LocationClientOption();
        lp.setOpenGps(true);
        lp.setIsNeedAddress(true);
        lp.setCoorType("bd09ll");
        locationClient.setLocOption(lp);
        locationClient.start();
    }

    private static boolean hasLoca=false;


    /**
     * 开始发起定位 在Activity中使用
     * @param activity
     * @param requestCode
     * @param <T>
     */
    public  static <T extends ParentActivity> void startLocationWithActivity( T activity,int requestCode){

        String[] permission = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        activity.requestPermission(permission,requestCode);
    }

    /**
     * 开始发起定位在fragment中使用
     * @param fragment
     * @param requestCode
     * @param <T>
     */
    public static <T extends ParentFragment> void startLocationWithFragment(T fragment, int requestCode) {
        String[] permission = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        fragment.requestPermission(permission,requestCode);
    }
}
