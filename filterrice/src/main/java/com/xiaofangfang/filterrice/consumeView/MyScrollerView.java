package com.xiaofangfang.filterrice.consumeView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollerView extends ScrollView {
    public MyScrollerView(Context context) {
        super(context);
    }

    public MyScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 对拦截的事件进行处理
     * ，判断scooler是否已经移动到了底部，如果移动到了底部，
     * 就不在拦截事件，反之拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        View contentView = getChildAt(0);

        if (contentView.getMeasuredHeight() == getScrollY() + getHeight()) {

            Log.d("test", "滑动到了底部");
            return false;

        } else {

            return true;
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(0, 0);
    }
}
