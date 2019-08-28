package com.example.componentasystemtest.canvas.PathEfact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fang on 2018/6/9.
 */

public class PathEfect extends View {

    Path path;
    Paint paint;
    private float i;

    public PathEfect(Context context) {
        this(context, null);
    }

    public PathEfect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathEfect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(20);
        paint.setPathEffect(cornerPathEffect);

        DiscretePathEffect discretePathEffect=new DiscretePathEffect(3.5f,5.0f);
        paint.setPathEffect(discretePathEffect);
       if(i>10000){
           return;
       }
               draw1(canvas);

        path.moveTo(40,40);
        path.lineTo(400,400);
        path.lineTo(500,600);
        path.addCircle(0, 0, 5, Path.Direction.CCW);
        PathDashPathEffect pathDash = new PathDashPathEffect(path, 12, 0, PathDashPathEffect.Style.ROTATE);
        paint.setPathEffect(pathDash);
        path.moveTo(40, 40);
        path.lineTo(400, 400);
        path.lineTo(500, 600);
        canvas.drawPath(path, paint);
    }

    private void draw1(Canvas canvas) {

    }


}
