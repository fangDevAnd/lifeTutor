package com.kangren.draw;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.View;

import com.lyc.utils.LogUtil;

/**
 * 绘制矩形的View
 */
public class DrawRectView extends View {

    public int passColor = Color.YELLOW;//
    private int lineColor = Color.RED;//����ͼ������


    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        linePaint = new Paint();
        linePaint.setStyle(Style.FILL);
        linePaint.setAntiAlias(true);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(2);
        linePaint.setAlpha(80);
    }

    public int headColor = Color.GRAY;//ǰ��СȦ
    public int speed = 20;
    public int pass = 5;


    //0���ϵ��� 1���µ���
    private int orientation = 0;
    //��������
    private int rectCount;
    private Canvas bitmapCanvas;
    private float step = 0.5f;
    private Context context;
    private int gridColor = 0xffffffff;//�����ɫ
    private int fontColor = 0xffffffff;//������ɫ
    private Runnable runnable;
    //
    private Bitmap bitmap;
    private Paint linePaint, passPaint, headPaint, gridPaint, fontPaint, clearPaint, opacityPaint;
    private float rowCount = 20f;
    private float cloumnCount = 8f;
    private float width;
    private float height;
    private float time = 28;

    public DrawRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();


    }


    private void drawRect() {

        if (getHeight() == 0 || getWidth() == 0)
            return;
        if (bitmap == null) {
            height = getHeight();
            width = getWidth();
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmap);
        }
        int top, left, right, bottom;
        if (orientation == 0) {
            top = 0;
            left = 0;
            right = bitmap.getWidth();
            bottom = (int) (rectCount * (bitmap.getHeight() / rowCount));

        } else {
            top = bitmap.getHeight();
            left = 0;
            right = bitmap.getWidth();
            bottom = (int) (bitmap.getHeight() - (rectCount * (bitmap.getHeight() / rowCount)));
        }
        bitmapCanvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
        bitmapCanvas.drawRect(left, top, right, bottom, linePaint);
        ((Activity) context).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                invalidate();
            }
        });

        //	ovalCanvas.drawOval(new RectF(newPos.X, newPos.Y - 3, newPos.X + 10,
        //			newPos.Y + 3), headPaint);

        //	lastPos=newPos;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            if (bitmap == null) {
                return;
            }

            canvas.drawBitmap(bitmap, 0, 0, null);
        } catch (Exception e) {
            LogUtil.WriteLog(e);
        }

    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setStyle(Style.FILL);
        linePaint.setAntiAlias(true);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(2);
        linePaint.setAlpha(80);

        passPaint = new Paint();
        passPaint.setAntiAlias(true);
        passPaint.setColor(passColor);
        passPaint.setStrokeWidth(2);

        headPaint = new Paint();
        headPaint.setAntiAlias(true);
        headPaint.setColor(headColor);
        headPaint.setStrokeWidth(1);

        gridPaint = new Paint();
        gridPaint.setAntiAlias(true);
        gridPaint.setColor(gridColor);
        gridPaint.setStrokeWidth(1);
        gridPaint.setAlpha(180);

        fontPaint = new Paint();
        fontPaint.setAntiAlias(true);
        fontPaint.setColor(fontColor);
        fontPaint.setStrokeWidth(2);


        clearPaint = new Paint();
        clearPaint.setAntiAlias(true);
        clearPaint.setColor(0x00000000);
        clearPaint.setStrokeWidth(1);


        opacityPaint = new Paint();
        opacityPaint.setAntiAlias(true);
        //opacityPaint.setColor(0xddddddaa);
        opacityPaint.setAlpha(120);
        opacityPaint.setStrokeWidth(1);

    }


    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void updateY(int rectCount) {

        this.rectCount = rectCount;

        drawRect();
    }

}

