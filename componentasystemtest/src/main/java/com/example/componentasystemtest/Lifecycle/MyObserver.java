package com.example.componentasystemtest.Lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 创建一个生命周期观察者
 */
public class MyObserver implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPuause() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory() {

    }


}
