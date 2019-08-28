package com.example.momomusic.precenter;


import com.example.momomusic.activity.ui.BaseView;

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


public abstract class BasePresenter<T extends BaseView> {


    private T view;

    public T getView() {
        return view;
    }

    public void attacgView(T view) {
        this.view = view;
    }


    public void detachView() {
        this.view = null;
        //终止请求
    }


}
