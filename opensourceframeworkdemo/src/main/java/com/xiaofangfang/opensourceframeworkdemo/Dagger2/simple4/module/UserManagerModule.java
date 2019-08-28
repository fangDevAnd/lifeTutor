package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.module;


import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.UserManager;

import dagger.Module;
import dagger.Provides;

@Module
public class UserManagerModule {
    @Provides
    UserManager provideUserManager() {
        return new UserManager();
    }
}
