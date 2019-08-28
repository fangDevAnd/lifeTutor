package com.xiaofangfang.bootstrap.bridge;


import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import androidx.arch.core.executor.TaskExecutor;

import cn.pedant.SafeWebViewBridge.JsCallback;

/**
 * 用来连接两个操作的类
 */
public class HostJsScope {


    public static void testA(WebView webView, String message) {

        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 启动的action
     *
     * @param webView
     * @param action
     */
    public static void clickOprate(WebView webView, String action) {

        Intent intent = new Intent(action);
        webView.getContext().startActivity(intent);

    }


    /**
     * 方法的重载
     *
     * @param view
     * @param val
     * @return
     */
    public static int overloadMethod(WebView view, int val) {
        return val;
    }

    public static String overloadMethod(WebView view, String val) {
        return val;
    }


    /**
     * 异步回调函数
     *
     * @param view
     * @param ms
     * @param backMsg
     * @param jsCallback
     */
    public static void delayJsCallBack(WebView view, int ms, final String backMsg, final JsCallback jsCallback) {
//        TaskExecutor.scheduleTaskOnUiThread(ms*1000, new Runnable() {
//            @Override
//            public void run() {
//                jsCallback.apply(backMsg);
//            }
//        });


        /**
         * 下面的代码不能用，报不在一个线程
         */
//        HandlerThread handlerThread = new HandlerThread("hotjsScope", HandlerThread.MAX_PRIORITY);
//
//        handlerThread.start();
//
//        Handler handler = new Handler(handlerThread.getLooper());
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //设置 可以回调多次
//                jsCallback.setPermanent(true);
//                try {
//                    jsCallback.apply(backMsg);
//                } catch (JsCallback.JsCallbackException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, ms * 1000);


/**
 * 报错
 * All WebView methods must be called on the same thread
 */
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    jsCallback.apply(backMsg);
//                } catch (JsCallback.JsCallbackException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


        /**
         * 报错
         * All WebView methods must be called on the same thread
         */
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    jsCallback.apply(backMsg);
//                } catch (JsCallback.JsCallbackException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 3000);


        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*
                设置能够被调用 类似 反射技术
                 */
                jsCallback.setPermanent(true);
                try {
                    //调用数据进行返回
                    jsCallback.apply(backMsg);
                } catch (JsCallback.JsCallbackException e) {
                    e.printStackTrace();
                }
            }
        }, 3000);
    }


}
