package com.xiaofangfang.rice2_verssion.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xiaofangfang.rice2_verssion.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤条件的tar，软件本身已经定义了这样的bar，但是实在是样式太丑， 过滤的是销量和价格
 */
public class FilterConditionNewBar extends LinearLayout {
    public FilterConditionNewBar(Context context) {
        this(context, null);
    }

    public FilterConditionNewBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterConditionNewBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private TextView filterOne, filterTwo, filterThree;


    private int[] saleDrawable = {
            R.drawable.ic_arrow_down,
            R.drawable.ic_arrow_up
    };


    /**
     * 当前的销量的排序方式是否是降序排雷
     */
    public boolean currentSaleSortAsc = true;

    /**
     * 当前的价格排序是否是降序排列
     */
    public boolean currentPriceSortAsc = true;


    private List<View> viewList = new ArrayList<>();

    /**
     *
     */
    private void initView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.filter_condition_new_bar, null, false);
        filterOne = view.findViewById(R.id.filterOne);//销量
        filterTwo = view.findViewById(R.id.filterTwo);//价格
        filterThree = view.findViewById(R.id.filterThree);//过滤
        setIconForPrice();
        setIconForSale();

        viewList.add(filterOne);
        viewList.add(filterTwo);
        viewList.add(filterThree);

        for (View view1 : viewList) {

            view1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.filterOne://销量
                            currentSaleSortAsc = !currentSaleSortAsc;
                            setIconForSale();
                            if (filterOptionClickListner != null) {
                                filterOptionClickListner.onSaleClick(v, currentSaleSortAsc);
                            }
                            break;
                        case R.id.filterTwo://价格
                            currentPriceSortAsc = !currentPriceSortAsc;
                            setIconForPrice();
                            if (filterOptionClickListner != null) {
                                filterOptionClickListner.onPriceClick(v, currentPriceSortAsc);
                            }
                            break;
                        case R.id.filterThree://过滤
                            if (filterOptionClickListner != null) {
                                filterOptionClickListner.onFilterCondition(v);
                            }
                            break;

                    }
                }
            });

        }

        filterThree = view.findViewById(R.id.filterThree);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        this.addView(view, layoutParams);


    }


    private void setIcon(TextView view, int index) {
        Drawable drawable = getResources().getDrawable(saleDrawable[index]);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(null, null, drawable, null);
    }

    private void setIconForPrice() {
        if (currentPriceSortAsc) {
            setIcon(filterTwo, 0);
        } else {
            setIcon(filterTwo, 1);
        }
    }

    private void setIconForSale() {

        if (currentSaleSortAsc) {
            setIcon(filterOne, 0);
        } else {
            setIcon(filterOne, 1);
        }
    }


    private FilterOptionClickListner filterOptionClickListner;


    public void setFilterOptionClickListner(FilterOptionClickListner filterOptionClickListner) {
        this.filterOptionClickListner = filterOptionClickListner;
    }

    public interface FilterOptionClickListner {
        void onSaleClick(View view, boolean currentSaleSortAsc);

        void onPriceClick(View view, boolean currentPriceAsc);

        void onFilterCondition(View view);
    }


    /*
    获得过滤的条件
     */
    public String getFilterConditionString() {
        return "currentSaleSortAsc=" + currentSaleSortAsc + "&currentPriceSortAsc=" + currentPriceSortAsc;

    }


}
