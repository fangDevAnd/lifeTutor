package com.example.componentasystemtest.canvas.MainActivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.componentasystemtest.R;
import com.example.componentasystemtest.canvas.View.SizeChange;

/**
 * Created by fang on 2018/6/7.
 */

public class Activity extends AppCompatActivity implements View.OnTouchListener {

    private SizeChange sizeChange;
    private LinearLayout content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo15);

        sizeChange = (SizeChange) findViewById(R.id.changeView);
        content = (LinearLayout) findViewById(R.id.content);
        sizeChange.setOnTouchListener(this);


    }


    int startX;
    int moveX;
    int startY;
    int moveY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("test", "触发的是事件回调");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                Log.d("test", "起始的位置x=" + startX + ";    y=" + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                moveY = (int) event.getY();
                Log.d("test", "起始的位置x=" + moveX + ";    y=" + moveY);
                int offsetY = moveY - startY;
                int offsetX = moveX - startX;
                int left = content.getLeft() + offsetX;
                int right = content.getRight() + offsetX;
                int top = content.getTop() + offsetY;
                int bottom = content.getBottom() + offsetY;
                content.layout(left, top, right, bottom);
                startX = moveX;
                startY = moveY;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("test", "触发的是监听");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                Log.d("test", "起始的位置x=" + startX + ";    y=" + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                moveY = (int) event.getY();
                Log.d("test", "起始的位置x=" + moveX + ";    y=" + moveY);
                int offsetY = moveY - startY;
                int offsetX = moveX - startX;
                int left = content.getLeft() + offsetX;
                int right = content.getRight() + offsetX;
                int top = content.getTop() + offsetY;
                int bottom = content.getBottom() + offsetY;
                content.layout(left, top, right, bottom);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    /**
     * 使用布局参数进行测试
     * 发现这种方法是可行的 ，
     * 这种方法在调用过程中是没有办法实现相应的布局
     */
    private void layoutParams() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) sizeChange.getLayoutParams();
        marginLayoutParams.setMargins(50, 50, 350, 250);
        sizeChange.setLayoutParams(marginLayoutParams);
    }
    /**
     * 这种方法可以实现，具体的细节可能与view节点树创建有关系
     * 默认情况下的大小
     * width :300dp
     * height:200dp
     */
    private void layoutTest() {
        sizeChange.layout(50, 50, 350, 250);
        sizeChange.invalidate();
    }


}
