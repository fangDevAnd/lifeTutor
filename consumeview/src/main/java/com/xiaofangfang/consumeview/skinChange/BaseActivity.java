package com.xiaofangfang.consumeview.skinChange;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import com.xiaofangfang.consumeview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public SharedPreferences sp;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int currentTheme = sp.getInt("theme", 0);
        if (currentTheme != 0) {
            setTheme(currentTheme);
        }
        super.onCreate(savedInstanceState);
    }


    /**
     * 尽管可以通过这个方式实现,但是并不推荐,原因是影响系统功能,同时影响用户的体验
     *
     */
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        recreate();
//    }
}
