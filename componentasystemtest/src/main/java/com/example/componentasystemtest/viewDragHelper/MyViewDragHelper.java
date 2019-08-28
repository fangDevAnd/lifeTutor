package com.example.componentasystemtest.viewDragHelper;

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fang on 2018/6/7.
 */

public class MyViewDragHelper extends ViewGroup {

    ViewDragHelper viewDragHelper;
    MyCallBack myCallBack;

    public MyViewDragHelper(Context context) {
        this(context, null);
    }

    public MyViewDragHelper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewDragHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        myCallBack = new MyCallBack();
        viewDragHelper = ViewDragHelper.create(this, myCallBack);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    class MyCallBack extends ViewDragHelper.Callback {
        /**
         * 设置能够滑动的子view
         *
         * @param child
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return viewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }
}
