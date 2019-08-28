package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.component;

import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.mode.TipOprate;
import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.module.ContextModule;

import dagger.Component;
import dagger.Module;

@Component(modules = {
        ContextModule.class
})

public interface ContextComponent {

    void inject(TipOprate tipOprate);
}
