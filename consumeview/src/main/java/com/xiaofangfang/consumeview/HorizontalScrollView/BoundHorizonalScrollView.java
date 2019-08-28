package com.xiaofangfang.consumeview.HorizontalScrollView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 失败的案例  开始使用viewpager来实现
 */

/**
 * 实现的算是一种带有弹性布局效果的分页显示的水平滚动布局的实现
 *
 * <p>
 * <p>
 * 实现步骤
 * 1.添加一个顶层的布局 LInearLayout,设置
 */
public class BoundHorizonalScrollView extends HorizontalScrollView {
    private static final String TAG = "test";

    public BoundHorizonalScrollView(Context context) {
        this(context, null);
    }

    public BoundHorizonalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoundHorizonalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private List<View> list;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }


    private int containerWidth;
    private boolean isMeasure = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //在这里得到viewgroup的宽度和高度的值
        if (!isMeasure) {
            containerWidth = getMeasuredWidth();
            isMeasure = !isMeasure;
        }

        Log.d(TAG, "onMeasure: 当前container的宽度=" + containerWidth);
    }

    private LinearLayout ly;

    /**
     * 初始化布局
     */
    private void initView() {


        list = new ArrayList<>();
        ly = new LinearLayout(getContext());
        ly.setOrientation(LinearLayout.HORIZONTAL);
        ly.setPadding(containerLeftPadding, 0, 0, 0);
        this.addView(ly);
    }


    /**
     * 设置每一屏内部的padding
     */
    private int padding = 25;

    private int innerContainerWidth;

    private int containerLeftPadding = 3 * padding;


    public void setViewList(List<? extends View> list) {
        this.list = (List<View>) list;
        addViewList();
    }


    private void addViewList() {


        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!isMeasure) {
                }

                innerContainerWidth = containerWidth - 6 * padding;


                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < list.size(); i++) {
                            View view = list.get(i);

                            LinearLayout.MarginLayoutParams lp = new MarginLayoutParams(innerContainerWidth, -1);

                            ly.addView(view, lp);
                        }
                    }
                });

            }
        }).start();
    }


    private int startX;
    private int moveX;
    int offsetX;
    boolean isMove = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                moveX = (int) ev.getX();
                offsetX = moveX - startX;

                break;
            case MotionEvent.ACTION_UP:
                if (offsetX > 0) {//左滑动

                    if (offsetX < containerWidth / 3) {
                        if (!isMove) {
                            isMove = !isMove; //恢复现场
                            ValueAnimator va = ValueAnimator.ofInt(getScrollX(), getScrollX() + innerContainerWidth - offsetX);
                            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    BoundHorizonalScrollView.this.scrollTo((Integer) animation.getAnimatedValue(), 0);
                                }
                            });
                            va.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    isMove = !isMove;
                                }
                            });
                            va.setDuration(1000);
                            va.start();
                        }


                    } else {
                        //上翻页
                        if (!isMove) {
                            isMove = !isMove; //恢复现场
                            ValueAnimator va = ValueAnimator.ofInt(getScrollX(), getScrollX() - (innerContainerWidth - offsetX));
                            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    BoundHorizonalScrollView.this.scrollTo((Integer) animation.getAnimatedValue(), 0);
                                }
                            });
                            va.setDuration(1000);
                            va.start();
                            va.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    isMove = !isMove;
                                }
                            });
                        }

                    }
                } else if (offsetX < 0) {//右滑动

                }

                break;
        }
        return super.onTouchEvent(ev);
    }
}
