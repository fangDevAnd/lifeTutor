package com.xiaofangfang.consumeview.autoRollViewGroup;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

public class MyView extends ViewGroup {


    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        scroller = new Scroller(getContext());

    }

    Scroller scroller;

    VelocityTracker velocityTracker;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        velocityTracker = VelocityTracker.obtain();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int totalHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {

            View view = getChildAt(i);
            int height = view.getMeasuredHeight();
            Log.d("test", "onLayout:" + height);
            view.layout(l, t + totalHeight, r, b + totalHeight + height);
            totalHeight += height;
        }


    }

    int index;
    Timer timer;

    public void autoRun() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MyView.this.post(new Runnable() {
                    @Override
                    public void run() {


                        ObjectAnimator oa = ObjectAnimator.ofFloat(getChildAt(0),
                                "translationY",
                                -index * MyView.this.getMeasuredHeight(),
                                -(index + 1) * MyView.this.getMeasuredHeight());
                        oa.setInterpolator(new AccelerateDecelerateInterpolator());
                        oa.setDuration(1000);
                        oa.start();
                        index++;
                    }
                });

            }
        }, 2000, 2000);

    }


    int startX = 0;
    int defaultSpee = 100;
    int speed = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        boolean touch = false;
        int moveX;

        boolean isMove = false;

        velocityTracker.addMovement(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                startX = (int) event.getRawY();

                touch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getRawY();

                int distance = moveX - startX;


//                if (getScrollY() == 0) {//当内部控件的y轴的偏移量到父控件的头部。我们就不运行进行滑动
//
//
//                }


                Log.d("test", "distance=" + distance);

                scrollBy(0, -distance);

                startX = moveX;

                velocityTracker.computeCurrentVelocity(1000);

                Log.d("test", "onTouchEvent: " + Math.abs(velocityTracker.getYVelocity()));

                speed = (int) velocityTracker.getYVelocity();

                Log.d("test", "scrollY" + getScrollY());


                break;

            case MotionEvent.ACTION_UP:

//                Log.d("test", "滑动结束后的速度 =" + speed);
//                isMove = true;
//
//                while (isMove) {
//                    int s;
//                    s = speed / defaultSpee;
//                    if (speed > 0) {
//                        speed -= 100;
//                    } else {
//                        speed += 100;
//                    }
//                    SystemClock.sleep(10);
//                    scrollBy(0, -s);
//                    if (Math.ceil(s) == 0) {
//                        isMove = false;
//                    }
//
//                    Log.d("test", "s =" + s);
//                }


//                scroller.startScroll(0, getScrollY(), 0, getScrollY() + 200);

                Log.d("test", "scrollY" + getScrollY());


                break;

        }


        return true;
    }


    @Override
    public void computeScroll() {
        if (scroller != null) {
            if (scroller.computeScrollOffset()) {//判断scroll是否完成
                ((View) getParent()).scrollTo(scroller.getCurrX(), scroller.getCurrY());//执行本段位移
                invalidate();
                //进行下段位移
            }
        }

    }


}
