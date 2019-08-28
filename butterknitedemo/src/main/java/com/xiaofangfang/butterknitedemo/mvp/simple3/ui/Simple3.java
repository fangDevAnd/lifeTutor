package com.xiaofangfang.butterknitedemo.mvp.simple3.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;
import com.xiaofangfang.butterknitedemo.mvp.simple3.presenter.LoginPresenter_3;

public class Simple3 extends AppCompatActivity implements LoginView_3 {


    /**
     * 继续优化
     * 抽象父类  类型写死了，需要泛性化
     * 解决方案   使用泛性设计
     * 方案二    强制类型转换
     * 方案三    使用view的基类设计（有点呆板，灵活度不够，毕竟不同的view接口，需要实现的功能
     *          千差万别 ，无法建立一个统一的标准
     * ）
     */


    private LoginPresenter_3 loginPresenter_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //最原始的写法  垃圾代码
        findViewById(R.id.click).setOnClickListener((v) -> {
            Log.d("test", "onCreate:== click");
            loginPresenter_3 = new LoginPresenter_3();
            loginPresenter_3.attacgView(this);
            loginPresenter_3.login("FANG", "52353");
        });
    }


    @Override
    public void onLoginResult(String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Simple3.this, "请求数据" + result, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginPresenter_3 != null) {
            loginPresenter_3.detachView();
        }
    }
}
