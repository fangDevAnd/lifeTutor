package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.simple2;

import java.util.Observable;

public class SimpleObservable extends Observable {


    private int data = 0;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        if (data != this.data) {
            this.data = data;
            setChanged();//发生改变
            notifyObservers();//通知观察者,表示状态发生改变
        }
    }


}
