package com.xiaofangfang.consumeview.PageScrollView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 我们需要定义一个自定义的ScrollView,实现的效果是
 * 当我们进行下拉或者上拉现在的view的时候,会出现一种拉伸的感觉
 */
public class MyScrollView extends ScrollView {


    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    ViewGroup viewGroup;

    private void initView() {

        viewGroup = (ViewGroup) getChildAt(0);
        View head = new View(getContext());
//        head.setLayoutParams(new ViewGroup.LayoutParams(-1, 0));
        View foot = new View(getContext());
//        foot.setLayoutParams(new ViewGroup.LayoutParams(-1, 0));


        LinearLayout ll = new LinearLayout(getContext());
        ll.setLayoutParams(new ViewGroup.LayoutParams(-1, 0));
        ll.addView(head);

        LinearLayout ll1 = new LinearLayout(getContext());
        ll1.setLayoutParams(new ViewGroup.LayoutParams(-1, 0));
        ll1.addView(foot);


        viewGroup.addView(ll, 0);
        viewGroup.addView(ll1);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //在进行上一个view的计算的时候,也就是调用父类去计算子元素的高度,宽度,等,然后在前去添加一个头部一个尾部
        initView();
    }


    int startY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int moveY;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) ev.getY();
                int distance = moveY - startY;
//                Log.d("test", "distance=" + distance);
//                Log.d("test", "高度=" + getChildAt(0).getMeasuredHeight());
                rollProgress(distance);


                break;
            case MotionEvent.ACTION_UP:

                Log.d("test", "up action executed");

                final View view = viewGroup.getChildAt(viewGroup.getChildCount() - 1);
                final ViewGroup.LayoutParams ly = viewGroup.getLayoutParams();
                int totalHeight = ly.height;
                final ValueAnimator va = ValueAnimator.ofInt(totalHeight, 0);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        ly.height = value;
                        view.requestLayout();
                    }
                });
                va.setInterpolator(new AccelerateDecelerateInterpolator());
                va.start();
                break;
        }


        return super.onTouchEvent(ev);


    }


    private void rollProgress(int distance) {

        Log.d("test", "height" + (getScrollY() + getMeasuredHeight()) + "    " + viewGroup.getMeasuredHeight());
        if ((getScrollY() + getMeasuredHeight()) == viewGroup.getMeasuredHeight()) {
            //代表的是滑动到了最底部
            View view = viewGroup.getChildAt(viewGroup.getChildCount() - 1);
            ViewGroup.LayoutParams ly = viewGroup.getLayoutParams();
            ly.height++;
            viewGroup.requestLayout();
            Log.d("test", "view高度=" + ly.height);
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // Log.d("test", "change=" + l + " " + (t + getMeasuredHeight()) + " " + oldl + " " + oldt);
        //  Log.d("test", "scrollY" + (getScrollY() + getMeasuredHeight()));
        //上边的两个结果是一样的
        if ((t + getMeasuredHeight()) == getChildAt(0).getMeasuredHeight()) {
            //代表的是滑动到了最底部
        }

    }
}
