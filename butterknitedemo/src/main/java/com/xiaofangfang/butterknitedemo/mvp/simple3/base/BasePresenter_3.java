package com.xiaofangfang.butterknitedemo.mvp.simple3.base;


import com.xiaofangfang.butterknitedemo.mvp.simple3.ui.LoginView_3;

/**
 * p层  用来实现对数据的处理与转发  ，实现逻辑控制
 * <p>
 * 特点
 * 1.持有M层的引用
 * 2.持有V层引用
 * 3.对m层和v层进行关联
 * <p>
 * 这里对p进行抽象
 */


public abstract class BasePresenter_3 {


    private LoginView_3 loginView;


    public LoginView_3 getLoginView() {
        return loginView;
    }

    public void attacgView(LoginView_3 loginView_2) {
        this.loginView = loginView_2;
    }


    public void detachView() {
        this.loginView = null;
        //终止请求
    }


}
