package com.xiaofangfang.butterknitedemo.mvp.simple1.presenter;


import com.xiaofangfang.butterknitedemo.mvp.simple1.mode.LoginModel;
import com.xiaofangfang.butterknitedemo.mvp.simple1.ui.LoginView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * p层  用来实现对数据的处理与转发  ，实现逻辑控制
 * <p>
 * 特点
 * 1.持有M层的引用
 * 2.持有V层引用
 * 3.对m层和v层进行关联
 */


public class LoginPresenter {


    private LoginModel loginModel;
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginModel = new LoginModel();
        this.loginView = loginView;
    }

    public void login(String name, String password) {

        this.loginModel.login(name, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (loginView != null) {
                    loginView.onLoginResult(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (loginView != null) {
                    loginView.onLoginResult(response.body().string());
                }
            }
        });

    }


}
