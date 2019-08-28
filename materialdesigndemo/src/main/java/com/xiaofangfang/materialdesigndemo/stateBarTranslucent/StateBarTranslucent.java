package com.xiaofangfang.materialdesigndemo.stateBarTranslucent;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiaofangfang.materialdesigndemo.R;

public class StateBarTranslucent extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        WindowManager.LayoutParams mParams;

        mParams = new WindowManager.LayoutParams();
        mParams.format = PixelFormat.TRANSLUCENT;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;  //设置状态栏透明
        }
        getWindow().setAttributes(mParams);

        /**
         * 上面实现的是透明的状态栏，实现了很多的方案，发现上面的实现
         * 是实现的最好的，之前自己实现的都有问题
         */

        setContentView(R.layout.activity_statebar1);


    }
}
