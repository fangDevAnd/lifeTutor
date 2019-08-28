package com.example.momomusic.view;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.momomusic.R;
import com.example.momomusic.view.Adapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

public class BannerFlip extends FrameLayout implements ViewPager.OnPageChangeListener {


    private static final String TAG = "test";

    public BannerFlip(@NonNull Context context) {
        super(context);
    }

    public BannerFlip(@NonNull Context context, AttributeSet attr) {
        super(context, attr);
        this.setPadding(padding, 0, padding, 0);
        initView();
    }


    private ViewPager viewPager;
    private List<CardView> imageViews;


    public void setBannerHeight(int height) {
        viewPager.setLayoutParams(new LayoutParams(-1, height));
    }


    private int padding = 30;

    private int cardRadius = 10;

    /**
     * 这个设置的图片的地址
     */
    public void setImageUrl(List<? extends Object> imageUrl) {

        for (Object obj : imageUrl) {
            CardView cardView = new CardView(getContext());
            cardView.setRadius(cardRadius);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            ImageView imageView = new ImageView(getContext());
//            imageView.setLayoutParams(new LayoutParams(-1, -1));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (obj instanceof String) {
                String value = (String) obj;
                Glide.with(getContext()).load(value).into(imageView);
            } else if (obj instanceof Integer) {
                int value = (int) obj;
                Glide.with(getContext()).load(value).into(imageView);
            }
            cardView.addView(imageView);
            this.imageViews.add(cardView);
        }

        mvpa.notifyDataSetChanged();

        addPoint(imageUrl.size());
    }

    private void addPoint(int size) {
        for (int i = 0; i < size; i++) {

            View view = new View(getContext());
            LinearLayout.LayoutParams ly = new LinearLayout.LayoutParams(20, 20);
            ly.gravity = Gravity.CENTER_VERTICAL;
            if (i == 0) {
                ly.leftMargin = 40;
            } else {
                ly.leftMargin = 5;
            }
            ly.bottomMargin = 20;
            view.setBackgroundResource(R.drawable.circle);
            view.setLayoutParams(ly);

            linearLayout.addView(view);
        }
        linearLayout.getChildAt(index).setBackgroundResource(R.drawable.circle_select);
        oldIndex = index;
    }

    MyViewPagerAdapter mvpa;
    LinearLayout linearLayout;

    private void initView() {
        imageViews = new ArrayList<>();
        viewPager = new ViewPager(getContext());

        viewPager.addOnPageChangeListener(this);

        viewPager.setAdapter(mvpa = new MyViewPagerAdapter<CardView>(imageViews));

        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams ly = new LayoutParams(-1, 100);
        ly.gravity = Gravity.BOTTOM | Gravity.CENTER_VERTICAL;
        linearLayout.setLayoutParams(ly);
        this.addView(viewPager);
        this.addView(linearLayout);

    }


    private Timer timer;
    int index;
    int oldIndex = -1;

    public void startAutoRoll(long delay) {

        final android.os.Handler handler = new android.os.Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    viewPager.setCurrentItem(index);
                    oldIndex = index;
                }
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                index++;
                index = index % imageViews.size();
                handler.sendEmptyMessage(0);

            }
        }, delay, delay);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (oldIndex >= 0) {
            linearLayout.getChildAt(oldIndex).setBackgroundResource(R.drawable.circle);
        }
        index = position;

        linearLayout.getChildAt(position).setBackgroundResource(R.drawable.circle_select);
        Log.d(TAG, "onPageSelected: " + position);
        oldIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
