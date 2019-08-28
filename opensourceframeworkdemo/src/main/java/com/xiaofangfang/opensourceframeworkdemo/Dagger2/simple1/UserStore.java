package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple1;

import android.content.Context;

public class UserStore {


    private Context context;

    public UserStore(Context context) {
        this.context = context;
    }


    /**
     * 保存到本地
     */
    public void register() {
        context.getSharedPreferences("user_sp", Context.MODE_PRIVATE);
    }
}
