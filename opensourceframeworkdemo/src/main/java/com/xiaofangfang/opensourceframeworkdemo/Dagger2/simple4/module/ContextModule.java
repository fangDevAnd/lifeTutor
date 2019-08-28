package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.module;


import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }
}
