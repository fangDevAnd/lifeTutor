package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.simple1;

public class ConcreateWatcher implements Watcher {

    @Override
    public void update(String str) {
        System.out.print(str);
    }
}
