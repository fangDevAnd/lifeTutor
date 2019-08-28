package com.example.componentasystemtest.scrollview.vertcalViewGroup;

import android.content.Context;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fang on 2018/6/7.
 * 这个类的作用是自定义一个ViewGroup测试 ScrollX和scrollY的值
 */

public class MyScrollViewGroup extends ViewGroup {


    public MyScrollViewGroup(Context context) {
        super(context);
    }

    public MyScrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量孩子节点的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;
        //设置自身的大小
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            height += view.getMeasuredHeight();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(l, i * view.getMeasuredHeight(), r, (1 + i) * view.getMeasuredHeight());
        }

    }

    int startY;
    int moveY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) event.getRawY();
                int offset = moveY - startY;
                scrollBy(0, -offset);
                Log.d("test", "getRawY=" + event.getRawY());
                Log.d("test", "ScrollY=" + getScrollY());
                startY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }
}
