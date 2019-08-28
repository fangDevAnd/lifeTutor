package com.xiaofangfang.consumeview.musicPlayHideSplitPage;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.xiaofangfang.consumeview.R;


/**
 * 当前的view是一个折叠的view
 * 我们重写的view是linearlayout
 * <p>
 * 下面是自己总结的一些问题
 * <p>
 * 对于是LienarLayoutt  如果内部的某个布局是不可见的，那么他的高度为0，并不是在下面显示，但由于外部高度不够不去显示，而是直接为0
 * <p>
 * <p>
 * 当对第一个占满全屏的视图进行高度的递减时。。会使得二个布局的高度自适应 ，当然前提是你的第二个布局使用的是match_parent布局设置
 */
public class SplitPageView extends LinearLayout {

    private static final String TAG = "test";

    public SplitPageView(Context context) {
        this(context, null);
    }

    public SplitPageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /**
     * 主布局
     */
    LinearLayout main;


    /**
     * 第二个界面实现
     */
    LinearLayout two;

    /**
     * 上下文对象
     */

    Context context;


    Toolbar toolbar, toolbar1;


    /**
     * 滚动布局
     */
    NestedScrollView scrollView;


    /**
     * 当前布局的外层布局的图片
     */
    ImageView imageView;

    /**
     * 下面触发的第二屏
     */
    TextView downBtn;


    /**
     * toolbar1上面用来显示当前音乐播放的图片
     * 上面的图片经过动画的操作后，就是缩放到下面的大小
     */
    ImageView music;


    public SplitPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        this.context = context;

        MIN_DRAG_DISTANCE = dp2px(100);

        padding = dp2px(4);

        mainWidth = getResources().getDisplayMetrics().widthPixels;

        //在这里进行初始化的操作
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }


    boolean scrollToTop = false;


    /**
     * 当前的toolbar的高度
     */
    private int toolbarHeight;

    /**
     * 0 :存放的是专辑的左边距离
     * 1，代表的是存放的是专辑宽度
     */
    private int[] musicImgInfo = new int[2];

    private float imgWidth;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        if (mainHeight == 0) {

            /**
             * 这里代表的是进行的初始化操作
             */
            two = findViewById(R.id.two);

            main = findViewById(R.id.main);

            toolbar1 = findViewById(R.id.toolbar1);

            scrollView = findViewById(R.id.scrollView);

            toolbar = findViewById(R.id.toolbar);

            music = findViewById(R.id.music);

            downBtn = findViewById(R.id.downBtn);

            downBtn.setOnClickListener((v) -> {
                startTwoAnim();
                inalidate();
            });


            imageView = ((ViewGroup) getParent()).findViewById(R.id.albumImg);

            imageView.post(() -> {//有值了
                imgWidth = imageView.getMeasuredWidth();
                Log.d(TAG, "onMeasure: " + imgWidth);
            });


            musicImgInfo[0] = music.getLeft();
            musicImgInfo[1] = music.getWidth();


            toolbarHeight = toolbar.getMeasuredHeight();//有值

//            Log.d(TAG, "onMeasure: "+toolbarHeight);

            scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    //判断滑动的方向

                    if (oldScrollY >= scrollY) {//当为0的时候 代表的是进行上滑动

                        if (scrollY == 0) {
                            //到达了顶部
                            Log.d(TAG, "onScrollChange: scrollview滚动到了顶部");

                            scrollToTop = true;
                        }
                    } else {
                        Log.d(TAG, "onScrollChange: scrollview离开了顶部");

                        scrollToTop = false;
                    }
                }
            });


            scrollView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {


                    switch (event.getAction()) {


                        case MotionEvent.ACTION_DOWN:


                            break;


                        case MotionEvent.ACTION_MOVE:


                            break;

                        case MotionEvent.ACTION_UP:

                            if (isTwoScreen && scrollToTop) {

                                Log.d(TAG, "onTouch: 需要滚动到第一屏");

                                final LayoutParams mainLayout = (LayoutParams) main.getLayoutParams();

                                final ValueAnimator va = ValueAnimator.ofInt(mainLayout.height, mainHeight);
                                va.setDuration(200);

                                //滚动的同时，我们需要设置toolbar的动画出现

                                Log.d(TAG, "onTouch: " + toolbarHeight);

                                ObjectAnimator oa = ObjectAnimator.ofFloat(toolbar, "translationY", -toolbarHeight, 0);
                                oa.setDuration(1000);


                                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        int value = (int) va.getAnimatedValue();
                                        mainLayout.height = value;
                                        main.requestLayout();
                                        imageView.setTranslationY(-mainHeight + value);
                                    }
                                });


                                //对专辑图片的位置进行恢复
                                AnimatorSet as = new AnimatorSet();

                                as.setInterpolator(new AccelerateDecelerateInterpolator());
                                as.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        Log.d(TAG, "onAnimationStart: ");
                                        isTwoScreen = false;
                                        toolbar1.setVisibility(GONE);
                                    }
                                });
                                as.playTogether(va, oa);
                                as.start();
                            }
                            break;
                    }
                    //代表的是将这个事件不进行消费 ，里面的view进行消费
                    return false;
                }
            });


            mainHeight = main.getMeasuredHeight();

        }

    }

    /**
     * 代表的是是否进行拦截
     * true代表的是拦截
     */
    boolean isIntercept = false;

    /**
     * 当前主界面的高度
     */
    int mainHeight;

    /**
     * 当前屏幕的宽度
     */
    int mainWidth;


    /**
     * 设置的最小的滑动距离
     */
    public final int MIN_DRAG_DISTANCE;


    /**
     * 将px转为  dp
     *
     * @param value
     * @return
     */
    public int dp2px(int value) {

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }


    /**
     * 事件的拦截机制
     * <p>
     * <p>
     * 采用的是外部拦截机制  ，也就是当前需要时间的时候进行拦截
     * <p>
     * <p>
     * 1.主界面向上滑动的时候拦截(如何判断是主界面  ？   如何判断下滑动？  )
     * <p>
     * 1.判断主界面   mai的height==当前的屏幕大小
     * 2.判断上滑动    moveY-startY <0
     * <p>
     * 2.scrollview滚动到最上面的时候拦截
     *
     * @param ev
     * @return
     */

    private int startY, moveY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {


            case MotionEvent.ACTION_DOWN:


                startY = (int) ev.getRawY();

                isIntercept = false;

                break;

            case MotionEvent.ACTION_MOVE:


                if (isTwoScreen) {
                    //第二屏的先关操作


                } else {


                    moveY = (int) ev.getRawY();

                    int offsetY = moveY - startY;

                    if (offsetY > 0) {

                        //代表的是上滑动
                        isIntercept = false;

                    } else {

                        //代表的是上滑动

                        if (main.getMeasuredHeight() == mainHeight) {
                            //代表的是处于主屏幕
                            isIntercept = true;
                        } else {
                            isIntercept = false;
                        }
                    }
                }

                break;


            case MotionEvent.ACTION_UP:


                isIntercept = false;
                break;
        }


        return isIntercept;
    }


    /**
     * 事件的处理
     */

    int moveY1, startY1;

    /**
     * 执行下滑动
     */

    boolean toDrop = false;


    boolean isTwoScreen = false;

    int padding;


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

