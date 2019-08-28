package com.xiaofangfang.rice2_verssion.tool;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {


    public static int[] getScreenDimension(Context context) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int[] val = {
                displayMetrics.widthPixels,
                displayMetrics.heightPixels
        };
        return val;
    }


    public static SharedPreferences getSystemSeeting(Context context) {
        return context.getSharedPreferences(SystemSet.settingFileName, Context.MODE_PRIVATE);
    }


    /**
     * 这个方法实现的是传递相对应的view设置对应的compoundDrwable的颜色
     *
     * @param color
     * @param t
     * @param <T>
     */
    public static <T extends TextView> void setDrawableColor(int color, T t) {
        Drawable[] dws = t.getCompoundDrawables();
        for (Drawable dw : dws) {
            if (dw != null) {
                dw.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
        }
    }


    /**
     * 将px转为  dp
     *
     * @param value
     * @param context
     * @return
     */
    public static int px2dp(int value, Context context) {

        int value1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
        return value1;
    }


    public static Dialog showWarnDialog(String title, String message, Context context) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setNegativeButton("确定", (dialog, which) -> {

                    dialog.cancel();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.cancel();
                })
                .setMessage(message);
        return builder.create();

    }


    public static void loadPop(Context context, EventProgress eventProgress, @LayoutRes int res) {

        WindowManager windowManager = ((ParentActivity) context).getWindowManager();

        //设置弹出的窗口的高度的值为屏幕大小的3/4
        float height = context.getResources().getDisplayMetrics().heightPixels / 2;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) height, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_DITHER, PixelFormat.TRANSPARENT
        );
        layoutParams.windowAnimations = R.style.windowanim;
        layoutParams.gravity = Gravity.BOTTOM;
        View view = LayoutInflater.from(context).inflate(res, null, false);
        eventProgress.eventProgress(view);
        windowManager.addView(view, layoutParams);
        //设置背景变暗
    }


    public interface EventProgress {

        void eventProgress(View view);

    }


    public String getFilterCondition(String value) {

        String[] v = value.split("&");
        if (v.length == 1) {//代表的是当前过滤条件为单条


        }


        return value;
    }


    /**
     * 获得软件的版本
     *
     * @return
     */
    public static long getSoftWareVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        long versionCode;
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            versionCode = pi.getLongVersionCode();
        } else {
            versionCode = pi.versionCode;
        }
        return versionCode;
    }

    /**
     * "HH:mm
     *
     * @param param
     * @return
     */
    public static String dateFormat(String param) {
        SimpleDateFormat sdf = new SimpleDateFormat(param);
        Date date = new Date(System.currentTimeMillis());
        String value = sdf.format(date);
        return value;
    }


    public static void drawableChange(@DrawableRes int imgRes, Context context, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(imgRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }


}
