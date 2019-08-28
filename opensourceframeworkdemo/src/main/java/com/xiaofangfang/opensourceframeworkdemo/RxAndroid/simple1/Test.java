package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.simple1;

public class Test {

    public static void main(String[] args) {

        Watched watched = new ConcreateWeathed();


        Watcher watcher1 = new ConcreateWatcher();
        Watcher watcher2 = new ConcreateWatcher();
        Watcher watcher3 = new ConcreateWatcher();

        watched.addWatcher(watcher1);
        watched.addWatcher(watcher2);
        watched.addWatcher(watcher3);

        watched.notifyWatchers("我要偷东西了\n");







    }
}
