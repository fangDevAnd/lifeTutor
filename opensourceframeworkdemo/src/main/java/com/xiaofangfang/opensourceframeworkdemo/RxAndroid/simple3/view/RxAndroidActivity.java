package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.simple3.view;

import android.os.Bundle;
import android.view.View;

import com.xiaofangfang.opensourceframeworkdemo.R;
import com.xiaofangfang.opensourceframeworkdemo.RxAndroid.simple3.android_rx.RxUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RxAndroidActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_android);


    }


    /**
     * 创建了一个被观察者
     *
     * @param view
     */
    public void click(View view) {
        RxUtils.createObservable();
    }

    public void clickTwo(View view) {
        RxUtils.createPrint();
    }

    public void clickThree(View view) {
        RxUtils.from();
    }

    public void clickFour(View view) {
        RxUtils.just();

    }

    public void clickFive(View view) {
        RxUtils.filter();
    }
}
