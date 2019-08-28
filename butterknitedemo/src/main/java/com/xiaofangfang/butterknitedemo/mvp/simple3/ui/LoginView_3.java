package com.xiaofangfang.butterknitedemo.mvp.simple3.ui;


/**
 * v层不需要创建，我们的activity  fragment view就是view   ，所以仅仅需要定义我们回调数据的接口
 */


public interface LoginView_3 {


    /***
     * 这个参数就是presenter层传递的参数
     * @param result
     */
    void onLoginResult(String result);

}
