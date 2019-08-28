package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple6.component;

import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple6.MainActivity;

import dagger.Component;

@Component
public interface GangJingComponent {
    void inject(MainActivity mainActivity);
}