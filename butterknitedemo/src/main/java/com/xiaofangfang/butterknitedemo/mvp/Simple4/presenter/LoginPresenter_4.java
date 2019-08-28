package com.xiaofangfang.butterknitedemo.mvp.Simple4.presenter;


import com.xiaofangfang.butterknitedemo.mvp.Simple4.base.BasePresenter_4;
import com.xiaofangfang.butterknitedemo.mvp.Simple4.ui.BaseView;
import com.xiaofangfang.butterknitedemo.mvp.Simple4.ui.LoginView_4;
import com.xiaofangfang.butterknitedemo.mvp.simple3.base.BasePresenter_3;
import com.xiaofangfang.butterknitedemo.mvp.simple3.mode.LoginModel_3;

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


public class LoginPresenter_4 extends BasePresenter_4<LoginView_4> {


    private LoginModel_3 loginModel;


    public LoginPresenter_4() {
        this.loginModel = new LoginModel_3();
    }


    public void login(String name, String password) {

        this.loginModel.login(name, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (getView() != null) {
                    getView().onLoginResult(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (getView() != null) {
                    getView().onLoginResult(response.body().string());
                }
            }
        });

    }


}
