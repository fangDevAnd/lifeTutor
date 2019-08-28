package com.xiaofangfang.rice2_verssion.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.tool.Looger;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个自定义的空间一般用在搜索上边，本控件模仿了淘宝过滤条
 */
public class ExpandableLayout extends LinearLayout {


    public ExpandableLayout(Context context) {
        this(context, null);
    }

    public ExpandableLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private boolean isSelected = false;

    public void setDataResource(List<String> list) {

        int count = list.size();
        rawCount = count % horizontalButtonSize != 0
                ? count / horizontalButtonSize + 1 : count / horizontalButtonSize;

        Looger.D("当前被填充的孩子节点的数量=" + rawCount);

        for (int i = 0; i < rawCount; i++) {

            List<String> rawDataList = new ArrayList<>();
            for (int j = 0; j < horizontalButtonSize; j++) {
                if (rawCount - 1 == i) {//代表的最后一行
                    if (i * horizontalButtonSize + j + 1 > count) {//代表的是超出数据
                        break;
                    }
                }
                rawDataList.add(list.get(i * horizontalButtonSize + j));
            }
            HorizontalScrollMenuBar horizontalScrollMenuBar = new HorizontalScrollMenuBar(getContext());
            horizontalScrollMenuBar.setDataList(rawDataList);
            isSelected = horizontalScrollMenuBar.isSelectes;
            horizontalScrollMenuBar.setOnItemClickListener(
                    new HorizontalScrollMenuBar.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view, ViewGroup parentView) {
                            //内部已经实现了对该行其他控件的关闭，但是不能实现对同组的其他控件的关闭
                            Looger.D("当前布局的孩子数量" + viewContainer.getChildCount());
                            isSelected = !isSelected;
                            hindOrDisContainer(parentView, isSelected);
                            if (onItemClickListener != null) {
                                onItemClickListener.onClick(view, parentView,
                                        position, isSelected, title.getText().toString());
                            }
                        }
                    });


            LayoutParams layoutParams = new LayoutParams(-1, -2);
            layoutParams.topMargin = menuLineHeight;
            layoutParams.bottomMargin = menuLineHeight;
            viewContainer.addView(horizontalScrollMenuBar, layoutParams);
        }
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    /**
     * 获得当前过滤条的标题
     *
     * @return
     */
    public String getTitle() {
        return this.title.getText().toString();
    }

    /**
     * 行菜单的之间的空间高度
     */
    private int menuLineHeight = 5;

    /**
     * 隐藏或者显示空控件组
     */
    public void hindOrDisContainer(ViewGroup buttonParent, boolean isSelected) {
        int childCount = viewContainer.getChildCount();

        if (isSelected) {
            for (int i = 0; i < childCount; i++) {
                if (buttonParent.getParent() == viewContainer.getChildAt(i)) {
                    continue;
                }
                viewContainer.getChildAt(i).setVisibility(GONE);
            }
        } else {
            for (int i = 0; i < childCount; i++) {
                if (buttonParent.getParent() == viewContainer.getChildAt(i)) {
                    continue;
                }
                viewContainer.getChildAt(i).setVisibility(VISIBLE);
            }
        }
    }


    /**
     * 水平按钮的数量
     */
    private int horizontalButtonSize = 5;
    private ViewGroup viewContainer;
    private TextView title;
    private View splitLine;
    /**
     * 行的数量
     */
    private int rawCount;

    private void initView() {
        this.setOrientation(LinearLayout.VERTICAL);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.expandable_layout, null, false);

        viewContainer = view.findViewById(R.id.viewContainer);
        title = view.findViewById(R.id.title);

        splitLine = view.findViewById(R.id.splitLine);

        LayoutParams layoutParams = new LayoutParams(-1, -2);
        this.addView(view, layoutParams);

    }


    private OnItemClickListener onItemClickListener;


    /**
     * 设置item的点击事件的处理
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        /**
         * 定义一个item点击的监听
         *
         * @param view       点击的按钮
         * @param viewParent 被点击的view的parent 是一个线性布局
         * @param position   点击控件在父布局的位置，也就是在parent的位置
         * @param isSelected 是否被选中
         */
        void onClick(View view, ViewGroup viewParent, int position, boolean isSelected, String title);
    }


    /**
     * 下面使用两个方法来实现对下拉的图标的隐藏，以及对分割线的隐藏
     */

    public void dropIconVisibility(int status) {
        if (status == View.VISIBLE) {
            Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_down);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            title.setCompoundDrawables(null, null, drawable, null);
        } else if (status == View.INVISIBLE || status == View.GONE) {
            title.setCompoundDrawables(null, null, null, null);
        }
    }


    public void splitLineVisibility(int status) {
        if (status == View.VISIBLE) {

        } else if (status == View.GONE || status == View.INVISIBLE) {
            splitLine.setBackgroundResource(android.R.color.transparent);
        }

    }


    /**
     * 设置一行水平布局控件的数量
     *
     * @param horizontalButtonSize
     */
    public void setHorizontalButtonSize(int horizontalButtonSize) {
        this.horizontalButtonSize = horizontalButtonSize;
    }
}

