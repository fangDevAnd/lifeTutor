package com.xiaofangfang.consumeview.skinChange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaofangfang.consumeview.R;

public class IndexActivit extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    public void click(View view) {
        Intent intent = new Intent(this, SkinChangeActivity.class);
        startActivity(intent);

    }
}
