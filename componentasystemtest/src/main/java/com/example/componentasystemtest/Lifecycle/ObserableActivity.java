package com.example.componentasystemtest.Lifecycle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ObserableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //添加一个生命周期观察者,getLifecycle()是FragmentActivity中的方法
        MyObserver observer = new MyObserver();
        getLifecycle().addObserver(observer);



    }
}
