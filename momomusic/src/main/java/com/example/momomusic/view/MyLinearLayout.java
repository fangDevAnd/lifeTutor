package com.example.momomusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;


/**
 * 这个布局的作用是对对界面里面的image进行宽度的测量
 * 然后对其高度进行调整
 */
public class MyLinearLayout extends LinearLayout {

    private static final String TAG = "test";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        } else if (view instanceof ImageView) {
            MarginLayoutParams ml = (MarginLayoutParams) view.getLayoutParams();
            ml.height = view.getMeasuredWidth();
            view.requestLayout();
        } else {
            return;
        }

        if (viewInflater != null) {
            viewInflater.viewInflaterComplete();
        }
    }


    /**
     * view的解析的过程的回调接口
     */
    public interface ViewInflater {
        void viewInflating(View view);

        void viewInflaterComplete();
    }

    private ViewInflater viewInflater;


    public void setViewInflater(ViewInflater viewInflater) {
        this.viewInflater = viewInflater;
    }


}
