package com.example.componentasystemtest.canvas.Shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.componentasystemtest.R;


/**
 * Created by fang on 2018/6/9.
 */

public class MyView extends View {

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
        paint.setStrokeWidth(50);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meizi);
        BitmapShader bitmapShader=new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        LinearGradient linearGradient = new LinearGradient(0, 0, 200, 200, Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
        // paint.setShader(bitmapShader);
        paint.setShader(linearGradient);
        // canvas.drawCircle(200, 200, 100, paint);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, 0, getWidth(), getBottom(), paint);
    }
}
