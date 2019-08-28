package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.module;


import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.mode.ApiService;
import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.mode.UserStore;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    UserStore provideUserStore() {
        return new UserStore();
    }

    @Provides
    ApiService provideApiSerivce() {
        return new ApiService();
    }


}
