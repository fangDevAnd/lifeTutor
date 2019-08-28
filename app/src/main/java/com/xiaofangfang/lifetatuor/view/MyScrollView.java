package com.xiaofangfang.lifetatuor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.xiaofangfang.lifetatuor.tools.Looger;

import java.util.logging.Logger;

/**
 * 这个scrollView的作用是在
 * 我们在实现自定义视图天气的视图时,由于内层使用的是
 * VIewPager,我们发现我们在使用ScrollView嵌套
 * ViewPager时出现事件拦截事件,所以我们在这里重写这些事件机制
 * 处理滑动冲突
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    boolean intercept = false;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
         //我们判断是否是
        float startX = 0;
        float startY = 0;

        float moveX;
        float moveY;

        switch(ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                startX= (int) ev.getRawX();
                startY= (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX= (int) ev.getRawX();
                moveY= (int) ev.getRawY();

                float offsetY=moveY-startY;
                float offsetX=moveX-startX;

                if(offsetY<0){
                    if(offsetX<0){//上右滑动
                      if((-offsetY)>(-offsetX)){
                          intercept=true;//拦截事件
                      }else{
                          //代表的是不拦截事件
                          intercept=false;
                      }
                    }else{//上左滑动
                        if((-offsetY)>offsetX){
                            intercept=true;//拦截事件
                        }else{
                            //代表的是不拦截事件
                            intercept=false;
                        }
                    }
                }else { //offsetY>0

                    if(offsetX<0){//下右滑动
                        if(offsetY>(-offsetX)){
                            intercept=true;
                        }else {
                            intercept=false;
                        }
                    }else{//下左滑动
                            if(offsetY>offsetX){
                                intercept=true;
                            }else {
                                intercept=false;
                            }
                    }
                }

                Looger.d("是否拦截"+intercept);

                break;
        }
        return intercept;
    }


}
