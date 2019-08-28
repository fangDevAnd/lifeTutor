package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple7;

import javax.inject.Singleton;

import dagger.Component;

@Singleton//注意：写在Component的上面
@Component(modules = OkHttpModule.class)
public interface OkHttpComponent {
    void inject(MainActivity mainActivity);
}