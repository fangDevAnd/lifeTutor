package com.xiaofangfang.filterrice.Activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xiaofangfang.filterrice.R;

import java.util.ArrayList;
import java.util.List;

public class BannerMyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup viewRoot = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.banner_new, null, false);
        setContentView(viewRoot);


        int height = getResources().getDisplayMetrics().heightPixels;
        int width = getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.banner1);
        imageList.add(R.drawable.banner2);
        imageList.add(R.drawable.banner3);
        imageList.add(R.drawable.banner4);
        imageList.add(R.drawable.banner5);

        List<String> titleList = new ArrayList<>();
        titleList.add("Java从入门到精通（第4版）");
        titleList.add("Java程序设计（慕课版）");
        titleList.add("JavaWeb程序设计（慕课版）");
        titleList.add("JSP程序设计（慕课版）");
        titleList.add("PHP程序设计（慕课版）");

//
//        BannerFlipContainer bannerFlipContainer = new BannerFlipContainer(this, imageList, titleList);

//        viewRoot.addView(bannerFlipContainer, layoutParams);
//
//        bannerFlipContainer.autoRun();


    }
}
