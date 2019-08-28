package com.example.componentasystemtest.circular;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by fang on 2018/6/10.
 */

public class MyCircularReveal extends AppCompatActivity implements View.OnClickListener {

    ImageView img01;
    ImageView img02;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.demo10);

//        img01 = (ImageView) findViewById(R.id.img01);
//        img02 = (ImageView) findViewById(R.id.img02);
        img02.setOnClickListener(this);
        img01.setOnClickListener(this);

        //状态改变动画



    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.img01:
//                Animator animator = ViewAnimationUtils.createCircularReveal(v, v.getWidth() / 2, v.getHeight() / 2, 0, v.getWidth()/2);
//                animator.setInterpolator(new AccelerateDecelerateInterpolator());
//                animator.setDuration(2000);
//                animator.start();
//                break;
//            case R.id.img02:
//                Animator animator1 = ViewAnimationUtils.createCircularReveal(v, v.getWidth() / 2, v.getHeight() / 2, v.getWidth()/2,0 );
//                animator1.setInterpolator(new AccelerateDecelerateInterpolator());
//                animator1.setDuration(2000);
//                animator1.start();
//                break;
        }
    }
}
