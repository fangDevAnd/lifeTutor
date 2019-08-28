package com.xiaofangfang.lifetatuor.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.ScreenTools;


/**
 * 这个类的作用是学习处理滑动冲突
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 首先  我们应该知道如何处理
     * 我们做下假设,我们在这里面判断华东的距离,如果滑动的距离大于1/2就拦截
     * 如果低于1/2就交给子view进行处理
     *
     * @param ev
     * @return
     */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int startx = 0;
        int endY;
        boolean isIntercept = false;
        Looger.d("事件被外层的viewPager拦截");

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startx = (int) ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                endY = (int) ev.getRawX();
                int offsetX = endY - startx;
                if (offsetX >= ScreenTools.getScreenDimension(getContext())[0] / 2) {
                    //如果滑动的距离大于屏幕的1/2,就去消费当前的事件
                    isIntercept = true;
                } else {
                    //反之不在消费,交给子view进行处理
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return isIntercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        Looger.d("事件被外层的viewPager消费");
        return super.onTouchEvent(ev);
    }
}
