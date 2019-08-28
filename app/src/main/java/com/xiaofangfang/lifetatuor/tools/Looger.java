package com.xiaofangfang.lifetatuor.tools;

import android.util.Log;

public class Looger {


    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WRAN = 4;
    public static final int ERROR = 5;

    public static int currentLevel = DEBUG;
    public static String TAG = "test";

    public static void setTag(String tag){
        TAG=tag;
    }


    public static void v(String verbose) {
        if (currentLevel >= VERBOSE) {
            Log.d(TAG, verbose);
        }
    }

    public static void i(String info) {
        if (currentLevel >= INFO) {
            Log.d(TAG, info);
        }
    }

    public static void w(String warn) {
        if (currentLevel >= WRAN) {
            Log.d(TAG, warn);
        }
    }

    public static void d(String debug) {
        if (currentLevel >= DEBUG) {
            Log.d(TAG, debug);
        }
    }

    public static void e(String error) {
        if (currentLevel >= ERROR) {
            Log.d(TAG, error);
        }
    }


}
