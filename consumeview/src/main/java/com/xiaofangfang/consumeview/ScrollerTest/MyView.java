package com.xiaofangfang.consumeview.ScrollerTest;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class MyView extends View {
    private static final String TAG = "test";

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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    Scroller scroller;


    public Scroller getScroller() {
        return scroller;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
            Log.d(TAG, "onDraw: 重绘制");
        }
    }
}
