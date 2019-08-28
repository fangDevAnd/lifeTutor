package com.example.momomusic.activity;

import android.os.Bundle;

import com.example.momomusic.activity.ui.BaseView;
import com.example.momomusic.precenter.BasePresenter;

import androidx.annotation.Nullable;


/**
 * 抽象出解绑和绑定操作
 * 为了兼容多个模块
 * 所以采用泛型设计
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends ParentActivity {

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
