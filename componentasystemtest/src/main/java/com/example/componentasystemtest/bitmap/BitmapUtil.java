package com.example.componentasystemtest.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by Administrator on 2017/1/24 0024.
 */

public class BitmapUtil {

    //图片灰度化处理
    public static Bitmap huidu(Bitmap bmSrc) {
        // 得到图片的长和宽
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        // 创建目标灰度图像
        Bitmap bmpGray = null;
        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        // 创建画布
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }
    // 怀旧效果
    public static Bitmap huaijiu(Bitmap bmp)
    {
//        // 速度测试
//        long start = System.currentTimeMillis();
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        int pixColor = 0;
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++)
        {
            for (int k = 0; k < width; k++)
            {
                pixColor = pixels[width * i + k];
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
                newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
                newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
                int newColor = Color.argb(255, newR > 255 ? 255 : newR, newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
                pixels[width * i + k] = newColor;
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        long end = System.currentTimeMillis();
        return bitmap;
    }
    //冰冻效果
    public static Bitmap ice(Bitmap bmp) {

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int dst[] = new int[width * height];
        bmp.getPixels(dst, 0, width, 0, 0, width, height);

        int R, G, B, pixel;
        int pos, pixColor;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pos = y * width + x;
                pixColor = dst[pos]; // 获取图片当前点的像素值
                R = Color.red(pixColor); // 获取RGB三原色
                G = Color.green(pixColor);
                B = Color.blue(pixColor);
                pixel = R - G - B;
                pixel = pixel * 3 / 2;
                if (pixel < 0)
                    pixel = -pixel;
                if (pixel > 255)
                    pixel = 255;
                R = pixel; // 计算后重置R值，以下类同
                pixel = G - B - R;
                pixel = pixel * 3 / 2;
                if (pixel < 0)
                    pixel = -pixel;
                if (pixel > 255)
                    pixel = 255;
                G = pixel;

                pixel = B - R - G;
                pixel = pixel * 3 / 2;
                if (pixel < 0)
                    pixel = -pixel;
                if (pixel > 255)
                    pixel = 255;
                B = pixel;
                dst[pos] = Color.rgb(R, G, B); // 重置当前点的像素值
            } // x
        } // y
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(dst, 0, width, 0, 0, width, height);
        return bitmap;
    }

}
