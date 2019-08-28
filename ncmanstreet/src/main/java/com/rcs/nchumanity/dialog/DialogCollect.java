package com.rcs.nchumanity.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Build;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

import com.rcs.nchumanity.R;
import com.rcs.nchumanity.ul.ParentActivity;

/**
 * 由于程序复用，定义的已经写好布局和实现的dialog
 */
public abstract class DialogCollect {


    /**
     * 显示一个警告dialog，使用的是android的原生实现
     *
     * @param title
     * @param message
     * @param context
     * @return
     */
    public static Dialog showWarnDialog(String title, String message, Context context, EnterProgress progress) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setPositiveButton("确定", (dialog, which) -> {
                    progress.onProgre(dialog, builder);
                    dialog.cancel();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.cancel();
                })
                .setMessage(message);
        return builder.create();

    }

    public interface EnterProgress {
        void onProgre(DialogInterface dialog, AlertDialog.Builder builder);
    }


    /**
     * 加载一个弹出窗口
     * 使用的是windowManager的实现，用户体验较为差劲
     * 实现的dialog为宽度为屏幕宽的实现
     * 建议实现下面的方法去实现
     * {@link #openDialog(Context, int, boolean, boolean, int, int, int)}
     * <p>
     * 同样实现的是全屏宽的dialog，但是集体的高度耗时需要自己去做设置，
     * 为了屏幕的适配，建议设置高度的时候做兼容
     * #{{@link com.fang.nchumanity.tool.DensityConvertUtil}} 进行像素的转换
     *
     * @param context
     * @param eventProgress
     * @param height
     * @param res
     * @param anim
     */
    public static void loadPop(Context context, EventProgress eventProgress, int height, @LayoutRes int res, @StyleRes int anim) {

        WindowManager windowManager = ((ParentActivity) context).getWindowManager();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) height, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_DITHER, PixelFormat.TRANSPARENT
        );
        layoutParams.windowAnimations = anim;
        layoutParams.gravity = Gravity.BOTTOM;
        View view = LayoutInflater.from(context).inflate(res, null, false);
        eventProgress.eventProgress(view);
        windowManager.addView(view, layoutParams);
        //设置背景变暗
    }


    /**
     * 打开一个dialog
     *
     * @param context       上下文对象，由于dialog需要运行环境，所以该context不能使用applicationContext，会报错，请注意
     * @param viewid        view的id
     * @param cancelable    是否可以被取消
     * @param isWidthScreen 是否是屏幕的宽度
     * @param position      位置 #{{@link Gravity}}
     * @param style         用来设置当前dialog的动画效果
     *                      <style name="windowanim" parent="android:Animation">
     *                      <item name="android:windowEnterAnimation">@anim/dialog_enter</item>
     *                      <item name="android:windowExitAnimation">@anim/dialog_exit</item>
     *                      </style>
     */
    public static void openDialog(Context context, @LayoutRes int viewid, boolean cancelable, boolean isWidthScreen, int position, @StyleRes int style, @DrawableRes int drawableBg) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context)
                .setView(viewid)
                .setCancelable(cancelable);

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        if (isWidthScreen) {
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            /**
             * 我们发现了,上面所说的是正确的,那么通过设置背景,就能将dectorView的确定大小确定下来
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                dialog.getWindow().getDecorView().setBackground(context.getResources().getDrawable(drawableBg));
            }
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) -2, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_DITHER, PixelFormat.RGBA_8888
        );
        layoutParams.gravity = position;
        layoutParams.windowAnimations = style;//小心使用的style错误
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
    }


    /**
     * 事件处理的接口
     */
    public interface EventProgress {

        void eventProgress(View view);

    }

    /**
     * 打开登录
     *
     * @param context
     * @return
     */
    public static Dialog openLoadDialog(Context context) {

        View view2 = LayoutInflater.from(context).inflate(R.layout.dialog_load, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.BottomDialog)
                .setView(view2);
        AlertDialog dialog = builder.create();
        return dialog;
    }


    public static <T extends CharSequence> Dialog openTipDialog(Context context, ClickListener listener, T... o) {

        return new AlertDialog.Builder(context)
                .setTitle(o[0])
                .setMessage(o[1])
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    listener.done(dialogInterface, i);
                }).setNegativeButton("取消", ((dialogInterface, i) -> dialogInterface.dismiss()))
                .create();
    }

    private ClickListener clickListener;
    
    public static interface ClickListener {
        void done(DialogInterface inter, int index);
    }


}
