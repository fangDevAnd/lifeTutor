package com.example.componentasystemtest.Annomation.consume;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.componentasystemtest.R;


/**
 * Created by fang on 2018/6/9.
 */

public class MyView extends View {

    Path path;
    Bitmap bitmap;
    Paint paint;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(50);
       bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meizi);
         bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
        canvas.drawBitmap(bitmap,20,30,paint);
       Camera camera = new Camera();
        camera.rotate(-100, 100, -10);
       Matrix matrix = new Matrix();
        camera.getMatrix(matrix);
        canvas.drawBitmap(bitmap, matrix, paint);


        camera.translate(100, 0, 500);
        camera.getMatrix(matrix);


        canvas.translate(0,1000);
        canvas.drawRect(0, 0, 200,200, paint);
        canvas.drawLine(100,0,100,1000,paint);

        canvas.drawBitmap(bitmap, matrix, paint);



        canvas.drawText("nihao", 30, 40, paint);
        Log.d("test", "testfdf");
    }
}
