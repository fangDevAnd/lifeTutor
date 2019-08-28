package com.xiaofangfang.filterrice.consumeView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class ButtonInter extends androidx.appcompat.widget.AppCompatButton{
    public ButtonInter(Context context) {
        super(context);
    }

    public ButtonInter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonInter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d("test", "子节点获得事件"+event.getAction());

        return super.onTouchEvent(event);
    }
}
