package com.xiaofangfang.materialdesigndemo.TabLayout;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import com.xiaofangfang.materialdesigndemo.R;

import androidx.appcompat.app.AppCompatActivity;

public class TabLayoutActivity extends AppCompatActivity {


    TabLayout tablayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tablayout);

        tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.addTab(tablayout.newTab().setText("Tab 1"));
        tablayout.addTab(tablayout.newTab().setText("Tab 2"));
        tablayout.addTab(tablayout.newTab().setText("Tab 3"));
        tablayout.addTab(tablayout.newTab().setText("Tab 4"));
        tablayout.addTab(tablayout.newTab().setText("Tab 5"));
        tablayout.addTab(tablayout.newTab().setText("Tab 6"));


    }
}
