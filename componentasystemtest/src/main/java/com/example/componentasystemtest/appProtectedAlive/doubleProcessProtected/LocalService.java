package com.example.componentasystemtest.appProtectedAlive.doubleProcessProtected;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.componentasystemtest.R;
import com.example.componentasystemtest.Service_1;
import com.example.componentasystemtest.musicPlay.simple2.PlayService;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


/**
 * 本地的服务
 */
public class LocalService extends Service {


    private static final String TAG = "test";
    private ServiceConnection conn;
    private MyService myService;


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");

        return myService;
    }

    private int id = 12;

    private NotificationManager manager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(new NotificationChannel("123", "通知", NotificationManager.IMPORTANCE_HIGH));
        createNoti();
        startForeground(12, notification);
        init();
    }


    Notification notification;
    RemoteViews remoteViews;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createNoti() {

        notification = new Notification.Builder(this)
                .setCustomContentView(remoteViews = new RemoteViews(getPackageName(), R.layout.noti_baohuo))
                .setSmallIcon(R.drawable.rice)
                .setContentTitle("保活service")
                .setChannelId("123")
                .build();
    }


    private void init() {
        if (conn == null) {
            conn = new MyServiceConnection();//创建了remoteservice连接对象
        }
        myService = new MyService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        Toast.makeText(getApplicationContext(), "本地进程启动", Toast.LENGTH_LONG).show();
        Intent intents = new Intent();
        intents.setClass(this, RemoteService.class);
        bindService(intents, conn, Context.BIND_AUTO_CREATE);
        return START_STICKY;
    }

    class MyService extends Service_1.Stub {


        @Override
        public String getName() throws RemoteException {
            return "fangfangafang";
        }
    }

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("获取连接");
            Toast.makeText(LocalService.this, "本地服务获得连接", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(LocalService.this, "远程连接被干掉了", Toast.LENGTH_SHORT).show();
            LocalService.this.startService(new Intent(LocalService.this,
                    RemoteService.class));
            LocalService.this.bindService(new Intent(LocalService.this,
                    RemoteService.class), conn, Context.BIND_IMPORTANT);
        }

    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}

