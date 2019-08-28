package com.xiaofangfang.rice2_verssion.view;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.*;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机详情界面的banner用来展示商品的详情界面的显示
 * 本来的作用是用来进行手机展示的显示，但是考虑到实际很多的界面都使用了该界面模块，所以改变了类名，作为一个公用的模块使用
 */
public class ProductDetailBanner extends LinearLayout {


    public ProductDetailBanner(@NonNull Context context) {
        this(context, null);
    }

    public ProductDetailBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    /**
     * 外层布局
     */
    private FrameLayout frameLayout;
    /**
     * 图片的地址
     */
    private List<String> imageAddress;

    /**
     * viewpage
     */
    private ViewPager phonePage;

    private ImageButton backupPage, phoneMenu;

    /**
     * 指示器
     */
    private TextView indicator;


    private MyPageAdapter myPageAdapter;

    private List<ImageView> imageViews;


    private void initView() {


        imageAddress = new ArrayList<>();

        imageViews = new ArrayList<>();

        this.setOrientation(LinearLayout.VERTICAL);


        frameLayout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.phone_detail_banner, null, false);

        phonePage = frameLayout.findViewById(R.id.phonePage);
        backupPage = frameLayout.findViewById(R.id.backupPage);
        backupPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接关闭
                ((ParentActivity) getContext()).finish();
            }
        });
        phoneMenu = frameLayout.findViewById(R.id.phone_menu);


        indicator = frameLayout.findViewById(R.id.indicator);


        int[] screenDeminsions = Tools.getScreenDimension(getContext());
        int height = screenDeminsions[1];
        LayoutParams layoutParams = new LayoutParams(-1, height * 4 / 9);
        frameLayout.setLayoutParams(layoutParams);
        myPageAdapter = new MyPageAdapter();
        phonePage.setAdapter(myPageAdapter);
        phonePage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int pixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setText((position + 1) + "/" + imageViews.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.addView(frameLayout);

    }


    /**
     * 创建viewpage的内部image
     *
     * @param imageAdress
     */
    public void createImageViewForPage(List<String> imageAdress) {
        int count = imageAdress.size();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(getContext()).load(NetRequest.serverMain + imageAdress.get(i)).into(imageView);
            imageViews.add(imageView);
        }
        myPageAdapter.notifyDataSetChanged();
    }


    class MyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(imageViews.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }
    }


}
