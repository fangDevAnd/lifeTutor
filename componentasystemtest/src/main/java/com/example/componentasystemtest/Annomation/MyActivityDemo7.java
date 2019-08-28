package com.example.componentasystemtest.Annomation;

import android.annotation.TargetApi;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.componentasystemtest.Annomation.consume.MyAnimation;
import com.example.componentasystemtest.R;


/**
 * Created by fang on 2018/6/9.
 */

public class MyActivityDemo7 extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo8);

        ImageView imageView = (ImageView) findViewById(R.id.img1);
        ((Animatable) imageView.getDrawable()).start();


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        //执行平移动画
        Toast.makeText(this, "点击事件", Toast.LENGTH_SHORT).show();
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 300);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(2000);
        v.startAnimation(translateAnimation);

        ViewPropertyAnimator viewProAnim = v.animate();
        viewProAnim.setDuration(2000)
                .setInterpolator(new BounceInterpolator())
                .alpha(0.5f)
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {

                    }
                })
                .rotation(120);
        viewProAnim.start();


        demo6(v);


    }

    MyAnimation myanimation;
    private void demo6(View v) {

         myanimation = new MyAnimation();
        v.startAnimation(myanimation);
    }

    public void dmeo5() {

        LayoutAnimationController layoutAnim = new LayoutAnimationController(myanimation);


    }

}
