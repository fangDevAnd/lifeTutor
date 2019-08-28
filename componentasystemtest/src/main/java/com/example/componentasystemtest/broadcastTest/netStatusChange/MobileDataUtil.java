package com.example.componentasystemtest.broadcastTest.netStatusChange;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * 移动数据开关工具类
 */
public class MobileDataUtil {

    ConnectivityManager conManager;
    private Context context;

    public MobileDataUtil(Context context) {
        conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.context = context;
    }


    public void closeMobileData() {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {

            Class[] getArgArray = null;
            Class[] setArgArray = new Class[]{boolean.class};
            Object[] getArgInvoke = null;
            try {
//                Method mGetMethod = conManager.getClass().getMethod("getMobileDataEnabled", getArgArray);
                Method mSetMethod = conManager.getClass().getMethod("setMobileDataEnabled", setArgArray);
                mSetMethod.setAccessible(true);
                mSetMethod.invoke(conManager, false);
//                boolean isOpen = (Boolean) mGetMethod.invoke(conManager, getArgInvoke);
//                if (isOpen) {
//                    mSetMethod.invoke(conManager, false);
//                } else {
//                    mSetMethod.invoke(conManager, true);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TelephonyManager teleManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            Class[] getArgArray = null;
            Class[] setArgArray = new Class[]{boolean.class};
            Object[] getArgInvoke = null;
            try {
//                Method mGetMethod = teleManager.getClass().getMethod("getDataEnabled", getArgArray);
                Method mSetMethod = teleManager.getClass().getMethod("setDataEnabled", setArgArray);
                mSetMethod.setAccessible(true);
                mSetMethod.invoke(teleManager, false);

//                boolean isOpen = (Boolean) mGetMethod.invoke(teleManager, getArgInvoke);
//                if (isOpen) {
//                    mSetMethod.invoke(teleManager, false);
//                } else {
//                    mSetMethod.invoke(teleManager, true);
//                }
            } catch (Exception e) {
                e.printStackTrace();

                /**
                 * 报java.lang.reflect.InvocationTargetException
                 *
                 *  getDataEnabled调用没有参数，getArgInvoke传空就可以。抛出的异常是因为最新版的Android系统增加了权限控制，
                 *  调用setDataEnabled需要MODIFY_PHONE_STATE权限。而这是一个系统权限，一般的APP获取不到，就报错了
                 *
                 *
                 */
            }
        }

    }


}
