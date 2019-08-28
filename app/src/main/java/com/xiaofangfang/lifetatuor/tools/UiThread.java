package com.xiaofangfang.lifetatuor.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


public class UiThread {


    /**
     * 返回一个主线程运行的handler
     *
     * @param context
     * @return
     */
    public static Handler getUiThread(Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        return handler;
    }

}
