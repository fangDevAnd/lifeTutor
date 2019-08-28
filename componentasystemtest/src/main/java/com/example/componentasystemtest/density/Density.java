package com.example.componentasystemtest.density;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;

import android.util.Log;
import android.util.TypedValue;

/**
 * Created by fang on 2018/6/7.
 * 用于实现像素之间的转换
 */

public class Density extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int density = (int) getResources().getDisplayMetrics().density;//获得dpi转换px的换算比例
        int densityDpi = getResources().getDisplayMetrics().densityDpi;//得到当前手机的dpi值
        int piexsY = getResources().getDisplayMetrics().heightPixels;//获得高的像素点
        int piexsX = getResources().getDisplayMetrics().widthPixels;//获得宽的像素点
        int scaleDensity = (int) getResources().getDisplayMetrics().scaledDensity;
        int xdpi = (int) getResources().getDisplayMetrics().xdpi;//取得的就是densityDpi
        int ydpi = (int) getResources().getDisplayMetrics().ydpi;//取得的就是densityDpi
        Log.d("test", "density=" + density + ";   densityDpi=" + densityDpi + ";   piexsY=" + piexsY + ";   piexsX=" + piexsX + ";  scaleDeinsity=" + scaleDensity + ";    xdpi=" + xdpi + ";    ydpi=" + ydpi);
    }






}
