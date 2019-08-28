package com.lyc.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast的工具类
 */
public class ToastUtil {

    public static void showTip(Context context,String tip){
        Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
    }
}
