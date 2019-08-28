package com.example.componentasystemtest.canvas.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.componentasystemtest.R;


/**
 * Created by fang on 2018/6/9.
 */

public class MyViewDemo1 extends View {
    private final Paint paint;
    Path path;

    public MyViewDemo1(Context context) {
        this(context, null);
    }

    public MyViewDemo1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewDemo1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meizi);
        bitmap = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), false);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        if (path != null) {
            paint.setAlpha(0);
            paint.setColor(Color.RED);
            canvas.drawPath(path, paint);
        }
    }


    int start;
    int move;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
}
