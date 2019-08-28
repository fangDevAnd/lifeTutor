package com.example.momomusic.tool;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * 提供了对资源访问的便利
 */
public class ResourceUtil {


    public static String getString(int resId, Context context) {
        return context.getResources().getString(resId);
    }

    public static Drawable getDrawable(int resId, Context context) {
        return context.getResources().getDrawable(resId);
    }

}
