package com.example.componentasystemtest.canvas.ViewGroup;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by fang on 2018/6/10.
 */

public class MyLinearViewGroup extends LinearLayout {
    private Button button;

    public MyLinearViewGroup(Context context) {
        this(context, null);
    }

    public MyLinearViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        button = (Button) getChildAt(0);
    }

    int startY;
    int moveY;
    int startX;
    int moveX;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("test", "事件触发");
        //在这里处理子view的触摸事件
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                moveY = (int) event.getY();

                int offsetY = moveY - startY;
                int offsetX = moveX - startX;

                int left = button.getLeft() + offsetX;
                int right = button.getRight() + offsetX;
                int top = button.getTop() + offsetY;
                int bottom = button.getBottom() + offsetY;
                button.layout(left, top, right, bottom);
                startY = moveY;
                startX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
