package com.lyc.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * @author fang
 * @date 2019/7/19
 *
 *
 * this is logcat help util
 */

public class LogUtil {
    /**
     * 获得错误信息的任务栈
     *
     * @param e
     * @param length
     * @return
     */
    public static String getErrorStack(Exception e, int length) {
        String error = null;
        if (e != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                e.printStackTrace(ps);
                error = baos.toString();
                if (length > 0) {
                    if (length > error.length()) {
                        length = error.length();
                    }
                    error = error.substring(0, length);
                }
                baos.close();
                ps.close();
            } catch (Exception e1) {
                error = e.toString();
            }
        }
        /*
         * try{ String str=new String(error.getBytes("ISO-8859-1"),"GBK");
         * return str; }catch(Exception e1) { e1.printStackTrace(); }
         */
        return error;
    }

    public static String getErrorStack(Exception e) {
        return getErrorStack(e, 0);

    }

    public static void WriteLog(Exception e) {
        Log.v("customMessage", getErrorStack(e));
    }

    public static void WriteLog(String msg) {
        Log.v("customMessage", msg);
    }

    public static void WriteLog(String Tag, Exception e) {
        Log.v("customMessage" + Tag, getErrorStack(e));
    }

    public static void WriteLog(String Tag, String msg) {
        Log.v("customMessage" + Tag, msg);
    }

    public static void WriteLog(String Tag, Object msg) {
        Log.v("customMessage" + Tag, msg.toString());
    }

}
