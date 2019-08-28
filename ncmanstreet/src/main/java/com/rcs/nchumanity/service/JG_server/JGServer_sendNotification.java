package com.rcs.nchumanity.service.JG_server;

import com.rcs.nchumanity.service.JG.Logger;

import java.util.concurrent.Executors;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;

public class JGServer_sendNotification {

    public static String APP_KEY = "e72bffb2c220d4872b894b07";

    public static String Secret = "f9df54611085c9f62cb4bd6e";

    public static String title = "南昌人道";


    public static void sendPushNoti(String notification) {

        JPushClient jpushClient = new JPushClient(Secret, APP_KEY, null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_tag_alertWithTitle(notification);

        PushResult result = null;

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    jpushClient.sendPush(payload);
                    Logger.i("test", "Got result - " + result);

                } catch (APIConnectionException e) {
                    // Connection error, should retry later
                    Logger.i("test", "Connection error, should retry later" + e);

                } catch (APIRequestException e) {
                    // Should review the error, and fix the request
                    Logger.i("test", "Should review the error, and fix the request" + e);
                    Logger.i("test", "HTTP Status: " + e.getStatus());
                    Logger.i("test", "Error Code: " + e.getErrorCode());
                    Logger.i("test", "Error Message: " + e.getErrorMessage());
                }
            }
        });

        return;
    }

    private static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(ALERT);
    }


    private static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias("alias1"))
                .setNotification(Notification.alert(ALERT))
                .build();
    }


    /**
     * 发送通知
     * android 平台
     * 标题为 title
     * 内容自定义
     *
     * @param content
     * @return
     */
    private static PushPayload buildPushObject_android_tag_alertWithTitle(String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.android(content, title, null))
                .build();
    }


}