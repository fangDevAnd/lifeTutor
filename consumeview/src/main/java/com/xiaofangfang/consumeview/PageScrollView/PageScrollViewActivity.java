package com.xiaofangfang.consumeview.PageScrollView;

import android.os.Bundle;

import com.xiaofangfang.consumeview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 实现的是弹性的scrollview
 */
public class PageScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_scroll_view);
    }
}
