package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.component;


import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.UserManager;
import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.module.UserModule;

import dagger.Component;


@Component(modules = {
        UserModule.class})
public interface UserComponent {

    void inject(UserManager userManager);
}
