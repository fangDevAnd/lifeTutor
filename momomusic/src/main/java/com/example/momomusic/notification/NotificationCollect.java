package com.example.momomusic.notification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.RemoteViews;

import com.example.momomusic.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 创建了一些通用的Notification，用来作为一些通用的提示
 */
public class NotificationCollect {

    /**
     * 通用的notification，对高版本的sdk做了适配
     *
     * @param title
     * @param content
     * @param id
     * @param context
     */
    public static void generNoti(String title, String content, String id, Context context) {

        NotificationTools notificationTools = new NotificationTools() {
            @Override
            public void bindView(RemoteViews remoteViews, Notification.Builder builder) {
                //当isConsume为false的时候，代表的是没有通过自定义视图，所以RemoteViews为null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    builder.setChannelId("14");
                }

                builder.setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.rice);
            }
        };

        Notification notification = notificationTools.createNoti(context, false);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel(id, "消息通知", NotificationManager.IMPORTANCE_HIGH));
        }
        notificationManager.notify(Integer.parseInt(id), notification);
    }

}
