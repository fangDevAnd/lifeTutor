package com.xiaofangfang.consumeview.BoundScrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ScrollView;

public class BoundScrollView extends ScrollView {
    public BoundScrollView(Context context) {
        this(context, null);
    }

    public BoundScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoundScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private int mMaxOverScrollY = 20;

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverScrollY, isTouchEvent);
    }

    private void initView() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        mMaxOverScrollY = (int) (displayMetrics.density * mMaxOverScrollY);
    }

    public void setmMaxOverScrollY(int mMaxOverScrollY) {
        this.mMaxOverScrollY = mMaxOverScrollY;
    }


}
