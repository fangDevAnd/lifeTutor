package com.xiaofangfang.butterknitedemo.mvp.simple3.mode;


import com.xiaofangfang.butterknitedemo.mvp.network.NetRequest;

import okhttp3.Callback;

/**
 * 登录模块的实现
 */
public class LoginModel_3{

    //正常的是将这个作为常量   login作为一个登录的模块实现
    private String url = "http://10.109.3.112:8080/GF/MvpServlet?";

    public void login(String name, String password, Callback callback) {
        NetRequest.requestUrl(url + "user=" + name + "&password=" + password, callback);
    }







}
