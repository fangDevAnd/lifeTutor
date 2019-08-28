package com.example.canvesbitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class MapDraw {


    private Context context;

    public MapDraw(Context context) {
        this.context = context;
        initPaint();

        initValue();
    }

    private void initValue() {
        pic_left = dp2Px(12);
         pic_top = dp2Px(73);
        pic_width = dp2Px(67);
        pic_height = dp2Px(83);
        name_left = dp2Px(132);
        name_top = dp2Px(69);
    }

        private int pic_left;

        private int pic_top;

        private int pic_width ;

        private int pic_height ;


        private int name_left;

        private int name_top;


    private int dp2Px(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }


    public static class MyUserInfo {
        public Bitmap pic_bitmap;
        public String sex;
        public String name;
        public String idNumber;
        public String code;
        public String date;

        public MyUserInfo(Bitmap pic_bitmap,
                          String sex,
                          String name,
                          String idNumber,
                          String code,
                          String date) {
            this.pic_bitmap = pic_bitmap;
            this.sex = sex;
            this.name = name;
            this.idNumber = idNumber;
            this.code = code;
            this.date = date;
        }
    }

    private Paint paint;

    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
    }


    public Bitmap drawJustBitmap(Bitmap bitmap, MyUserInfo userInfo) {
        if (bitmap == null) {
            return null;
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap,null,
                new RectF(pic_left,pic_top,
                        pic_left+pic_width,
                        pic_top+pic_height),
                paint);

        canvas.drawText(userInfo.name,name_left,name_top,paint);


        return bitmap;
    }

}
