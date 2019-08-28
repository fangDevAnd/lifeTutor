package com.example.momomusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.momomusic.R;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class InterceptScrollView extends NestedScrollView {
    private static final String TAG = "test";

    public InterceptScrollView(@NonNull Context context) {
        this(context, null);
    }

    public InterceptScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InterceptScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getView(this);
    }

    public void getView(View view) {
        if (viewInflater != null) {
            viewInflater.viewInflating(view);
        }
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View view1 = ((ViewGroup) view).getChildAt(i);
                getView(view1);
            }
        } else if (view instanceof View) {
            //不做处理
        }

        if (viewInflater != null) {
            viewInflater.viewInflaterComplete();
        }
        return;
    }

    private MyLinearLayout.ViewInflater viewInflater;

    public void setViewInflater(MyLinearLayout.ViewInflater viewInflater) {
        this.viewInflater = viewInflater;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (eventProgress != null) {
            return eventProgress.eventProgress(event);
        } else {
            return super.onInterceptTouchEvent(event);
        }
    }


    private EventProgress eventProgress;

    public void setEventProgress(EventProgress eventProgress) {
        this.eventProgress = eventProgress;
    }

    public interface EventProgress {
        boolean eventProgress(MotionEvent event);
    }


}
