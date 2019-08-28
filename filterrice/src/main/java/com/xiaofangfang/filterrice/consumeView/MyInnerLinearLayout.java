package com.xiaofangfang.filterrice.consumeView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MyInnerLinearLayout extends LinearLayout {

    public MyInnerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isInter = false;
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.d("test", "MyInnerLinearLayout接收到Down");
                isInter = false;
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("test", "MyInnerLinearLayout接收到Move");

                isInter = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("test", "MyInnerLinearLayout接收到cancel");

                isInter = false;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("test", "MyInnerLinearLayout接收到up");
                break;

        }
        return isInter;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("test", "MyInnerLinearLayout节点子节点接收到onTouchEvent");
        boolean isInter = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("test", "MyInnerLinearLayout的Down");
                isInter = true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("test", "MyInnerLinearLayout的Move");
                isInter = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("test", "MyInnerLinearLayout的cancel");
                isInter = false;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("test", "MyInnerLinearLayout的up");
                isInter = false;
                break;
        }
        return isInter;
    }
}
