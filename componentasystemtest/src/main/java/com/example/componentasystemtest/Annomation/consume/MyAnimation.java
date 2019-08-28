package com.example.componentasystemtest.Annomation.consume;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

/**
 * 自定义动画
 * Created by fang on 2018/6/9.
 */

public class MyAnimation extends Animation {

    private int mWidth;
    private int mHeight;
    Camera camera;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //设置默认的时长
        setDuration(2000);
        //动画结束后保存状态
        setFillAfter(true);
        setInterpolator(new BounceInterpolator());
        mWidth = width / 2;
        mHeight = height / 2;
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        matrix.preScale(1, 1 - interpolatedTime, 20, 0);
        matrix = t.getMatrix();
        camera.save();
        camera.rotateY(60f * interpolatedTime);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(mWidth, mHeight);
        matrix.postTranslate(-mWidth, -mHeight);

    }
}
