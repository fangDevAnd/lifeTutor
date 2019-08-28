package com.xiaofangfang.consumeview.EventIntercept;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyViewGroup extends ViewGroup {


    private static final String TAG = "test";

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 内部有一个TextView，是这个group的内部，边缘存在10的边距
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        this.setPadding(20, 20, 20, 20);
        /**
         *
         * 通过上边的代码发现，这个l,t,r,b并不一定是view的大小，只是内部的大小，不包括padding
         *
         *
         *
         */

        MyView textView = (MyView) getChildAt(0);
        textView.layout(l + 40, t + 40, r - 40, b - 40);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "ViewGroup--------------------->dispatchTouchEvent:\t\t" + super.dispatchTouchEvent(ev) + "\t\t" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "ViewGroup--------------------->onInterceptTouchEvent:\t\t" + super.onInterceptTouchEvent(ev) + "\t\t" + ev.getAction());
        return super.onInterceptTouchEvent(ev);//默认返回false
    }

    /**
     * 1.如果一个view拦截了摸个事件，那么下次事件分发将有本节点开始
     */


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "ViewGroup--------------------->onTouchEvent:\t\t" + super.onTouchEvent(event) + "\t\t" + event.getAction());
        return super.onTouchEvent(event);//默认去处理
    }
}
