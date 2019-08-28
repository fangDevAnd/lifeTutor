package com.xiaofangfang.filterrice.tool;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class MyListVIew extends ListView {
    public MyListVIew(Context context) {
        super(context);
    }

    public MyListVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        int startY = 0;
        int moveY;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startY = getSelfScrollY();

                break;

            case MotionEvent.ACTION_MOVE:

                moveY = getSelfScrollY();

                Log.d("test", "startY=" + startY + "moveY=" + moveY);

                if (getSelfScrollY() == 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:

        }

        return true;
    }


    public int getSelfScrollY() {
        View c = this.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = this.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }


}
