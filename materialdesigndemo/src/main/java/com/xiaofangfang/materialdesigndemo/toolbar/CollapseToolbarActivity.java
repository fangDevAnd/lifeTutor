package com.xiaofangfang.materialdesigndemo.toolbar;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiaofangfang.materialdesigndemo.R;


/**
 * 展示的是一个可以折叠的toolbar
 */
public class CollapseToolbarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapse_toolbar);
//        setContentView(R.layout.collapse_toolbar1);
    }
}
