package com.xiaofangfang.consumeview.ScrollerTest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.xiaofangfang.consumeview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ScrollerTest extends AppCompatActivity {


    MyView myView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_test);
        myView = findViewById(R.id.myview);
    }


    public void start(View view) {


        myView.scroller.startScroll(0, 0, 200, 200);
        myView.scrollBy(200, 200);

        /**
         *
         * 上班两点都做不到，这个我比较怀疑
         */


        ValueAnimator valueAnimator = ValueAnimator.ofFloat(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                myView.setTranslationX((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }


}
