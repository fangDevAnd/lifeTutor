package com.xiaofangfang.rice2_verssion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.xiaofangfang.rice2_verssion.R;

import java.util.List;


/**
 * 水平滚动的视图显示效果，被ExpandableLayout内嵌
 */
public class HorizontalScrollMenuBar extends HorizontalScrollView {
    public HorizontalScrollMenuBar(Context context) {
        this(context, null);
    }

    public HorizontalScrollMenuBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollMenuBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private LinearLayout linearLayout;

    private void initView() {

        this.setPadding(10, 5, 10, 5);
        this.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.setHorizontalScrollBarEnabled(false);

        linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));

        this.addView(linearLayout);

    }


    int[] colorList = {

            R.color.orangle,
            android.R.color.black
    };


    public int px2dp(int value) {
        value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
        return value;
    }


    /**
     * 设置数据的列表
     *
     * @param list
     */
    public void setDataList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            Button button = new Button(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(px2dp(80), px2dp(35));
            layoutParams.rightMargin = px2dp(10);
            button.setLayoutParams(layoutParams);
            button.setText(list.get(i));
            button.setTextSize(11);
            button.setAllCaps(false);
            setSelectStatusStyle(isSelectes, button);
            hindOrDisOtherButton(isSelectes, linearLayout, button);
            button.setOnClickListener(new MyClickListener(button, i, linearLayout));
            linearLayout.addView(button);
        }
    }


    private void setSelectStatusStyle(boolean isSelectes, Button button) {
        if (isSelectes) {//选中
            button.setTextColor(getResources().getColor(colorList[0]));
            button.setBackgroundResource(R.drawable.all_radius_red_bg);
        } else {
            button.setBackgroundResource(R.drawable.all_radius_gray_bg);
            button.setTextColor(getResources().getColor(android.R.color.black));
        }
    }

    /**
     * 隐藏或显示其他按钮
     *
     * @param isSelectes
     * @param viewGroup
     * @param view
     */
    private void hindOrDisOtherButton(boolean isSelectes, LinearLayout viewGroup, View view) {
        int count = viewGroup.getChildCount();
        if (isSelectes) {
            for (int i = 0; i < count; i++) {
                if (viewGroup.getChildAt(i) != view) {
                    viewGroup.getChildAt(i).setVisibility(GONE);
                }
            }
        } else {

            for (int i = 0; i < count; i++) {
                if (viewGroup.getChildAt(i) != view) {
                    viewGroup.getChildAt(i).setVisibility(VISIBLE);
                }
            }
        }
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    boolean isSelectes = false;


    /**
     * 内部按钮的点击监听
     */
    public interface OnItemClickListener {
        void onItemClick(int position, View view, ViewGroup parentView);
    }


    class MyClickListener implements OnClickListener {

        private View view;
        private int position;
        private ViewGroup viewGroup;

        MyClickListener(View view, int position, ViewGroup viewGroup) {
            this.view = view;
            this.position = position;
            this.viewGroup = viewGroup;
        }


        @Override
        public void onClick(View v) {

            isSelectes = !isSelectes;
            //更改背景和颜色
            setSelectStatusStyle(isSelectes, (Button) v);
            hindOrDisOtherButton(isSelectes, (LinearLayout) viewGroup, view);
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position, view, viewGroup);
            }

        }
    }


}
