package com.example.componentasystemtest.appProtectedAlive.nullNotification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.componentasystemtest.R;

import androidx.annotation.RequiresApi;

public class KeepLiveService extends Service {

    public static final int NOTIFICATION_ID = 0x11;
    private static final String TAG = "test";

    public KeepLiveService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        //API 18以下，直接发送Notification并将其置为前台
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startForeground(NOTIFICATION_ID, new Notification());
        } else {
            //API 18以上，发送Notification并将其置为前台后，启动InnerService
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher).setChannelId("com.example.componentasystemtest");
            startForeground(NOTIFICATION_ID, builder.build());


            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                startForegroundService(new Intent(this, InnerService.class));
            } else {
                startService(new Intent(this, InnerService.class));
            }
        }
        Log.d(TAG, "前台服务创建");

    }

    public static class InnerService extends Service {
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @TargetApi(Build.VERSION_CODES.O)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onCreate() {
            super.onCreate();
            //发送与KeepLiveService中ID相同的Notification，然后将其取消并取消自己的前台显示
            Notification.Builder builder = new Notification.Builder(this);

            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setChannelId("com.example.componentasystemtest");
            startForeground(NOTIFICATION_ID, builder.build());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopForeground(true);
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    NotificationChannel nfc = new NotificationChannel("com.example.componentasystemtest", "qwe", NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(nfc);
                    manager.cancel(NOTIFICATION_ID);
                    stopSelf();
                }
            }, 100);

        }
    }
}