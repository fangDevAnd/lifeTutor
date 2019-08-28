package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple7;


import android.os.Bundle;

import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple6.GangJing;
import com.xiaofangfang.opensourceframeworkdemo.R;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {


    @Inject
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dagger_activity);

        /**
         * 生成 类的名字只Component的名字前面加上Dagger
         */
        okHttpClient.newCall(new Request.Builder().build());


    }
}
