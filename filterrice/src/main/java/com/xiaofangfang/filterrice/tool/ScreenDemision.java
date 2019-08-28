package com.xiaofangfang.filterrice.tool;


import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import com.xiaofangfang.filterrice.model.ImageData;

import java.util.List;

/**
 * 计算屏幕宽高
 */
public class ScreenDemision {

    /**
     * 获得屏幕的宽高
     *
     * @param context
     * @return
     */
    public static Point getScreenDemision(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        Point point = new Point();
        point.x = dm.widthPixels;
        point.y = dm.heightPixels;
        return point;
    }

    /**
     * 计算缩放比例
     *
     * @param screenWidth 屏幕的宽度
     * @param imgWidth    图片的宽度
     */
    public static PointF calculationScaleRate(int screenWidth, int imgWidth) {
        float rate;
        PointF pointF;
        if (screenWidth > imgWidth) {
            rate = screenWidth / (float) imgWidth;//放大
            pointF = new PointF(0, rate);
        } else {
            rate = imgWidth / (float) screenWidth;//缩放
            pointF = new PointF(-1, rate);
        }
        return pointF;
    }

    /**
     * 开始进行缩放
     *
     * @param imgDemision 屏幕尺寸的定义，存放的是屏幕的宽高
     * @param pointF      计算出来的相应的缩放比例,传递的x代表的是放大还是缩小
     *                    0代表放大 1,代表的是缩小
     *                    传递的y值代表的是缩放或则放大的比例
     * @return 返回计算出来的缩放的最终的值
     */
    public static Point startScale(Point imgDemision, PointF pointF) {
        if (pointF.x == 0) {
            imgDemision.x *= pointF.y;
            imgDemision.y *= pointF.y;
        }else{
            imgDemision.x /= pointF.y;
            imgDemision.y /= pointF.y;
        }
        return imgDemision;
    }


    /**
     * 该图片的list集合存放了当前视图的宽和高，我们需要计算出最大值，用来对水平的
     * 滚动视图设置height
     * 也就是计算当前视图需要的最大值
     *
     * @param imageDataList
     * @return
     */
    public static float getTotalDemision(List<ImageData> imageDataList) {

        float height = imageDataList.get(0).getHeight();
        for (int i = 0; i < imageDataList.size(); i++) {
            if (height < imageDataList.get(i).getHeight()) {
                height = imageDataList.get(i).getHeight();
            }

        }


        return height;
    }
}
