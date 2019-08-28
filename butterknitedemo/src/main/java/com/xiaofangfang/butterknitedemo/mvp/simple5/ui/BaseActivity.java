package com.xiaofangfang.butterknitedemo.mvp.simple5.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiaofangfang.butterknitedemo.mvp.simple5.base.BasePresenter_5;


/**
 * 抽象出解绑和绑定操作
 * 为了兼容多个模块
 * 所以采用泛型设计
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter_5<V>> extends AppCompatActivity {

    private V view;
    private P presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.presenter == null) {
            this.presenter = createPresenter();
        }

        if (this.view == null) {
            this.view = createView();
        }

        if (this.presenter != null && this.view != null) {
            this.presenter.attacgView(this.view);
        }

    }


    public P getPresenter() {
        return presenter;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.presenter != null && this.view != null) {
            this.presenter.detachView();
        }
    }

    public abstract V createView();


    public abstract P createPresenter();


}
