package com.xiaofangfang.animator.ViewZValueAnim;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.xiaofangfang.animator.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TranslationZActivity extends AppCompatActivity {


    View view1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_z);
        view1 = findViewById(R.id.view);
    }


    public void click(View view) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view1, "translationZ", 100);
        objectAnimator.setDuration(1000);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view1.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (200-value)));
            }
        });


        objectAnimator.start();
    }


}
