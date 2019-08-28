package com.xiaofangfang.butterknitedemo.mvp.Simple4.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;
import com.xiaofangfang.butterknitedemo.mvp.Simple4.presenter.LoginPresenter_4;

public class Simple4 extends AppCompatActivity implements LoginView_4 {


    /**
     * 继续优化
     * 如果activity重复增加，我们会重新编写很多绑定和解绑操作，这些属于代码的冗余
     * 所以对于一些的实现需要定义到父类中
     * <p>
     * 解决方案
     * 抽象  activity的抽象
     */


    private LoginPresenter_4 loginPresenter_4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //最原始的写法  垃圾代码
        findViewById(R.id.click).setOnClickListener((v) -> {
            Log.d("test", "onCreate:== click");
            loginPresenter_4 = new LoginPresenter_4();
            loginPresenter_4.attacgView(this);
            loginPresenter_4.login("fang", "24212");
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginPresenter_4 != null) {
            loginPresenter_4.detachView();
        }
    }

    @Override
    public void onLoginResult(String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Simple4.this, "请求数据" + result, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
