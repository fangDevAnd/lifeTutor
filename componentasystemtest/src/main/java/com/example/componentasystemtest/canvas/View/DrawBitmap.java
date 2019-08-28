package com.example.componentasystemtest.canvas.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.componentasystemtest.R;

/**
 * Created by fang on 2018/6/10.
 */

public class DrawBitmap extends View {

    Canvas mCanvas;
    Bitmap bitmap;
    Path path;
    Paint paint;

    public DrawBitmap(Context context) {
        this(context, null);
    }

    public DrawBitmap(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawBitmap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(40);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(20);
        paint.setStyle(Paint.Style.FILL);
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmap);
        int radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2;
        mCanvas.drawRoundRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), 30, 30, paint);
        PorterDuffXfermode porterDufffermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        paint.setXfermode(porterDufffermode);
        paint.setColor(Color.RED);
        paint.setAlpha(0);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

          paint.setAlpha(0);
         mCanvas.drawRoundRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), 30, 30, paint);
        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.meizi);
        bitmap1 = Bitmap.createScaledBitmap(bitmap1, getMeasuredWidth(), getMeasuredHeight(), false);
        canvas.drawBitmap(bitmap1, 0, 0, null);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mCanvas.drawPath(path, paint);
        invalidate();
        return true;
    }
}
