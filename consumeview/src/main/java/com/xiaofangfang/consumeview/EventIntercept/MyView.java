package com.xiaofangfang.consumeview.EventIntercept;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class MyView extends AppCompatButton {
    private static final String TAG = "test";

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "View--------------------->onTouchEvent:\t\t" + super.onTouchEvent(event) + "\t\t" + event.getAction());
        return super.onTouchEvent(event);//默认false。也就是代表处理事件
    }

}
