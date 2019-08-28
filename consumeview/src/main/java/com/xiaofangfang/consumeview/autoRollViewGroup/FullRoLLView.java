package com.xiaofangfang.consumeview.autoRollViewGroup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/***
 * 自动滚动的控件，可以实现控件的上下自动滚动
 * 里面必须使用一个LInearLayout布局，
 */
public class FullRoLLView extends ViewGroup {


    public FullRoLLView(Context context) {
        super(context);
    }


    public FullRoLLView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(List<? extends String> strings) {

        /**
         * 内部使用了一个LInearlayout，这个实现的遍历数组，实现数据的更新
         */
        LinearLayout ly = (LinearLayout) getChildAt(0);

        for (int i = 0; i < ly.getChildCount(); i++) {

            TextView textView = (TextView) ly.getChildAt(i);

            textView.setText(strings.get(i));
        }
    }

    int totalHeight;
    int value;


    int minWidth = 400;
    int minHeight = 100;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //在这里进行测量，使之能够实现对屏幕适配

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        switch (modeWidth) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:

                width = Math.min(minWidth, sizeWidth);
                break;
            case MeasureSpec.EXACTLY:
                width = sizeWidth;
                break;
        }

        switch (modeHeight) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:

                height = Math.min(minHeight, sizeHeight);
                break;
            case MeasureSpec.EXACTLY:
                height = sizeHeight;
                break;
        }
        setMeasuredDimension(width, height);


        LinearLayout ly = (LinearLayout) getChildAt(0);
        value = ly.getChildCount();
        ly.setLayoutParams(new LayoutParams(width, totalHeight = value * getMeasuredHeight()));


    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        LinearLayout ly = (LinearLayout) getChildAt(0);
        ly.layout(l, t, r, totalHeight);

    }

    private Timer timer;
    int index = 0;

    public void autoRun(long delay) {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                FullRoLLView.this.post(new Runnable() {
                    @Override
                    public void run() {

                        ObjectAnimator oa = null;

                        if (index == -1) {

                            oa = ObjectAnimator.ofFloat(getChildAt(0),
                                    "translationY", -index * FullRoLLView.this.getMeasuredHeight(), 0
                            );

                        } else {
                            oa = ObjectAnimator.ofFloat(getChildAt(0),
                                    "translationY",
                                    -index * FullRoLLView.this.getMeasuredHeight()
                            );
                        }
                        oa.setDuration(1000);
                        oa.setInterpolator(new LinearInterpolator());
                        oa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                //在index >= value的时候
                            }
                        });
                        oa.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (index >= value) {

                                    index = -1;
                                    getChildAt(0).setTranslationY(-index * FullRoLLView.this.getMeasuredHeight());
                                    //下面是一个更好的实现方案
                                }
                            }
                        });
                        oa.start();
                        index++;
                    }
                });
            }
        }, 0, delay);
    }
}
