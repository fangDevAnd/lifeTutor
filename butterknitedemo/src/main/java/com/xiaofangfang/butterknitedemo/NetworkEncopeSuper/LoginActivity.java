package com.xiaofangfang.butterknitedemo.NetworkEncopeSuper;

import android.os.Bundle;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class LoginActivity extends BaseActivity {//工具类  被封装到一个工具中 ,然后我们定义一个接口,
    //view  ---->context  --->  banner--->context----loginAcitivty     bannner ---listVIewAcitivity  ---->




    List<Fragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fragmentList = getSupportFragmentManager().getFragments();


        findViewById(R.id.login).setOnClickListener((v) -> {
            openNetwork("http://192.168.43.170:8080/LoginTest/Login", 12);
        });




    }


    @Override
    public void failth(Exception e, int what) {

    }

    @Override
    public void success(String response, int what) {
        if (what == 12) {
            Toast.makeText(this, "activity接受到请求数据" + response, Toast.LENGTH_SHORT).show();
        }

        if (what == 3456) {
            for (Fragment fragment : fragmentList) {

                if (fragment.getTag().equals("loginFragment")) {

                    if (fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).success(response, what);
                    }
                }
            }
        }
    }
}
