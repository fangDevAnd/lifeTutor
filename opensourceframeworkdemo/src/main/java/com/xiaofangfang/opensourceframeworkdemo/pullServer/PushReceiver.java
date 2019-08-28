package com.xiaofangfang.opensourceframeworkdemo.pullServer;

import android.content.Context;

import com.google.gson.Gson;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


/**
 * 当信鸽sdk推送信息到该应用上时,PushReceiver广播就会接受到相关信息,开发者只需要在PushReceiver类重载的方法中写处理消息的逻辑即可.
 */
public class PushReceiver extends XGPushBaseReceiver {


    /**
     * 注册的回调
     *
     * @param context
     * @param i
     * @param xgPushRegisterResult
     */
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    /**
     * 反注册的回调
     *
     * @param context
     * @param i
     */
    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    /**
     * 设置标签回调
     *
     * @param context
     * @param i
     * @param s
     */
    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    /**
     * 删除标签的回调
     *
     * @param context
     * @param i
     * @param s
     */
    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    /**
     * 应用内的消息回调
     *
     * @param context
     * @param xgPushTextMessage
     */
    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {

        String title = xgPushTextMessage.getTitle();
        String custom = xgPushTextMessage.getCustomContent();

        Gson gson = new Gson();

        Map<String, Object> map = new HashMap<>();
        map = gson.fromJson(custom, map.getClass());

        String content = (String) map.get("content");
        String date = (String) map.get("date");

        MessageEvent event = new MessageEvent(title, content, date);

        EventBus.getDefault().post(event);

    }

    /**
     * 通知被点击处理的回调
     *
     * @param context
     * @param xgPushClickedResult
     */
    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

    }

    /**
     * 通知被展示触发的回调,可以在此保存App收到的通知
     *
     * @param context
     * @param xgPushShowedResult
     */
    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

    }
}
