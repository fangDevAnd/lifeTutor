package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple3;


import dagger.Component;

@Component
public interface ActivityComponent {

    void inject(MainActivity activity);
}
