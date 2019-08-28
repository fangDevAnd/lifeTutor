package com.xiaofangfang.consumeview.PageScrollView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;


/**
 * 这个view实现的是带有拉伸效果的viewGroup
 */


public class PageScrollView extends ViewGroup {
    private static final String TAG = "test";

    public PageScrollView(Context context) {
        this(context, null);
    }

    public PageScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    //代表是不是已经经过了测量
    boolean isChe = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            measureChildren(widthMeasureSpec, heightMeasureSpec);
        }
        if (!isChe) {
            primaryHeight = getMeasuredHeight();
            isChe = true;
        }
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
        marginLayoutParams.height = getChildCount() * primaryHeight;
        setLayoutParams(marginLayoutParams);
        //这里再一次的设置布局，会导致当前的函数
        Log.d(TAG, "\tmeasureHeight=" + getMeasuredHeight() + "\t\t" + primaryHeight);

    }

    //最初父布局的大小
    int primaryHeight;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            getChildAt(i).layout(l, i * primaryHeight, r, (i + 1) * primaryHeight);
        }

    }

    int startY;
    int moveY;


    //是不是需要更新当前的界面显示，也就是修复
    boolean isUpd = false;

    //当前的滚动距离
    int currentScroll;

    //总共的偏移
    int totalOffset;

    //滑动的距离
    int dy;

    //最大的偏移量
    int maxOffset = 50;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getY();
                break;

            case MotionEvent.ACTION_MOVE:

                moveY = (int) event.getY();
                dy = startY - moveY;

                if (getScrollY() < 0 && dy < 0) {
                    isUpd = true;
                    if (Math.abs(getScrollY()) >= maxOffset) {
                        executeAnim(true);
                        return true;
                    }
                }
                if (getScrollY() > getHeight() - primaryHeight && dy > 0) {
                    isUpd = true;
                    if (Math.abs(getScrollY() - (getHeight() + primaryHeight)) > maxOffset) {
                        executeAnim(false);//执行修复动画
                        return true;
                    }
                }
                scrollBy(0, dy);
                startY = moveY;
                break;

            case MotionEvent.ACTION_UP:

                if (isUpd) {//是否需要修正
                    if (getScrollY() < 0) {

                        executeAnim(true);

                    } else if (getScrollY() > getHeight() - primaryHeight) {//滚动句距离==父布局的高度-最后一屏幕的大小======>>处在最后一屏幕的位置
                        executeAnim(false);//执行修复动画
                    }
                    isUpd = false;
                }

                break;

        }
        return true;
    }


    public void executeAnim(boolean top) {
        if (top) {
            ObjectAnimator obj = ObjectAnimator.ofInt(PageScrollView.this, "scrollY", getScrollY(), 0);
            obj.setDuration(300);
            obj.start();
        } else {
            currentScroll = getScrollY();
            ObjectAnimator obj = ObjectAnimator.ofInt(
                    PageScrollView.this,
                    "scrollY",
                    currentScroll,
                    currentScroll - (primaryHeight - (getHeight() - getScrollY())));//记住，这里是绝对坐标值
            obj.setDuration(300);
            obj.start();
        }
    }


}
