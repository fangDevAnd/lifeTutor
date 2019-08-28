package com.example.momomusic.tool;


import android.util.Log;

/**
 * 日志帮助类
 */
public class Looger {


    public static final int ERROR = 5, VERBOSE = 1, DEBUG = 2, INFO = 3, WARN = 4;

    public static int currentLevel = 2;
    public static String logTag = "test";

    public static void E(String value) {
        if (currentLevel >= ERROR) {
            Log.e(logTag, value);
        }
    }

    public static void V(String value) {
        if (currentLevel >= VERBOSE) {
            Log.e(logTag, value);
        }
    }

    public static void D(String value) {
        if (currentLevel >= DEBUG) {
            Log.e(logTag, value);
        }
    }

    public static void I(String value) {
        if (currentLevel >= INFO) {
            Log.e(logTag, value);
        }
    }

    public static void W(String value) {
        if (currentLevel >= WARN) {
            Log.e(logTag, value);
        }
    }


}
