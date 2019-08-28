package com.xiaofangfang.filterrice.Activity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xiaofangfang.filterrice.R;


/**
 * 位置选择的条,用来实现对位置的更改
 */
public class LocationChangeBar extends LinearLayout implements View.OnClickListener {

    private String title;
    private String currentCity;
    private Context context;

    public LocationChangeBar(Context context, String title, String currentCity) {
        super(context);
        this.title = title;
        this.currentCity = currentCity;
        this.context = context;
        initView();

    }

    public LocationChangeBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LocationChangeBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initView() {

        ViewGroup viewRoot = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.location_change_bar, null, false);

        Button button = viewRoot.findViewById(R.id.locationButton);
        button.setText(currentCity);
        button.setOnClickListener(this);

        this.setOrientation(LinearLayout.VERTICAL);
        LayoutParams layoutParams = new LayoutParams(-1, 120);
        this.addView(viewRoot, layoutParams);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locationButton:
                progressLocationButtonEvent();
                break;
        }
    }

    /**
     * 处理位置按钮的点击事件的处理
     */
    private void progressLocationButtonEvent() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(R.layout.location_select);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        progressInnerViewEvent(alertDialog);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.width = width;
        layoutParams.height = height / 2;
        Window window = alertDialog.getWindow();
        window.setAttributes(layoutParams);
        window.setGravity(Gravity.TOP);
//        window.setWindowAnimations(R.style.location_select_animation);


    }

    private void progressInnerViewEvent(AlertDialog alertDialog) {


        ListView listView = alertDialog.findViewById(R.id.locationList);

    }





}
