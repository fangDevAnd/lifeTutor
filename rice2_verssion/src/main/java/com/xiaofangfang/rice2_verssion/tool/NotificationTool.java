package com.xiaofangfang.rice2_verssion.tool;


import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.os.Build;
import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.xiaofangfang.rice2_verssion.R;

/**
 * 通知的工具类
 */
public abstract class NotificationTool<T> {

    Context context;

    public NotificationTool(Context context) {
        this.context = context;
    }

    private RemoteViews remoteViews;


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Notification createNotification(String channelId, @LayoutRes int resId, T... objs) {

        Notification.Builder builder = new Notification.Builder(context)
                .setCustomContentView(remoteViews = new RemoteViews(context.getPackageName(), resId))
                .setSmallIcon(R.drawable.rice)
                .setChannelId(channelId);
        Notification notification = builder.build();
        bindView(notification, remoteViews, objs);

        return notification;
    }

    protected abstract void bindView(Notification notification, RemoteViews remoteViews, T[] objs);

}
