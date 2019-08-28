package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.component;


import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.MainActivity;
import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.module.UserManagerModule;

import dagger.Component;

@Component(modules = {
        UserManagerModule.class
})
public interface UserManagerComponent {

    void inject(MainActivity mainActivity);
}
