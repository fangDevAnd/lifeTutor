package com.example.componentasystemtest.Gradient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GradientTest extends View {


    public GradientTest(Context context) {
        super(context);
    }

    public GradientTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private Bitmap bitmap;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Canvas canvas1 = new Canvas(bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_4444));

        canvas1.drawCircle(getMeasuredHeight() / 2, getHeight() / 2, 100, new Paint());

        /**
         * 最后面一个参数可以为空
         */
        canvas.drawBitmap(bitmap, 0, 0, null);

//        LinearGradient linearGradient = new LinearGradient();
//        linearGradient.getLocalMatrix()




    }
}
