package com.xiaofangfang.filterrice.Activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.xiaofangfang.filterrice.R;

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1);

        findViewById(R.id.start).setOnClickListener((v) -> {
            loadPop();

        });


    }


    WindowManager windowManager;

    private void loadPop() {

        windowManager = getWindowManager();

        float height = getResources().getDisplayMetrics().heightPixels * 3 / 4;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) height, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND, PixelFormat.RGBA_8888
        );
        layoutParams.windowAnimations = R.style.windowanim;
        layoutParams.gravity = Gravity.BOTTOM;
        View view = LayoutInflater.from(this).inflate(R.layout.pop_dialog, null, false);
        eventProgress(view);
        windowManager.addView(view, layoutParams);
        resetProductParam(view);
    }

    /**
     * 这个是弹出窗口的事件的处理
     *
     * @param view
     */
    private void eventProgress(View view) {

    }

    /**
     * 设置弹出窗口的参数，因为里面的数据是需要进行赋值操作的
     */
    private void resetProductParam(View view) {

    }


}
