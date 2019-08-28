package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4;

import android.content.Context;

//import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.component.DaggerUserComponent;
import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.mode.ApiService;
import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.mode.UserStore;

import javax.inject.Inject;

/**
 * 用户管理
 */


public class UserManager {

    @Inject
    public ApiService apiService;

    @Inject
    public UserStore userStore;

    /**
     * userManager对里面的两个类具有依赖关系，
     * 能够减小类的耦合，
     */
//    public UserManager() {
//        DaggerUserComponent.create().inject(this);
//    }


    public void register(Context context) {
        apiService.register(context);
        userStore.register(context);
    }


}
