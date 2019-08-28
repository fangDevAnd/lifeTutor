package com.xiaofangfang.butterknitedemo.mvp.simple5.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;
import com.xiaofangfang.butterknitedemo.mvp.simple5.presenter.LoginPresenter_5;

public class Simple5 extends BaseActivity<LoginView_5, LoginPresenter_5> implements LoginView_5 {


    /**
     * 分析，对于这个架构，存在一个问题，如果一个界面需要实现多个presenter
     * 那么就存在问题
     * 有时间需要自己实现一套，同时网络加载不同的url出现大量重复代码，需要考虑重新设计
     * <p>
     * <p>
     * 扩展功能  对fragment的实现
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //最原始的写法  垃圾代码
        findViewById(R.id.click).setOnClickListener((v) -> {
            Log.d("test", "onCreate:== click");

            getPresenter().login("assfaf", "244125");

        });
    }

    @Override
    public LoginView_5 createView() {
        return this;
    }

    @Override
    public LoginPresenter_5 createPresenter() {
        return new LoginPresenter_5();
    }

    @Override
    public void onLoginResult(String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Simple5.this, "请求数据" + result, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
