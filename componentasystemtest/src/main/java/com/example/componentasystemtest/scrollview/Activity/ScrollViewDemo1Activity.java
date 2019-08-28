package com.example.componentasystemtest.scrollview.Activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Scroller;


import com.example.componentasystemtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现移动的7种方法
 */

public class ScrollViewDemo1Activity extends AppCompatActivity implements View.OnTouchListener {

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1);

        button = (Button) findViewById(R.id.move);
        button.setOnTouchListener(this);
        Scroller scroller = new Scroller(this);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //  useLayout(v, event);
        //    useOffsetLAndROrTAndB(v,event);
        //  useLayoutParam(v, event);
        //  useScrollToAndScrollBy(v, event);
        //  useScroller(v,event);
       // useAttributeAnimation(v, event);
//        useViewDragHelper(v,event);
        return true;
    }
    /**
     * 使用viewDragHelper
     *
     * @param v
     * @param event
     */
    private void useViewDragHelper(View v, MotionEvent event) {
    }


    /**
     * 使用属性动画
     *
     * @param v
     * @param event
     */
    private void useAttributeAnimation(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = (int) event.getRawX();
            startY = (int) event.getRawY();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            moveX = (int) event.getRawX();
            moveY = (int) event.getRawY();
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "translationX", startX, moveX);
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(v, "translationY", startY, moveY);
            List<Animator> animators = new ArrayList<>();
            animators.add(objectAnimator);
            animators.add(objectAnimator1);
            animatorSet.setDuration(30);
            animatorSet.playSequentially(animators);
            animatorSet.start();
            startX = moveX;
            startY = moveY;
            //在这里实现的话，还是有一些问题的，y轴的偏移很大，一时间我蒙蔽了，当然这里并不能够使用getY()和getX()来实现，原因说过了，单位时间的变化，
            //如果使用，会产生view乱抖。
        }
    }

    /**
     * 使用Scroller实现
     *
     * @param v     这个方法的实现要依赖重写viewGroup实现，
     * @param event
     */
    private void useScroller(View v, MotionEvent event) {


    }

    /**
     * 使用scrollBy和ScrollTo
     *
     * @param v
     * @param event
     */
    private void useScrollToAndScrollBy(View v, MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                startX = (int) event.getRawX();
                startY = (int) event.getRawY();

                Log.d("test", "action_down正在调用" + startX + "y的位置为：" + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getRawX();
                moveY = (int) event.getRawY();

                int offsetY = moveY - startY;
                int offsetX = moveX - startX;
                Log.d("test", "action_move正在调用" + moveX + "y的位置为：" + moveY);
                v.scrollBy(-offsetX, -offsetY);

                //当使用的是rawX或rawY时使用下面的代码
                startY = moveY;
                startX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }


    }

    /**
     * 使用布局参数实现
     *
     * @param v
     * @param event
     */
    private void useLayoutParam(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                startX = (int) event.getX();
                startY = (int) event.getY();
                Log.d("test", "action_down正在调用" + startX + "y的位置为：" + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                moveY = (int) event.getY();

                int offsetY = moveY - startY;
                int offsetX = moveX - startX;

                ViewGroup.MarginLayoutParams marginLayoutParam = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                marginLayoutParam.leftMargin = v.getLeft() + offsetX;

                marginLayoutParam.topMargin = v.getTop() + offsetY;

                v.setLayoutParams(marginLayoutParam);

                //当使用的是rawX或rawY时使用下面的代码
//                startY=moveY;
//                startX=moveX;
            case MotionEvent.ACTION_UP:
                break;
        }
    }


    /**
     * 使用上下，左右的偏移实现
     *
     * @param v
     * @param event
     */
    private void useOffsetLAndROrTAndB(View v, MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                moveY = (int) event.getY();

                int offsetY = moveY - startY;
                int offsetX = moveX - startX;

                v.offsetLeftAndRight(offsetX);
                v.offsetTopAndBottom(offsetY);
                //当使用的是rawX或rawY时使用下面的代码
//                startY=moveY;
//                startX=moveX;
            case MotionEvent.ACTION_UP:
                break;
        }
    }


    int startX;
    int startY;
    int moveX;
    int moveY;

    /**
     * 使用layout方法实现
     *
     * @param v
     * @param event
     */
    private void useLayout(View v, MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                startX = (int) event.getX();
                startY = (int) event.getY();
                Log.d("test", "action_down正在调用" + startX + "y的位置为：" + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                moveY = (int) event.getY();
                Log.d("test", "action_move正在调用" + moveX + "y的位置为：" + moveY);
                int offsetY = moveY - startY;
                int offsetX = moveX - startX;

                int newLeft = v.getLeft() + offsetX;
                int newRight = v.getRight() + offsetX;
                int newTop = v.getTop() + offsetY;
                int newBottom = v.getBottom() + offsetY;
                v.layout(newLeft, newTop, newRight, newBottom);

                Log.d("test", "l:" + newLeft + ";t:" + newTop + ";r" + newRight + ";b:" + newBottom);
                //当使用的是rawX或rawY时使用下面的代码
//                startY=moveY;
//                startX=moveX;
            case MotionEvent.ACTION_UP:
                break;
        }
    }


}
