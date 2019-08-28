package com.example.componentasystemtest.canvas.ViewGroup;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by fang on 2018/6/10.
 */

public class MyViewGroup extends LinearLayout {


    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
    //    super.dispatchDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
