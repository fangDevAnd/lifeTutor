package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.mode;

import android.content.Context;
import android.widget.Toast;

public class ApiService {


    public void register(Context context) {
        //向云端保存数据
        Toast.makeText(context, "进行了云端注册", Toast.LENGTH_SHORT).show();
    }
}
