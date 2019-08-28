package com.lyc.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.reflect.Method;

/**
 *
 * 协调方法的运行
 * 将异步方法，回调方法封装。
 *
 */
public class CoroutineUtil {

    private static Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            Object[] args = (Object[]) msg.obj;
            switch (msg.what) {

                case 1://
                    Thread t = (Thread) args[3];
                    t.interrupt();
                    Object target = args[0];
                    String callback = args[1].toString();
                    Object param = args[2];
                    executeMethod(target, callback, param);
                    break;
                default:
                    break;
            }
        }
    };

    private static Object executeMethod(Object target, String runMethodName) {
        return executeMethod(target, runMethodName, null);
    }


    private static Object executeMethod(Object target, String runMethodName,
                                        Object param) {
        Class<?> demo = null;
        Object res = null;
        Method method = null;
        try {

            demo = target.getClass();
            if (param != null) {
                method = demo.getDeclaredMethod(runMethodName, Object.class);
                method.setAccessible(true);
                res = method.invoke(target, param);
            } else {
                method = demo.getDeclaredMethod(runMethodName);
                res = method.invoke(target);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LogUtil.WriteLog(e);
        }
        return res;
    }

    public static void Start(final Object target, final String runMethodName,
                             final Object runParam, final String callbackMethod) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Object[] args = new Object[]{target, callbackMethod, null,
                        null};
                Object param = executeMethod(target, runMethodName, runParam);

                args[2] = param;
                args[3] = Thread.currentThread();
                mHandler.obtainMessage(1, args).sendToTarget();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    LogUtil.WriteLog("thread is break");
                }
            }
        }).start();
    }

    public static void Start(final Object target, final String runMethodName,
                             final String callbackMethod) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Object[] args = new Object[]{target, callbackMethod, null,
                        null};
                Object param = executeMethod(target, runMethodName);
                args[2] = param;
                args[3] = Thread.currentThread();
                mHandler.obtainMessage(1, args).sendToTarget();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    LogUtil.WriteLog("thread is break");
                }

            }
        }).start();
    }
}
