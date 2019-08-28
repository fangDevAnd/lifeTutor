package com.example.componentasystemtest.moveLinearViewDisplay.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by fang on 2018/6/10.
 */

public class LinearLayoutView extends View implements ValueAnimator.AnimatorUpdateListener {

    Paint paint;
    ValueAnimator valueAnimator;
    private Canvas mCanvas;
    private int fontWidht;//字的宽
    private Bitmap bitmap;
    String title = "你好,我是你的朋友";
    boolean isSetting;


    public LinearLayoutView(Context context) {
        this(context, null);
    }

    public LinearLayoutView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    private void init() {
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmap);
        //初始化画笔
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(40);
        paint.setTextSize(40);
        paint.setAntiAlias(true);
        paint.setDither(true);
        fontWidht = (int) paint.measureText(title);
        valueAnimator = ValueAnimator.ofInt(-fontWidht, getMeasuredWidth() + fontWidht);
        valueAnimator.addUpdateListener(this);
        valueAnimator.setDuration(4000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
        drawMoveBitmap();
    }

    private void drawMoveBitmap() {
        if (!isSetting) {
            valueAnimator.start();
            isSetting = true;
        }

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmap);
        Log.d("test", "获得的差值为=" + value);
        mCanvas.drawText(title, value, getMeasuredHeight() / 2 - 10, paint);
        invalidate();

    }
}
