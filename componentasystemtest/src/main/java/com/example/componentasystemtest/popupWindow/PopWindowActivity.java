package com.example.componentasystemtest.popupWindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.componentasystemtest.R;


/**
 * 通过对PopUpWindow的使用，来减少对viewPager的使用
 * 必要时,比较性能
 */
public class PopWindowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pop_window);
        
        findViewById(R.id.button1).setOnClickListener((v) -> {
            new PopupWindows(PopWindowActivity.this,v,-1);
        });
    }
}
