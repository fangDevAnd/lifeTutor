package com.example.componentasystemtest.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by fang on 2018/6/9.
 */

public class MySurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    Canvas canvas;
    boolean isDrawing;
    SurfaceHolder surfaceHolder;
    Path path;
    Paint paint;

    public MySurface(Context context) {
        this(context, null);
    }

    public MySurface(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        //设置能够获得焦点
        setFocusable(true);
        //设置此视图是否可以在触摸模式下接收焦点。
        setFocusableInTouchMode(true);
        //保持屏幕开启状态
        this.setKeepScreenOn(true);
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(25);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing) {
            draw1();
        }
    }

    private void draw1() {
        try {
            //获得canvas
            canvas = surfaceHolder.lockCanvas();
            //在这里进行绘制
            drawSin();
            drawPath1();

        } catch (Exception e) {

        } finally {
            if (surfaceHolder != null) {
                //提交画布上的内容
                surfaceHolder.unlockCanvasAndPost(canvas);
                Log.d("test","提交");
            }
        }
    }

    private void drawPath1() {
        if (path != null) {
            canvas.drawPath(path, paint);
        }
    }

    private void drawSin() {
        int startX = 200;
        int startY = 200;
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);
        for (int i = 0; i < 2000; i++) {
            double value = Math.sin(i * Math.PI / 180) * 100;
            canvas.drawCircle(startX + i, (float) (startY + value), 10f, paint);
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
        draw1();
        return true;
    }
}
