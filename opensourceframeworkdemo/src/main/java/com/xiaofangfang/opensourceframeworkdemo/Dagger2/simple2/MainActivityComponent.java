package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple2;

import dagger.Component;

@Component
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
