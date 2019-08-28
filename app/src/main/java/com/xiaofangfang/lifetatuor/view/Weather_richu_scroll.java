package com.xiaofangfang.lifetatuor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.xiaofangfang.lifetatuor.model.weather.Cond;

public class Weather_richu_scroll extends HorizontalScrollView {


    private Context context;

    public Weather_richu_scroll(Context context) {
        this(context, null);
    }

    public Weather_richu_scroll(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Weather_richu_scroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    Scroller scroller;
    private void init() {
         scroller = new Scroller(context);

         //下面是添加两个视图进行测试
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        LinearLayout linearLayout= (LinearLayout) getChildAt(0);
        for(int i=0;i<linearLayout.getChildCount();i++){
            View view =linearLayout.getChildAt(i);
            MarginLayoutParams layoutParams= (MarginLayoutParams)
                    view.getLayoutParams();
            layoutParams.width=context.getResources().getDisplayMetrics().widthPixels;
            view.setLayoutParams(layoutParams);

        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int end = 0;
        int start = 0;

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                start= (int)getScrollX();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                end= (int)getScrollX();
               if(!scroller.isFinished()){
                   scroller.abortAnimation();
               }


                break;
            case MotionEvent.ACTION_UP:
                int offset=end-start;
                if(offset>0){//左滑动
                    if(offset>=this.getMeasuredWidth()/3){
                        //再移动坐标向左
                       scroller.startScroll(getScrollX(),0,
                               -(this.getMeasuredWidth()-offset),0);
                    }else{
                        scroller.startScroll(getScrollX(),0,offset,0);
                    }

                }else{//右滑动
                    if (-offset>=this.getMeasuredWidth()/3){
                        //再向右滑动
                        scroller.startScroll(getScrollX(),0,
                                this.getMeasuredWidth()-offset,0);
                    }else{
                        scroller.startScroll(getScrollX(),0,-offset,0);

                    }
                }
                break;
        }

        postInvalidate();

        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            postInvalidate();
        }
    }
}
