package com.example.componentasystemtest.scrollview.viewGroup;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by fang on 2018/6/10.
 */

public class MyLinearViewGroup extends LinearLayout {
    public MyLinearViewGroup(Context context) {
        super(context);
    }

    public MyLinearViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //在这里处理子view的触摸事件




        return true;

    }
}
