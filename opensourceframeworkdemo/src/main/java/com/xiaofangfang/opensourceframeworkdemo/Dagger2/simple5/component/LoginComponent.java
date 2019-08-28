package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple5.component;


import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple5.module.LoginModule;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Component;

@Component(modules = {
        LoginModule.class
})
public interface LoginComponent {

    void inject(AppCompatActivity activity);

}
