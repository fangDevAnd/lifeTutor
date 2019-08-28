package com.JGpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import static cn.jpush.api.push.model.notification.AndroidNotification.TITLE;
import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;

public class JGServer_sendNotification {

    public static String APP_KEY = "e72bffb2c220d4872b894b07";

    public static String Secret = "f9df54611085c9f62cb4bd6e";

    public static void main(String[] argc) {

        JPushClient jpushClient = new JPushClient(Secret,APP_KEY, null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert();

        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.print("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            System.out.print("Connection error, should retry later");

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            System.out.print("Should review the error, and fix the request");
            System.out.print("HTTP Status: " + e.getStatus());
            System.out.print("Error Code: " + e.getErrorCode());
            System.out.print("Error Message: " + e.getErrorMessage());
        }

    }

    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(ALERT);
    }

    public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias("alias1"))
                .setNotification(Notification.alert(ALERT))
                .build();
    }

    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }




}
