package com.example.componentasystemtest.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;


import com.example.componentasystemtest.R;

import java.io.File;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by fang on 2018/6/10.
 */

public class MyNotificationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo11);
        Button send = (Button) findViewById(R.id.button1);
        send.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //  notification1();
//        notification2();
        notification3();

    }

    private void notification3() {
        NotificationTools notificationTools = new NotificationTools() {
            @Override
            public void bindView(RemoteViews remoteViews, Notification.Builder builder) {
                //当isConsume为false的时候，代表的是没有通过自定义视图，所以RemoteViews为null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    builder.setChannelId("14");
                }

                builder.setContentTitle("你好")
                        .setContentText("最新的功能呢，你使用了吗？")
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.rice);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("14", "消息通知", NotificationManager.IMPORTANCE_HIGH);

                    AudioAttributes.Builder builder1 = new AudioAttributes.Builder();
                    channel.setSound(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "yujian.mp3")), builder1.build());
                } else {
                    builder.setSound(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "yujian.mp3")));
                }
            }
        };

        Notification notification = notificationTools.createNoti(this, false);


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("14", "消息通知", NotificationManager.IMPORTANCE_HIGH));
        }

        notificationManager.notify(1, notification);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void notification2() {
        Notification.Builder builder = new Notification.Builder(this);
        //实现悬挂式的效果
        builder.setFullScreenIntent(PendingIntent.getActivity(this, 1, new Intent(this, MyNotificationActivity.class), FLAG_UPDATE_CURRENT), false);
        builder.setContentText("你有一封邮件");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void notification1() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification1);
        remoteViews.setTextViewText(R.id.removeViewButton, "点击");
        remoteViews.setOnClickPendingIntent(R.id.removeViewButton, PendingIntent.getActivity(this, 1, new Intent(this, MyNotificationActivity.class), FLAG_UPDATE_CURRENT));
        RemoteViews remoteViews1 = new RemoteViews(getPackageName(), R.layout.bignotification);
        builder.setCustomContentView(remoteViews);
        builder.setCustomBigContentView(remoteViews1);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
