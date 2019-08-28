package com.example.momomusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class InterceptViewPager extends ViewPager {
    public InterceptViewPager(@NonNull Context context) {
        super(context);
    }

    public InterceptViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (eventDispatch != null) {
            eventDispatch.eventDispatch(event);
        }

        return super.dispatchTouchEvent(event);
    }


    public interface EventDispatch {
        void eventDispatch(MotionEvent event);
    }

    private EventDispatch eventDispatch;


    public void setEventDispatch(EventDispatch eventDispatch) {
        this.eventDispatch = eventDispatch;
    }


}
