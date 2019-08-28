package com.xiaofangfang.consumeview.MagicViewPager.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaofangfang.consumeview.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_viewpager);
    }

    public void onStandardViewPager(View view) {
        Intent intent = new Intent(MainActivity.this, StandardViewPagerActivity.class);
        startActivity(intent);
    }

    public void onCircleViewPager(View view) {
        Intent intent = new Intent(MainActivity.this, CircleViewPagerActivity.class);
        startActivity(intent);
    }

}
