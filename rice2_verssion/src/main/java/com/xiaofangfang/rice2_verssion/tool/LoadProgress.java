package com.xiaofangfang.rice2_verssion.tool;

import android.content.Context;
import android.graphics.PixelFormat;

import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.ProgressBar;

public class LoadProgress {


    /**
     * 加载框
     *
     * @param context
     */
    public static ProgressBar loadProgress(Context context) {
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setInterpolator(new BounceInterpolator());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 0, 0, PixelFormat.TRANSPARENT);
        layoutParams.flags =
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        layoutParams.gravity = Gravity.CENTER;
        WindowManager wm = ((AppCompatActivity) context).getWindowManager();
        wm.addView(progressBar, layoutParams);
        return progressBar;
    }


    /**
     * 移除加载框
     *
     * @param context
     */
    public static void removeLoadProgress(Context context, ProgressBar progressBar) {
        WindowManager wm = ((AppCompatActivity) context).getWindowManager();
        wm.removeViewImmediate(progressBar);
    }


}
