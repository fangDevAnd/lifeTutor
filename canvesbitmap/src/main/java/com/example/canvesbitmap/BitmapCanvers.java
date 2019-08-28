package com.example.canvesbitmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class BitmapCanvers extends ImageView {

    public BitmapCanvers(Context context) {
        super(context);
    }

    public BitmapCanvers(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    Paint paint;

    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
    }

    public BitmapCanvers(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);


        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        Bitmap bitmap;

        Canvas canvas1 = new Canvas(bitmap=Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8));


        canvas1.drawText("xgvsdgsdgsdg", 20, 20,paint);



        canvas.drawBitmap(bitmap, 0,0,paint);

    }
}
