package com.example.componentasystemtest.scrollview.consumeView;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by fang on 2018/6/7.
 */


public class MyView extends View {

    Scroller scroller;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(scroller.getCurrX(), scroller.getCurrX());
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            View view = (View) getParent();
            scroller.startScroll(view.getScrollX(), view.getScrollY(), -view.getScrollX(), -view.getScrollY());
            invalidate();
        }

        return super.onTouchEvent(event);
    }
}
