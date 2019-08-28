package com.rcs.nchumanity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.orhanobut.logger.Logger;

public class MyViewPager extends ViewPager {


    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //是否可以进行滑动
    private boolean isSlide = false;

    public void setSlide(boolean slide) {
        this.isSlide = slide;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSlide;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Logger.d("事件传递到了这里");
        return isSlide;
    }


}
