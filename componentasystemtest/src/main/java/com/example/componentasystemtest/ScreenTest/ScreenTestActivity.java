package com.example.componentasystemtest.ScreenTest;

import android.os.Bundle;
import android.util.Log;

import com.example.componentasystemtest.R;

import java.util.logging.Logger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * android的屏幕的使用的是320X480作为设备独立像素
 * <p>
 * 那么对于物理像素特别好的手机,就会产生物理像素比dpr  ,按理说,android屏幕的最大值就应该是320dip,但是实际测试发现并不是
 */
public class ScreenTestActivity extends AppCompatActivity {

    private static final String TAG = "test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screen_test);


        Log.d(TAG, "dpr:" + getResources().getDisplayMetrics().density);//3.5
        Log.d(TAG, "densityDpi:" + getResources().getDisplayMetrics().densityDpi);//560
        Log.d(TAG, "heightPixels:" + getResources().getDisplayMetrics().heightPixels);//2712
        Log.d(TAG, "widthPixels:" + getResources().getDisplayMetrics().widthPixels);//1440

    }


}
