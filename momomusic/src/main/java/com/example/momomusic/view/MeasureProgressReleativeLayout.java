package com.example.momomusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * 这个方法的作用是提供了对视图解析过程的的回调
 * 解决控件调用getHeight或者调用getMeasureHeight()没有数据的情况
 */
public class MeasureProgressReleativeLayout extends RelativeLayout {
    public MeasureProgressReleativeLayout(Context context) {
        super(context);
    }

    public MeasureProgressReleativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureProgressReleativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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


}
