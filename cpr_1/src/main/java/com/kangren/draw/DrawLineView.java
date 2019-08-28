package com.kangren.draw;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.kanren.cpr.R;
import com.lyc.utils.LogUtil;

import java.text.DecimalFormat;

public class DrawLineView extends View {


    public float lineUnit = 1f;
    public int passColor = Color.YELLOW;//��ש��
    public int lineColor = Color.GREEN;//����ͼ������
    public int headColor = Color.GRAY;//ǰ��СȦ
    public int speed = 20;
    private float pass = 5 / lineUnit;
    private float errorPass = 5 / lineUnit;

    public float getPass() {
        return pass;
    }

    public void setPass(float pass) {
        this.pass = pass / lineUnit;
    }

    public float getErrorPass() {
        return errorPass;
    }

    public void setErrorPass(float errorPass) {
        this.errorPass = errorPass / lineUnit;
    }

    public int lineY = 10;
    private Canvas bitmapCanvas, ovalCanvas;
    public float step = 0.5f;
    private Position lastPos, newPos;
    private Context context;
    private int gridColor = 0xffffffff;//�����ɫ
    private int fontColor = 0xffffffff;//������ɫ
    private Runnable runnable;
    //һ��ԭʼͼֻ���������ߣ� һ�����ڻ��Ƶ�ͼ
    private Bitmap oldBitmap, currentBitmap, ovalBitmap;
    private Paint linePaint, passPaint, headPaint, gridPaint, fontPaint, clearPaint, opacityPaint, errorPaint;
    public int rowCount = 7;
    private float cloumnCount = 8f;
    private float width;
    private float height;
    private float time = 28;
    private float rowHeight, cloumnWidth;

    public DrawLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
        initRunable();
        lastPos = new Position(34, 10);//34���� 10 �ϱ����İ�


    }

    private void DrawGrid() {
        width = getWidth() - 10;//�ұ���10�߾�
        height = getHeight() - 25;//��������10�߾�
        rowHeight = height / rowCount;//���±߾�
        cloumnWidth = (width - 34) / cloumnCount;//34��������
        bitmapCanvas = new Canvas(oldBitmap);

        bitmapCanvas.drawLine(34, 10 + pass * rowHeight, width, 10 + pass * rowHeight, passPaint);
        bitmapCanvas.drawLine(34, 10 + errorPass * rowHeight, width, 10 + errorPass * rowHeight, errorPaint);
        //���Ʊ���ͼ
        Resources res = this.getContext().getResources();
        Bitmap pressBitmap = BitmapFactory.decodeResource(res, R.drawable.press);
        bitmapCanvas.drawBitmap(pressBitmap, (width - pressBitmap.getWidth()) / 2, (height - pressBitmap.getHeight()) / 2, opacityPaint);
        for (int i = 0; i <= rowCount; i++) {
            float num = i * lineUnit;
            DecimalFormat df = new DecimalFormat("0.0");//���"0.12"
            bitmapCanvas.drawText(df.format(num), 8, 10 + 3 + i * rowHeight, fontPaint);
            bitmapCanvas.drawLine(34, 10 + i * rowHeight, width, 10 + i * rowHeight, gridPaint);

        }
        for (int i = 0; i <= cloumnCount; i++) {
            float x = 25 + i * cloumnWidth;
            if (i == cloumnCount)
                x -= 15;
            bitmapCanvas.drawText((i * time / cloumnCount) + "s", x, height + 23, fontPaint);
        }
        ovalBitmap = Bitmap.createBitmap(15, 15, Bitmap.Config.ARGB_8888);


        currentBitmap = Bitmap.createBitmap(oldBitmap);

        bitmapCanvas = new Canvas(currentBitmap);
        ovalCanvas = new Canvas(ovalBitmap);

        ovalCanvas.drawOval(new RectF(0, 0, 10,
                6), headPaint);

    }

    private void drawline() {


        bitmapCanvas.drawLine(lastPos.X, lastPos.Y, newPos.X, newPos.Y, linePaint);

        lastPos = newPos;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            if (oldBitmap == null) {
                oldBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                DrawGrid();
            }
            canvas.drawBitmap(currentBitmap, 0, 0, null);
            canvas.drawBitmap(ovalBitmap, lastPos.X, lastPos.Y - 3, null);
        } catch (Exception e) {
            LogUtil.WriteLog(e);
        }

    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(2);

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
        gridPaint.setAlpha(30);

        fontPaint = new Paint();
        fontPaint.setAntiAlias(true);
        fontPaint.setColor(fontColor);
        fontPaint.setStrokeWidth(2);


        clearPaint = new Paint();
        clearPaint.setAntiAlias(true);
        clearPaint.setColor(0x00000000);
        clearPaint.setStrokeWidth(1);

        errorPaint = new Paint();
        errorPaint.setAntiAlias(true);
        errorPaint.setColor(Color.RED);
        errorPaint.setStrokeWidth(2);

        opacityPaint = new Paint();
        opacityPaint.setAntiAlias(true);
        //opacityPaint.setColor(0xddddddaa);
        opacityPaint.setAlpha(120);
        opacityPaint.setStrokeWidth(1);

    }

    private void initRunable() {
        runnable = new Runnable() {

            @Override
            public void run() {


                while (true) {
                    try {

                        if (lastPos.X >= width - 3) {

                            lastPos = new Position(34, 10);
                            currentBitmap = Bitmap.createBitmap(oldBitmap);
                            bitmapCanvas = new Canvas(currentBitmap);
                        }
                        newPos = new Position(lastPos.X + step, lineY);
                        drawline();
                        ((Activity) context).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub

                                invalidate();
                            }
                        });

                        Thread.sleep(speed);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
            }

        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void updateY(float y) {
        //	synchronized(lastPos){
        lineY = (int) (10 + y / lineUnit * rowHeight);
        //	if(newPos==null)
        //	newPos=new Position(lastPos.X, lastPos.Y);
        //	newPos.Y=y/lineUnit*rowHeight;
        //}

    }

}

class Position {
    Position(float x, float y) {
        this.X = x;
        this.Y = y;

    }

    public float X;
    public float Y;
}
