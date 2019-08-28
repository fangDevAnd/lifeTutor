package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.mode;

import android.content.Context;
import android.widget.Toast;

public class UserStore {


    public UserStore() {

    }

    /**
     * 保存到本地
     */
    public void register(Context context) {
        Toast.makeText(context, "进行了本地的注册", Toast.LENGTH_SHORT).show();
    }
}
