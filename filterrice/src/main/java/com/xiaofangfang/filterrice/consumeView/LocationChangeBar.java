package com.xiaofangfang.filterrice.consumeView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

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

        ViewGroup viewRoot = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.location_change_bar, this, false);
        Button button = viewRoot.findViewById(R.id.locationButton);
        button.setText(currentCity);
        button.setOnClickListener(this);
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
        MyLocationDialog mld = new MyLocationDialog(context);
        mld.show();
    }


    /**
     * 位置的dialog
     */
    class MyLocationDialog extends Dialog {


        public MyLocationDialog(@NonNull Context context) {
            super(context);
            setContentView(R.layout.location_select);
            initDialogView();
        }

        /**
         * 对dialog的视图进行创建解析
         */
        private void initDialogView() {
            Window window = this.getWindow();
            // WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setGravity(Gravity.BOTTOM);
//            window.setWindowAnimations(R.style.location_select_animation);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                    -1, 400, 0, 0, PixelFormat.TRANSPARENT);
        }


    }


}
