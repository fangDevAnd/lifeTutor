package com.example.componentasystemtest.popupWindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.componentasystemtest.R;


/**
 * 这个实现的是弹出的winddow，从下面弹出的，有点向下面弹出的菜单
 */
public class PopupWindows extends PopupWindow {


    public PopupWindows(Context mContext, View parent, final int pth) {
        View view = View.inflate(mContext, R.layout.item_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//设置宽
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);//设置高
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();//跟新popupwindows
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}