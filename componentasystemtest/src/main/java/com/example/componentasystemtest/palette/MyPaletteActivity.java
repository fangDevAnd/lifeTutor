package com.example.componentasystemtest.palette;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.componentasystemtest.R;


/**
 * Created by fang on 2018/6/10.
 */

public class MyPaletteActivity extends AppCompatActivity implements View.OnClickListener {

    private Button change;
    private ImageView changeImg;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo9);
        //获得效果
        demo1();
        //效果2
        demo2();
        //高度的实现
        init();


    }

    private void init() {
        Button button = (Button) findViewById(R.id.setting);
        button.setOnClickListener(this);
        //改变形状
        change = (Button) findViewById(R.id.change);
        changeImg = (ImageView) findViewById(R.id.changeImg);
        changeShape();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void changeShape() {
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 50);
            }
        };
        change.setOutlineProvider(viewOutlineProvider);
        changeImg.setOutlineProvider(viewOutlineProvider);

    }

    private void demo2() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //暗色，柔和的
                palette.getDarkMutedColor(Color.WHITE);
                //暗色的柔和的
                Palette.Swatch data1 = palette.getDarkMutedSwatch();
                //活力的，暗色
                palette.getDarkVibrantColor(Color.WHITE);
                //柔和的亮的
                palette.getLightMutedColor(Color.WHITE);
                //有活力的
                Palette.Swatch data2 = palette.getVibrantSwatch();
                //其中Palette.Swatch就是我们抽取的数据
//                data1.getBodyTextColor();
//                data1.getPopulation();
//                data1.getHsl();
//                data1.getRgb();
//                data1.getTitleTextColor();
            }
        });
    }

    private void demo1() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meizi);
        //创建Palttte对象
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onGenerated(Palette palette) {
                //通过Palette来获取对应的色调
                Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
                //将颜色设置给相应的组件
                getSupportActionBar().setBackgroundDrawable(
                        new ColorDrawable(vibrant.getRgb()));
                Window window = getWindow();
                window.setStatusBarColor(vibrant.getRgb());
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        v.animate().setDuration(5000).setInterpolator(new BounceInterpolator()).translationZ(10).rotation(360).start();
    }

}
