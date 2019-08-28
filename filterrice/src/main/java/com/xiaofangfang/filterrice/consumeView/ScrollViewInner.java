package com.xiaofangfang.filterrice.consumeView;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ScrollViewInner extends ScrollView {

    public ScrollViewInner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean intercept = false;

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.d("test", "拦截按下事件");
                intercept = false;
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("test", "拦截移动事件");
                intercept = false;
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.d("test", "拦截抬起事件");
                intercept = false;
                break;
        }
        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d("test", "事件被顶层消费"+super.onTouchEvent(event));
        boolean intercept = false;
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.d("test", "按下事件");
                intercept = false;
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("test", "移动事件");
                intercept = false;

                break;

            case MotionEvent.ACTION_CANCEL:
                Log.d("test", "抬起事件");
                intercept = false;
                break;
        }

        return false;
    }
}
