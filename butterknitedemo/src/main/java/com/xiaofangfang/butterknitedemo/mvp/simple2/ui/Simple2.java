package com.xiaofangfang.butterknitedemo.mvp.simple2.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;
import com.xiaofangfang.butterknitedemo.mvp.simple2.presenter.LoginPresenter_2;

public class Simple2 extends AppCompatActivity implements LoginView_2 {


    /**
     * 发现问题
     * 一个接口还好，如果多个类，我们需要反复的定义绑定和解绑的方法
     * 解决方案，将绑定和解绑进行抽象  BasePresenter
     */


    LoginPresenter_2 loginPresenter_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //最原始的写法  垃圾代码
        findViewById(R.id.click).setOnClickListener((v) -> {
            Log.d("test", "onCreate:== click");
            loginPresenter_2 = new LoginPresenter_2();
            loginPresenter_2.login("fang", "131113");
            loginPresenter_2.attacgView(this);
        });
    }


    @Override
    public void onLoginResult(String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Simple2.this, "请求=" + result, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginPresenter_2 != null) {
            loginPresenter_2.detachView();
        }
    }
}
