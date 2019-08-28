package com.xiaofangfang.consumeview.EventIntercept;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.xiaofangfang.consumeview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 关于事件拦截的测试代码
 */
public class EventInterceptActivity extends AppCompatActivity {


    private static final String TAG = "test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_intercept);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Activity--------------------->dispatchTouchEvent: \t\t" + super.dispatchTouchEvent(ev) + "\t\t" + ev.getAction());//false
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "Activity--------------------->onTouchEvent:\t\t" + super.onTouchEvent(event) + "\t\t" + event.getAction());//false
        return super.onTouchEvent(event);
    }


    /**
     public static final int ACTION_DOWN             = 0;
     public static final int ACTION_UP               = 1;
     public static final int ACTION_MOVE             = 2;
     public static final int ACTION_CANCEL           = 3;
     */


}
