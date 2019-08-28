package com.xiaofangfang.rice2_verssion.tool;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 提供的验证码服务
 * 使用该类的时候最先应该registerProgress注册验证请求,之后才能进行发送以及提交的处理
 */
public class ValidateCodeServler {


    /**
     * 返回的是handler的回调,我们通过实现这个回调就能够接受系统的信息
     * 注册一个处理
     *
     * @param callback
     */
    public static void registerProgress(Handler.Callback callback) {
        EventHandler eventHandler = new MyEventHandler(callback);
        SMSSDK.registerEventHandler(eventHandler);
    }


    static class MyEventHandler extends EventHandler {

        Handler.Callback callback;

        MyEventHandler(Handler.Callback callback) {

            this.callback = callback;
        }


        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), callback).sendMessage(msg);
        }
    }


    /**
     * 提交验证码
     *
     * @param countryCode 国家代码中国是86
     * @param code        短信验证码
     * @param phone       手机号码
     */
    public static void submitValidateCode(String countryCode, String phone, String code) {
        SMSSDK.submitVerificationCode(countryCode, phone, code);
    }

    /**
     * 发送验证码
     *
     * @param countryCode 国家代码 86
     * @param phone       手机号
     */
    public static void sendValidateCode(String countryCode, String phone) {
        SMSSDK.getVerificationCode(countryCode, phone);
    }


}
