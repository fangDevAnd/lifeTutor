package com.example.componentasystemtest.broadcastTest.netStatusChange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * 由于android8.0取消了静态注册，所以对于andrid版本大于7.0的版本，考虑使用JobScheduler
 */
public class NetChangeBroadcast extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        ConnectivityManager connectivityManager;

        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
//                String name = info.getTypeName();
//                Toast.makeText(context, "当前网络名称" + name, Toast.LENGTH_SHORT).show();
                int type = info.getType();
                switch (type) {
                    case ConnectivityManager.TYPE_MOBILE:
                        new MobileDataUtil(context).closeMobileData();
                        break;
                    case ConnectivityManager.TYPE_WIFI:
                        new WifiStatusUtil(context).closeWIFI();
                        break;
                }
            } else {
                Toast.makeText(context, "没有可用网络", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
