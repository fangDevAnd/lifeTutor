package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple5.module;

import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple5.mode.Login;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private String name;
    private String password;

    public LoginModule(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Provides
    public Login provideLogin() {
        return new Login(name, password);
    }

}
