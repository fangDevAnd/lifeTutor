package com.example.componentasystemtest.notification;


import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.widget.RemoteViews;


/**
 * 消息通知的工具类
 */

public abstract class NotificationTools {


    public Notification createNoti(Context context, boolean isConsume, int... layoutRes) {


        Notification.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && isConsume) {

            RemoteViews remoteViews;

            builder = new Notification.Builder(context)
                    .setCustomContentView(remoteViews = new RemoteViews(context.getPackageName(), layoutRes[0]));
            bindView(remoteViews, builder);
        } else if (!isConsume) {
            builder = new Notification.Builder(context);
            bindView(null, builder);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return builder.build();
        }
        return null;
    }

    abstract void bindView(RemoteViews remoteViews, Notification.Builder builder);


}

