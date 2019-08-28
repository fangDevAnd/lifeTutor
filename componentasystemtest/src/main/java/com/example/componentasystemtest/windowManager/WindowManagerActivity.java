package com.example.componentasystemtest.windowManager;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.componentasystemtest.R;


/**
 * 当前实例的作用是编写WindowManager的测试代码
 */
public class WindowManagerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stateBarTranslucent();

        setContentView(R.layout.activity_windowmanager);
    }


    public void stateBarTranslucent() {

        //获得当前的windowManager对象
//        mWindowManager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);

        WindowManager.LayoutParams mParams;

        mParams = new WindowManager.LayoutParams();
        mParams.format = PixelFormat.TRANSLUCENT;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;  //设置状态栏透明
        }

        getWindow().setAttributes(mParams);

    }

}
