package com.lyc.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 *
 *
 * bitmap相关的操作
 */

public class BitmapUtil {

    /**
     * 旋转
     * @param bm
     * @param degrees
     * @return
     */
    public static Bitmap rotate(Bitmap bm, float degrees){
        Bitmap res;
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        res = Bitmap.createBitmap(bm, 0, 0,bm.getWidth(),
                bm.getHeight(), matrix, true);

        return res;
    }

    /**
     * 根据高度缩放
     * @param bm
     * @param height
     * @return
     */
    public static Bitmap scaleByHeight(Bitmap bm,int height){
        Bitmap res;
        float zoom=height/bm.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(zoom, zoom);
        res = Bitmap.createBitmap(bm, 0, 0,bm.getWidth(),
                bm.getHeight(), matrix, true);

        return res;
    }


    /**
     * 垂直拼接两个bitmap
     * @param b1
     * @param b2
     * @return
     */
    public static Bitmap compairVertical(Bitmap b1, Bitmap b2){
        int width=b1.getWidth()>b2.getWidth()?b1.getWidth():b2.getWidth();
        int height=b1.getHeight()+b2.getHeight();
        Bitmap res=Bitmap.createBitmap(width, height, Config.RGB_565);

        Canvas c = new Canvas(res);
        c.drawBitmap(b1, 0, 0, null);
        c.drawBitmap(b2, 0, b1.getHeight(), null);

        return res;
    }

    /**
     *保存文件
     * @param bm
     * @param path
     */
    public static  void saveBitmap(Bitmap bm,String path) {

        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }

        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
