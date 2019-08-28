package com.xiaofangfang.filterrice.consumeView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xiaofangfang.filterrice.R;
import com.xiaofangfang.filterrice.ViewDataBean.DataResponse;
import com.xiaofangfang.filterrice.ViewDataBean.FunctionDataResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的功能模块
 */
public class FunctionMode extends LinearLayout implements ViewOprate {


    FunctionDataResponse functionDataResponse;

    public FunctionMode(Context context) {
        this(context, null);
    }


    public FunctionMode(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FunctionMode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public TextView title;
    public Button function_one, function_two, function_three, function_four;
    private ViewGroup viewGroup;

    /**
     * 初始化参数
     */
    private void initView() {
        viewGroup = (ViewGroup) LayoutInflater.from(getContext())
                .inflate(R.layout.function_mode, null, false);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.setMargins(15, 15, 15, 15);
        this.addView(viewGroup, layoutParams);
    }

    private void updateData() {

        title = viewGroup.findViewById(R.id.title);

        function_one = viewGroup.findViewById(R.id.function_one);
        function_two = viewGroup.findViewById(R.id.function_two);
        function_three = viewGroup.findViewById(R.id.function_three);
        function_four = viewGroup.findViewById(R.id.function_four);

        List<Button> buttons = new ArrayList<>();
        buttons.add(function_one);
        buttons.add(function_two);
        buttons.add(function_three);
        buttons.add(function_four);


        title.setText(functionDataResponse.getTitle());

        List<FunctionDataResponse.FunctionModeSingleBean> functionModeSingleBeans = functionDataResponse.getList();
        for (int i = 0; i < functionModeSingleBeans.size(); i++) {
            final FunctionDataResponse.FunctionModeSingleBean functionModeSingleBean = functionModeSingleBeans.get(i);
            Drawable drawable = getResources().getDrawable(functionModeSingleBean.getImageAddress());

            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            buttons.get(i).setCompoundDrawables(null, drawable, null, null);
            buttons.get(i).setText(functionModeSingleBean.getTitle());


            buttons.get(i).setOnClickListener((v) -> {

                Intent intent = new Intent(functionModeSingleBean.getClickStartActivity());
                if (!TextUtils.isEmpty(functionModeSingleBean.getTableName())) {
                    intent.putExtra("tableName", functionModeSingleBean.getTableName());
                }
                getContext().startActivity(intent);
            });
        }
    }


    @Override
    public void setDataResponse(DataResponse dataResponse) {
        this.functionDataResponse = (FunctionDataResponse) dataResponse;
        updateData();
    }

    @Override
    public void updateDataResponse(DataResponse dataResponse, int leavel) {


    }


    @Override
    public void defaultDataResponse() {


    }


}
