package com.example.componentasystemtest.broadcastTest.netStatusChange;


import android.content.Context;
import android.net.wifi.WifiManager;

/**
 *
 */
public class WifiStatusUtil {


    private WifiManager wifiManager;

    public WifiStatusUtil(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }


    /**
     * 返回wifi开关是否打开，如果打开返回true
     * 反之返回false
     *
     * @return
     */
    private boolean wifiEnabled() {
        return wifiManager.isWifiEnabled();
    }

    public void closeWIFI() {
        if (wifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }


}
