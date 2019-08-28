package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.Simple4;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.xiaofangfang.opensourceframeworkdemo.R;
import com.xiaofangfang.opensourceframeworkdemo.RxAndroid.Simple4.LoginUtil.LoginUtil;
import com.xiaofangfang.opensourceframeworkdemo.util.Bind;
import com.xiaofangfang.opensourceframeworkdemo.util.BindProgress;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.internal.util.ObserverSubscriber;
import rx.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {

    @Bind(bindView = R.id.user)
    EditText user;
    @Bind(bindView = R.id.password)
    EditText password;
    @Bind(bindView = R.id.submit)
    Button button;

    private LoginUtil loginUtil;

    /**
     * 请求服务区地址
     */
    private final String LOGIN = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        BindProgress.bindView(this);
    }

    @Bind(onCLick = {
            R.id.submit
    })
    public void onClick(View view) {

        Map<String, String> params = new HashMap<>();
        params.put("username", user.getText().toString());
        params.put("password", password.getText().toString());
        Observable observable = loginUtil.login(LOGIN, params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onNext(String o) {
                        Toast.makeText(LoginActivity.this, "" + o, Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
