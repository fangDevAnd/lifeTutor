package com.example.momomusic.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.momomusic.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;


/**
 * 功能实现了，但是存在一部分的bug.不提供使用
 */
@Deprecated

public class HorizonalScrollMenu extends LinearLayout implements View.OnTouchListener {
    private static final String TAG = "test";

    public HorizonalScrollMenu(Context context) {
        this(context, null);
    }

    public HorizonalScrollMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizonalScrollMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setPadding(padding, padding, padding, 5);

        translationXs = new ArrayList<>();

        initView();
    }


    int padding = 20;

    int width = 50;
    int height = 10;

    private List<String> datas;

    private HorizontalScrollView horizonView;
    private View intecator;


    public void initView() {
        horizonView = (HorizontalScrollView) LayoutInflater.from(getContext()).inflate(R.layout.view_scrollview, null);
        horizonView.setHorizontalScrollBarEnabled(false);
        horizonView.setOnTouchListener(this);


        this.addView(horizonView);
        intecator = new View(getContext());
        intecator.setBackgroundResource(R.color.colorAccent);
        this.addView(intecator, width, height);
        ll = (LinearLayout) ((ViewGroup) horizonView.getChildAt(0));
        changeText(defaultIndex);

    }

    private List<Integer> translationXs;

    private int defaultIndex = 0;

    private LinearLayout ll;

    /**
     * 上面我们使用addView，会导致下面的代码被调用addView使用的次数
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (int i = 0; i < ll.getChildCount(); i++) {
            TextView textView = (TextView) ll.getChildAt(i);
            textView.setOnClickListener(new ClickListener(i));
            int width = textView.getMeasuredWidth();
            translationXs.add(width);
        }
        if (isTouch) {

        } else {
            setIntecatorTranslationX(defaultIndex, false);
        }
    }

    private boolean isOpen = false;

    public void setIntecatorTranslationX(int index, boolean isMooth) {

        int sum = 0;

        for (int i = 0; i <= index; i++) {
            sum += translationXs.get(i);
        }

        sum -= translationXs.get(index) / 2;
        sum -= width / 2;
        if (isMooth) {//进行动画移动
            if (!isOpen) {
                isOpen = !isOpen;
                ObjectAnimator obj = ObjectAnimator.ofFloat(intecator, "translationX", sum);
                obj.setDuration(200);
                obj.start();
                obj.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isOpen = !isOpen;
                    }
                });
            }
        } else {//直接移动
            intecator.setTranslationX(sum);
        }
    }

    boolean isTouch = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        isTouch = true;
        Log.d(TAG, "onTouch: " + v.getScrollX() + "\t\t" + intecator.getTranslationX());

        MarginLayoutParams ml = (MarginLayoutParams) intecator.getLayoutParams();
        ml.leftMargin = -v.getScrollX();
        intecator.setLayoutParams(ml);


        return false;
    }


    private int oldIndex = -1;

    class ClickListener implements View.OnClickListener {

        private int index;

        public ClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            setIntecatorTranslationX(index, true);
            defaultIndex = index;
            changeText(index);
            if (clickItem != null) {
                clickItem.onClickItem(v, defaultIndex);
            }
        }
    }

    private ClickItem clickItem;


    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public interface ClickItem {
        void onClickItem(View view, int index);
    }


    public void changeText(int index) {
        if (oldIndex >= 0) {
            TextView textView1 = (TextView) ll.getChildAt(oldIndex);
            textView1.setTextSize(16);
            textView1.setTextColor(Color.GRAY);
        }

        TextView tv = (TextView) ll.getChildAt(index);
        tv.setTextSize(17);
        tv.setTextColor(Color.BLACK);
        oldIndex = defaultIndex;
    }
}
