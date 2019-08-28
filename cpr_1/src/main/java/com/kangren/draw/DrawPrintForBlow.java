package com.kangren.draw;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DrawPrintForBlow {

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint linePaint;
    private int width = 100;
    private int height;
    private float step;
    private int speed;
    public int lineY;
    private int rowCount;
    private float lineUnit;
    private Runnable runnable;
    private boolean isStarting = false;
    private Position lastPos, newPos;
    private DrawBlowLineView view;

    public DrawPrintForBlow(DrawBlowLineView view) {
        this.view = view;
        this.lineUnit = view.lineUnit;
        this.rowCount = view.rowCount;
        this.lineY = view.lineY;
        this.speed = view.speed;
        this.step = view.step;
        this.height = view.getHeight();
        initVar();
    }

    public Bitmap getBitmap() {
        Bitmap res = Bitmap.createBitmap((int) lastPos.X, height, Config.RGB_565);
        Canvas cc = new Canvas(res);
        cc.drawBitmap(bitmap, 0, 0, null);
        return res;
    }

    public void reset() {
        bitmap = null;
        initVar();
    }

    public void start() {
        isStarting = true;
        DrawGrid();
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void stop() {
        isStarting = false;
    }

    private void initVar() {
        initRunable();
        updateCanvas();
        linePaint = new Paint();

        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        linePaint.setFakeBoldText(true);
        lastPos = new Position(34, lineY);
        newPos = new Position(34, lineY);
    }

    private void DrawGrid() {


        height = height;//��������10�߾�

        float rowHeight = (height - 25) / rowCount;//���±߾�


        float passY = 10 + (rowCount - view.getPass()) * rowHeight;
        float errorY = 10 + (rowCount - view.getErrorPass()) * rowHeight;
        canvas.drawLine(34, passY, width, passY, linePaint);
        canvas.drawLine(34, errorY, width, errorY, linePaint);

        //���Ʊ���ͼ


        for (int i = (int) rowCount; i > -1; i--) {
            float lineY = 10 + i * rowHeight;
            float textX = 8;
            int num = (int) ((rowCount - i) * lineUnit);
            if (num >= 1000)
                textX = 3;
            if (i == rowCount)
                textX = 15;


            canvas.drawText(num + "", textX, 18 + i * rowHeight, linePaint);
            //if(lineY!=passY&&lineY!=errorY)
            //	canvas.drawLine(34, 10+i*rowHeight, width, 10+i*rowHeight, linePaint);

        }


    }

    private void updateCanvas() {
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
            canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
        } else {
            width += 4000;
            Bitmap temp = Bitmap.createBitmap(width, height, Config.RGB_565);
            canvas = new Canvas(temp);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            bitmap = temp;
            DrawGrid();
        }
    }

    private void drawline() {

        if (newPos.X >= width)
            updateCanvas();
        canvas.drawLine(lastPos.X, lastPos.Y, newPos.X, newPos.Y, linePaint);

        lastPos = newPos;
    }

    private void initRunable() {
        runnable = new Runnable() {
            // ��дrun()�������˷������µ��߳�������
            @Override
            public void run() {


                while (isStarting) {
                    try {

                        newPos = new Position(lastPos.X + step, view.lineY);

                        drawline();


                        Thread.sleep(speed);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
            }

        };

    }

    class Position {
        Position(float x, float y) {
            this.X = x;
            this.Y = y;

        }

        public float X;
        public float Y;
    }


}
