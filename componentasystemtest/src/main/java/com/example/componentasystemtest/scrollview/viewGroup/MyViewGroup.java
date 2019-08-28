package com.example.componentasystemtest.scrollview.viewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by fang on 2018/6/6.
 */

public class MyViewGroup extends ViewGroup {

    Scroller mScroller;

    public MyViewGroup(Context context) {
        this(context, null);

    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        for (int i = 0; i < getChildCount(); i++) {
            View buttonView = getChildAt(i);
            //设置布局
            int height = buttonView.getMeasuredHeight();
            int width = buttonView.getMeasuredWidth();
            Log.d("test", "height=" + height);
            Log.d("test", "width=" + width);
            Log.d("test", "l=" + l + ";t=" + t);
            buttonView.layout(l, 0, width, height);
            //这里需要注意的是：button.layout()的值是相对父元素来说的，onlayout()中的参数又是当前的viewGroup在父亲节点的位置
            //所以你不能使用 buttonView.layout(l,t,width,height)来设置值，原因是，这个t很可能会很大，这时buttonView的top大于height，系统
            //都不知道你想干嘛。

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量一下孩子的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        Log.d("test", "height=" + height);
        Log.d("test", "width=" + width);

        setMeasuredDimension(width, height);
    }

    int startX;
    int moveX;
    int startY;
    int moveY;

    int endY;
    int endX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = (int) event.getRawX();
            startY = (int) event.getRawY();
            endX = startX;
            endY = startY;
            Log.d("test", "endX=" + endX + ";  endY=" + endY);
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            moveX = (int) event.getRawX();
            moveY = (int) event.getRawY();
            int offsetX = moveX - startX;
            int offsetY = moveY - startY;
            offsetLeftAndRight(offsetX);
            offsetTopAndBottom(offsetY);
            invalidate();
            startY = moveY;
            startX = moveX;
        }

        View view = getChildAt(0);

        if (event.getAction() == MotionEvent.ACTION_UP) {
            View viewGroup = ((View) getParent());
            Log.d("test", "调用");
            Log.d("test", "View的moveX" + moveX + "; scrollY" + moveY + "; ");
            mScroller.startScroll(this.getLeft(), this.getTop(), -this.getLeft(), -this.getTop());
            invalidate();
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX()
                    , mScroller.getCurrY());
            //通过重绘来不断的调用cmputeScroll
            invalidate();
        }
    }
}
