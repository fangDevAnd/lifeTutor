package com.xiaofangfang.rice2_verssion.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xiaofangfang.rice2_verssion.R;

/**
 * 价格区间
 */
public class PriceSpace extends LinearLayout {

    public PriceSpace(Context context) {
        this(context, null);
    }

    public PriceSpace(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PriceSpace(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private EditText startPrice, endPrice;

    /**
     * 初始化布局
     */
    private void initView() {

        this.setOrientation(LinearLayout.VERTICAL);

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.price_space, null, false);

        startPrice = view.findViewById(R.id.startPrice);
        endPrice = view.findViewById(R.id.endPrice);

        LayoutParams layoutParams = new LayoutParams(-1, -2);
        this.addView(view, layoutParams);
    }

    /**
     * 获得价格的区间
     * 返回最终的价格区间的值，在使用该方法获得的数据之后请将数据
     * 与-1进行对比，如果该值等于-1，代表对应的区间没有输入数据
     *
     * @return
     */
    public int[] getPriceRank() {
        int price1 = -1;
        int price2 = -1;
        if (!TextUtils.isEmpty(startPrice.getText())) {
            price1 = Integer.parseInt(startPrice.getText().toString());
        }
        if (!TextUtils.isEmpty(endPrice.getText())) {
            price2 = Integer.parseInt(endPrice.getText().toString());
        }
        int[] value = new int[2];
        value[0] = price1;
        value[1] = price2;
        return value;
    }

}
