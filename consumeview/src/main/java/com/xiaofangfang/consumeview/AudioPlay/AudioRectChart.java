package com.xiaofangfang.consumeview.AudioPlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

import androidx.annotation.Nullable;

/**
 * 音频条形图
 */
public class AudioRectChart extends View {

    private static final String TAG = "test";

    public AudioRectChart(Context context) {
        this(context, null);
    }

    public AudioRectChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioRectChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    int minWidth = 200;
    int minHeight = 100;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        switch (modeWidth) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:

                width = Math.min(minWidth, sizeWidth);
                break;
            case MeasureSpec.EXACTLY:
                width = sizeWidth;
                break;
        }

        switch (modeHeight) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:

                height = Math.min(minHeight, sizeHeight);
                break;
            case MeasureSpec.EXACTLY:
                height = sizeHeight;
                break;
        }
        setMeasuredDimension(width, height);

    }

    private Paint paint;

    private void initView() {
        paint = new Paint();

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(5);
        paint.setColor(Color.parseColor("#111111"));

    }

    float defaultHeight;

    int innerPadding = 10;
    int count;
    float innerWidth = 30;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        defaultHeight = getHeight() - innerPadding;//当前的高度

        count = (int) ((getMeasuredWidth() - innerPadding) / innerWidth);


        Log.d(TAG, "onSizeChanged:  height" + defaultHeight + "\tcount=" + count);

        blockMaxHeight = (int) (defaultHeight - blockHeight);
    }

    float currentHeight;

    float blockHeight = 20;

    int blockMaxHeight;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 我们在绘制的时候，希望我们的条形图的起点在当前view的最下面
         */
        for (int i = 0; i < count; i++) {
            currentHeight = getCurrentHeight();

            Shader shader = new LinearGradient(0, defaultHeight, innerWidth, defaultHeight - currentHeight, Color.YELLOW, Color.BLUE, Shader.TileMode.CLAMP);
            paint.setShader(shader);

            canvas.drawRect(i * innerWidth + innerPadding, defaultHeight - currentHeight, (i + 1) * innerWidth, defaultHeight, paint);

            canvas.drawRect(i * innerWidth + innerPadding, defaultHeight - currentHeight - innerPadding - blockHeight, (i + 1) * innerWidth, defaultHeight - currentHeight - innerPadding, paint);

        }


        postInvalidateDelayed(300);

    }


    public int getCurrentHeight() {
        return (int) (Math.random() * blockMaxHeight);
    }


}
