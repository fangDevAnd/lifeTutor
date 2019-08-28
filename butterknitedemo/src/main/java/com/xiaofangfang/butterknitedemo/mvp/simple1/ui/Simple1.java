package com.xiaofangfang.butterknitedemo.mvp.simple1.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;
import com.xiaofangfang.butterknitedemo.mvp.network.NetRequest;
import com.xiaofangfang.butterknitedemo.mvp.simple1.presenter.LoginPresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Simple1 extends AppCompatActivity implements LoginView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //最原始的写法  垃圾代码
        findViewById(R.id.click).setOnClickListener((v) -> {
            NetRequest.requestUrl("f", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Simple1.this, "失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Simple1.this, "请求成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        });


        /**
         * mvp代码优化
         * 进行项目的框架的搭建（分模块  ，分为ui层  数据层）
         * 接下来进行代码的优化---->MVP设计关键
         * M层：LoginModel_2--->专门负责数据层---->数据库操作，网络操作，文件操作等等
         * P层：LoginPresenter_2----->专门负责交互（中介）---->将UI层和数据层进行关联
         * V层：loginView    ----->view层是没有具体的实例代码，只是一个接口，
         * 具体的v层就是我们的activity，fragment  view等 持有p层的引用
         */
        findViewById(R.id.click).setOnClickListener((v) -> {
            LoginPresenter loginPresenter = new LoginPresenter(this);
            loginPresenter.login("fasnfas", "3535");

        });


    }

    @Override
    public void onLoginResult(String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Simple1.this, "请求" + result, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 继续优化代码
     * 发现问题 ，当我们的网络请求正在进行的时候，我们退出了activity，然而我们的UI层的引用还会回调
     * 这是没有必要的，我们可以直接终止请求，当前的代码存在隐患？
     * 解决方案： 方法的绑定和解绑
     */


}
