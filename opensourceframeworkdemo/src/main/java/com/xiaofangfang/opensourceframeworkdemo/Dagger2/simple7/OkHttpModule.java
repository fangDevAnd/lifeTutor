package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple7;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class OkHttpModule {


    @Provides
    public String provideString() {
        return "傻逼";
    }


    /**
     * 带参数的提供者,所以我们需要提供参数的提供者
     * 原因我们猜测是这样的  调用下面的方法的时候,会去new一个对象  ,这时我们正好提供了这个Provinde,所以就实现了这个功能
     *
     * @param message
     * @return
     */
    @Singleton
    @Provides
    public OkHttpClient okHttpClientProvider(String message) {
        return new OkHttpClient();
    }
}
