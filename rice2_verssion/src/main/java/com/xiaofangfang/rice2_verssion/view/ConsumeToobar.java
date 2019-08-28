package com.xiaofangfang.rice2_verssion.view;

import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.model.City;
import com.xiaofangfang.rice2_verssion.model.Province;
import com.xiaofangfang.rice2_verssion.tool.DialogTool;


/**
 * 自定义的toolbar,是一个头部的控件，用来实现程序的地理位置的切换
 */
public class ConsumeToobar extends LinearLayout implements View.OnClickListener {

    /**
     * 定义的一些主题资源
     */
    public static int transparentTheme = 1;
    public static int writeTheme = 2;

    public ConsumeToobar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConsumeToobar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public Button locationChange;
    public TextView titleText;
    public RelativeLayout bgContainer;
    private DialogTool.AddressSelectDialog addressSelectDialog;

    public void setTitle(String title) {
        titleText.setText(title);
    }

    public void setLocation(String title) {
        locationChange.setText(title);
    }

    private void initView() {

        ViewGroup viewRoot = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.consume_toolbar_layout, null, false);

        locationChange = viewRoot.findViewById(R.id.locationButton);
        titleText = viewRoot.findViewById(R.id.title);
        locationChange.setOnClickListener(this);
        bgContainer = viewRoot.findViewById(R.id.bgContainer);

        LayoutParams layoutParams = new LayoutParams(-1, 160);
        this.addView(viewRoot, layoutParams);

    }


    public void setTheme(int theme) {
        if (theme == transparentTheme) {

            bgContainer.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            return;
        }
        if (theme == writeTheme) {

            bgContainer.setBackgroundColor(getResources().getColor(android.R.color.white));
            titleText.setTextColor(getResources().getColor(android.R.color.black));

            return;
        }
    }

    /**
     * 设置颜色
     *
     * @param color
     */
    public void setTitleColor(int color) {
        titleText.setTextColor(getResources().getColor(color));
    }

    public void setTitleAlpha(float alpha) {
        titleText.setAlpha(alpha);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locationButton:
                addressSelectDialog = new DialogTool.AddressSelectDialog(getContext());
                addressSelectDialog.setLocationChangeListener((City city, Province province) -> {
                    locationChange.setText(city.getName());
                    if (lo != null) {
                        lo.change(province, city);
                    }
                });
                addressSelectDialog.progress();

                break;
        }
    }


    private LocationChangeListener lo;

    public void setLocationChange(LocationChangeListener lo) {
        this.lo = lo;
    }

    public static interface LocationChangeListener {
        void change(Province province, City city);
    }


}
