package com.xiaofangfang.butterknitedemo.dataBinding.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.dataBinding.model.event.MyHandlers;

public class DataBindingActivity_demo1 extends AppCompatActivity implements MyHandlers {

    private static final String TAG = "test";


//    ActivityDataBindingDemo1Binding adb;


    /**
     * 简单的评价一些这个动态绑定库
     * <p>
     * 并不能真正实现动态的刷新,通过更改mode,可以改变view的值,但是view的值的变化并不会带动mode的数据变化
     * 实现的是单项的数据绑定  mode------>view的绑定
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        adb = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_demo1);
//        User user = new User("fzy123", "13077995907");
//        adb.setUser(user);
//        adb.setHandlers(this);
    }

    @Override
    public void onClick(View view) {


        Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
    }
}
