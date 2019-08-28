package com.xiaofangfang.consumeview.ClippingView;

import android.os.Bundle;
import android.view.View;

import com.xiaofangfang.consumeview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ClipProviderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clip_provinde);

        findViewById(R.id.btn1).setOutlineProvider(ClipViewUtil.getRoundRect(20));
        findViewById(R.id.img).setOutlineProvider(ClipViewUtil.getCircle());
    }

}
