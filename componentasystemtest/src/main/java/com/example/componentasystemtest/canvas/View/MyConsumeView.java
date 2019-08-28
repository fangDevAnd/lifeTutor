package com.example.componentasystemtest.canvas.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.os.SystemClock;
import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;


import com.example.componentasystemtest.R;

import java.util.ArrayList;

/**
 * Created by fang on 2018/6/7.
 */

public class MyConsumeView extends View {

    Paint paint;
    Path path;
    RectF rectf;
    private int width;
    private int height;
    private ArrayList<Object> list;

    public MyConsumeView(Context context) {
        this(context, null);
    }

    public MyConsumeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyConsumeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);

        path = new Path();

        rectf = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredHeight();
        height = getMeasuredWidth();
        list = new ArrayList<>();
        list.add(PorterDuff.Mode.ADD);
        list.add(PorterDuff.Mode.CLEAR);
        list.add(PorterDuff.Mode.DARKEN);
        list.add(PorterDuff.Mode.DST);
        list.add(PorterDuff.Mode.DST_ATOP);
        list.add(PorterDuff.Mode.DST_IN);
        list.add(PorterDuff.Mode.DST_OUT);
        list.add(PorterDuff.Mode.DST_OVER);
        list.add(PorterDuff.Mode.LIGHTEN);
        list.add(PorterDuff.Mode.MULTIPLY);
        list.add(PorterDuff.Mode.OVERLAY);
        list.add(PorterDuff.Mode.SCREEN);
        list.add(PorterDuff.Mode.SRC);
        list.add(PorterDuff.Mode.SRC_ATOP);
        list.add(PorterDuff.Mode.SRC_OUT);
        list.add(PorterDuff.Mode.SRC_IN);
        list.add(PorterDuff.Mode.SRC_OVER);
        list.add(PorterDuff.Mode.XOR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //drawDemo1(canvas);


        canvasModel(canvas);


    }

    private void drawDemo1(Canvas canvas) {
        int radius = Math.min(width, height);
        canvas.drawCircle(width / 2, height / 2, radius / 2, paint);
        canvas.saveLayerAlpha(getLeft(), getTop(), getRight(), getBottom(), 120, Canvas.ALL_SAVE_FLAG);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(width / 3, height / 3, radius / 3, paint);
        canvas.save();

        canvas.rotate(50);
        canvas.translate(100, 100);
        paint.setColor(Color.RED);
        paint.setTextSize(120);

        canvas.drawText("你好", 30, 30, paint);
        canvas.restore();

        canvas.drawText("你好", 69, 60, paint);


        ColorMatrix colorMatrix = new ColorMatrix();
        //设置色调
        colorMatrix.setRotate(0, 0);
        colorMatrix.setRotate(1, 340);
        colorMatrix.setRotate(2, 0);
        //设置饱和度
        colorMatrix.setSaturation(1.4f);
        //设置亮度
        colorMatrix.setScale(0.2f, 0.4f, 0.6f, 0.4f);

        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixColorFilter);

        canvas.drawText("傻逼", 180, 180, paint);

        Matrix matrix = new Matrix();
        matrix.setRotate(50);
        canvas.setMatrix(matrix);
        canvas.drawText("傻逼", 180, 180, paint);
    }

    //学习图层的叠加模式
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void canvasModel(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meizi);
        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 300, false);
        //  canvas.setBitmap(bitmap1);

//        canvas.drawRoundRect(20,30,bitmap1.getWidth()+20,bitmap1.getHeight()+30,80,80,paint);
//        RectF oval3 = new RectF(20, 30, bitmap.getWidth() + 20, bitmap.getHeight() + 30);
//        PorterDuffXfermode porterDuffXmode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);//显示的是底层的
//        paint.setXfermode(porterDuffXmode);
//        canvas.drawRoundRect(oval3, 20, 15, paint);
//        canvas.drawBitmap(bitmap, null, oval3, paint);

        if (i == 18) {
            return;
        }
        draw1(canvas);
    }


    int i = 0;

    private void draw1(Canvas canvas) {

//        Bitmap bitmap1 = drawRect();
//        Bitmap bitmap2 = drawCircle();
//        canvas.drawBitmap(bitmap1, 200, 200, paint);
        canvas.drawRect(10, 10, 210, 210, paint);
        PorterDuffXfermode port = new PorterDuffXfermode((PorterDuff.Mode) list.get(i));
        paint.setXfermode(port);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(200, 200, 100, paint);
//        canvas.drawBitmap(bitmap2, 300, 300, paint);
        i++;
        SystemClock.sleep(1000);
        invalidate();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Bitmap drawRect() {
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRoundRect(0, 0, 400, 400, 50, 50, paint);
        return bitmap;
    }

    public Bitmap drawCircle() {
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(200, 200, 200, paint);
        return bitmap;
    }

}
