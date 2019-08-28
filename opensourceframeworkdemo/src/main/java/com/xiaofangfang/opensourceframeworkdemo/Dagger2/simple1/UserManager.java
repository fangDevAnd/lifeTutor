package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple1;

import android.content.Context;

/**
 * 用户管理
 */
public class UserManager {

    private ApiService apiService;

    private UserStore userStore;


    /**
     * userManager对里面的两个类具有依赖关系，
     * 能够减小类的耦合，
     *
     * @param context
     */
    public UserManager(Context context) {

        apiService = new ApiService();

        userStore = new UserStore(context);

    }


    public void register() {
        apiService.register();
        userStore.register();
    }


}