//        toolbar1.setVisibility(GONE);

        switch (ev.getAction()) {


            case MotionEvent.ACTION_DOWN:
                startY1 = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                moveY1 = (int) ev.getRawY();

                int offsetY = moveY1 - startY1;

                Log.d(TAG, "onTouchEvent: " + offsetY);


                if (offsetY < 0) {//代表的是第一屏的上滑动

                    offsetY = Math.abs(offsetY);


                    if (offsetY > MIN_DRAG_DISTANCE) {

                        //不在触发
                        toDrop = true;

                    } else {
                        //触发滑动
                        LayoutParams mainLayout = (LayoutParams) main.getLayoutParams();

                        offsetY = offsetY >> 2;

                        mainLayout.height = (int) (mainHeight - offsetY);

                        main.requestLayout();

                        toDrop = false;

                    }
                } else {

                    //代表的是下滑动


                }


                break;
            case MotionEvent.ACTION_UP:


                Log.d(TAG, "onTouchEvent: 接受到事件");


                //判断是否达到了下滑动的要求

                Log.d(TAG, "onTouchEvent: toDrop" + toDrop + "isTwoScreen=" + isTwoScreen);

                if (isTwoScreen) {
                    //第二屏幕的手势的操作


                } else {

                    if (toDrop) {

                        startTwoAnim();

                    } else {

                        isTwoScreen = false;

                        //没有达到要求
                        //执行恢复现场的实现

                        final LayoutParams mainLayout = (LayoutParams) main.getLayoutParams();

                        final ValueAnimator va = ValueAnimator.ofInt(mainLayout.height, mainHeight);

                        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int value = (int) va.getAnimatedValue();
                                mainLayout.height = value;
                                main.requestLayout();
                            }
                        });
                        va.setDuration(200);
                        va.setInterpolator(new AccelerateDecelerateInterpolator());
                        va.start();
                    }

                }


                /**
                 * 进行现场的恢复
                 */

                inalidate();

                break;

        }


        /**
         * 将事件消费掉
         */
        return true;
    }


    public void inalidate() {
        moveY = 0;
        startY = 0;
        toDrop = false;
    }


    /**
     * 启动第二个页面的动画
     */
    public void startTwoAnim() {

        //达到了要求
        /**
         *
         * 进行隐藏one布局，看到第二个布局
         *
         */
        final LayoutParams mainLayout = (LayoutParams) main.getLayoutParams();

        final ValueAnimator va = ValueAnimator.ofInt(mainLayout.height, 0);
        va.setDuration(200);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) va.getAnimatedValue();
                mainLayout.height = value;
                main.requestLayout();

                imageView.setTranslationY(value - mainHeight);


                if (music.getMeasuredWidth() != 0 && musicImgInfo[1] == 0) {
                    musicImgInfo[1] = music.getMeasuredWidth();
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) music.getLayoutParams();
                    musicImgInfo[0] = music.getLeft() + lp.leftMargin;

                }

            }
        });

        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                /**
                 *toolbar1设置可见
                 */
                toolbar1.setVisibility(View.VISIBLE);
            }
        });


        //在这里我们设置进入第二屏幕的相关动画
        //toolbar的移入渐变动画


        ObjectAnimator oa = ObjectAnimator.ofFloat(toolbar1, "translationX", mainWidth, 0);

        ObjectAnimator oa1 = ObjectAnimator.ofFloat(toolbar1, "alpha", 0f, 1f);


        AnimatorSet a1 = new AnimatorSet();
        a1.setDuration(1000);
        a1.playTogether(oa, oa1);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(va, a1);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();


        /**
         * 设置scrollview滚动到顶部标志位
         */

        scrollToTop = true;


        /**
         * 设置我们滚动了到第二屏
         */
        isTwoScreen = true;
    }


}