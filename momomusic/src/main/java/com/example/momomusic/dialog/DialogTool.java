package com.example.momomusic.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;;
import android.widget.TextView;

import com.example.momomusic.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StyleRes;

/**
 * 提供了一些定义好的dialog
 */
public abstract class DialogTool<T> implements DialogSet {


    private ViewGroup view;
    //1.操作成功
    //2,警告弹窗
    //3提示弹窗


    public ViewGroup getView() {
        return view;
    }

    private Context context;

    public Dialog getDialog(Context context, @LayoutRes int viewResId, T... t) {

        this.context = context;

        view = (ViewGroup) LayoutInflater.from(context).inflate(viewResId, null, false);

        Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        bindView(this, dialog, t);
        return dialog;
    }


    public abstract void bindView(DialogTool<T> d, Dialog dialog, T... t);

    @Override
    public void setText(int id, String text) {
        View view1 = view.findViewById(id);
        if (view1 instanceof TextView) {
            ((TextView) view1).setText(text);
        }

    }


    public void addView(int id, View view) {


    }


    @Override
    public void setClickListener(int id, View.OnClickListener click) {
        View view1 = view.findViewById(id);
        view1.setOnClickListener(click);
    }

    @Override
    public void setAsyncImage(int id, int res) {
        View view2 = view.findViewById(id);
        if (view2 instanceof ImageView) {
            ((ImageView) view2).setImageResource(res);
        }
    }

    public void setTextSize(int id, int size) {

        float realSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, context.getResources().getDisplayMetrics());
        View view2 = view.findViewById(id);
        if (view2 instanceof TextView) {
            ((TextView) view2).setTextSize(realSize);
        }
    }

    public void setVisibility(int id, int visiable) {
        View view1 = view.findViewById(id);
        view1.setVisibility(visiable);
    }


    /**
     * 打开一个dialog
     *
     * @param context       上下文
     * @param viewid        视图布局的id
     * @param cancelable    是否可以取消
     * @param isWidthScreen 是否是宽屏显示
     * @param position      显示的位置
     * @param style         动画的id
     */
    public Dialog openDialog(Context context, @LayoutRes int viewid, boolean cancelable, boolean isWidthScreen, int position, @StyleRes int style, @DrawableRes int drawableBg, T... t) {

        view = (ViewGroup) LayoutInflater.from(context).inflate(viewid, null, false);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context)
                .setView(view)
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
        bindView(this, dialog, t);
        return dialog;
    }


}
