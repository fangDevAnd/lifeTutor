package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4;


import android.os.Bundle;
import android.widget.Toast;

//import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.component.DaggerUserManagerComponent;
import com.xiaofangfang.opensourceframeworkdemo.R;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Inject
    UserManager userManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dagger_activity);

        /**
         * 生成 类的名字只Component的名字前面加上Dagger
         */


//        DaggerUserManagerComponent.create().inject(this);

        userManager.register(this);


    }
}
