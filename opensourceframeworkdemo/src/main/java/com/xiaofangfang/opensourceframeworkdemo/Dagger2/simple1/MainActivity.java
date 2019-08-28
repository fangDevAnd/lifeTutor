package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple1;


import android.os.Bundle;

import com.xiaofangfang.opensourceframeworkdemo.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private UserManager userManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dagger_activity);

        userManager = new UserManager(this);
        userManager.register();

    }
}
