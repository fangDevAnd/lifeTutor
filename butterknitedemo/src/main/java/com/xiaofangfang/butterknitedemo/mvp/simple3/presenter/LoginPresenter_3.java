package com.xiaofangfang.butterknitedemo.mvp.simple3.presenter;


import com.xiaofangfang.butterknitedemo.mvp.simple3.base.BasePresenter_3;
import com.xiaofangfang.butterknitedemo.mvp.simple3.mode.LoginModel_3;
import com.xiaofangfang.butterknitedemo.mvp.simple3.ui.LoginView_3;

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


public class LoginPresenter_3 extends BasePresenter_3 {


    private LoginModel_3 loginModel;


    public LoginPresenter_3() {
        this.loginModel = new LoginModel_3();
    }


    public void login(String name, String password) {

        this.loginModel.login(name, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (getLoginView() != null) {
                    getLoginView().onLoginResult(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (getLoginView() != null) {
                    getLoginView().onLoginResult(response.body().string());
                }
            }
        });

    }


}
