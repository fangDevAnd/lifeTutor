package com.xiaofangfang.consumeview.ConstraintLayout;

import android.os.Bundle;

import com.xiaofangfang.consumeview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyActivity extends AppCompatActivity {


    /**
     * 对应的布局为  constraint_开头的布局
     * {@link R.layout.constraint*}
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);



    }
}
