package com.xiaofangfang.animator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "test";
    TextView box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        box = findViewById(R.id.box);

        findViewById(R.id.one).setOnClickListener((v) -> {
            float width = 400;

            float width1 = 20;

            Log.d(TAG, "onCreate: " + box.getMeasuredHeight());

            box.setPivotX(0f);
            box.setPivotY(0f);
            //缩放动画
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(box, "scaleX", width1 / width);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(box, "scaleY", width1 / width);

            AnimatorSet as = new AnimatorSet();
            as.playTogether(scaleX, scaleY);
            as.start();
        });

        findViewById(R.id.two).setOnClickListener((v) -> {
            //平移动画

        });

        findViewById(R.id.three).setOnClickListener((v) -> {
            //背景渐变
        });


        findViewById(R.id.four).setOnClickListener((v) -> {
            //透明度渐变动画
        });

        findViewById(R.id.five).setOnClickListener((v) -> {
            //旋转动画

        });


    }
}
