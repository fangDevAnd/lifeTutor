package com.example.componentasystemtest.canvas.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by fang on 2018/6/9.
 */

public class MyViewDemo2 extends View {
    Paint paint;
    Path path;

    public MyViewDemo2(Context context) {
        this(context, null);
    }

    public MyViewDemo2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewDemo2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        path = new Path();
        paint = new Paint();
        paint.setAlpha(0);
        paint.setColor(Color.RED);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        if(path!=null){
            canvas.drawPath(path,paint);
        }
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
        invalidate();
        return true;
    }
}
