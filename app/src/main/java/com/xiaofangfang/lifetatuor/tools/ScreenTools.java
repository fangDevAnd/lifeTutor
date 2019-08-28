package com.xiaofangfang.lifetatuor.tools;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 屏幕参数的工具
 * 提供了对屏幕的参数的
 */
public class ScreenTools {


    /**
     * @param context
     * @return 返回屏幕的宽高
     */
    public static int[] getScreenDimension(Context context) {

        int[] a = new int[2];
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        a[0] = dm.widthPixels;
        a[1] = dm.heightPixels;
        return a;
    }





}
