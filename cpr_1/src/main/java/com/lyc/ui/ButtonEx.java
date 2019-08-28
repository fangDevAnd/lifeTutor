package com.lyc.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * 对按钮进行了扩展
 * 实现的是，按下透明度的变化
 *
 *
 * 使用 setBackground(new SelectorDrawable())可以轻易实现
 *
 * 上一个代码开发人员是傻逼
 *
 *
 *
 *
 */
@SuppressLint("AppCompatCustomView")
public class ButtonEx extends Button {
    public ButtonEx(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.getBackground().setAlpha(100);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getBackground().setAlpha(255);
                }

                return false;
            }
        });
    }
}
