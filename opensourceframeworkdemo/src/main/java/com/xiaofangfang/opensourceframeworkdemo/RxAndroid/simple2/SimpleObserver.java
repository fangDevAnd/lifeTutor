package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.simple2;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

public class SimpleObserver implements Observer {


    public SimpleObserver(SimpleObservable simpleObservable) {
        simpleObservable.addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        System.out.print("data is changed" + ((SimpleObservable) o).getData());
    }
}
