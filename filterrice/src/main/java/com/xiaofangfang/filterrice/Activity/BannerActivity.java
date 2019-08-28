package com.xiaofangfang.filterrice.Activity;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaofangfang.filterrice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BannerActivity extends AppCompatActivity {
    //轮播图布局容器
    private FrameLayout fl;
    //轮播图控件
    private ViewPager mViewPaper;
    //轮播图图片集合
    private List<ImageView> images;

    //存放轮播图显示图片
    private int[] imageIds = new int[]{
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4,
            R.drawable.banner5
    };
    //存放图片的标题
    private String[] titles = new String[]{
            "Java从入门到精通（第4版）",
            "Java程序设计（慕课版）",
            "JavaWeb程序设计（慕课版）",
            "JSP程序设计（慕课版）",
            "PHP程序设计（慕课版）"
    };
    //轮播图显示文字控件
    private TextView title;
    //轮播图点集合
    private List<View> dots;
    //记录当前跳转的轮播图ID
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;

    //轮播图适配器
    private ViewPagerAdapter adapter;
    //执行定时任务
    private ScheduledExecutorService scheduledExecutorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fl = (FrameLayout)findViewById(R.id.fl);
        //获取屏幕宽高
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 屏幕宽度（像素）
        int width = metric.widthPixels;
        //设置布局容器宽度
        fl.getLayoutParams().width=width;
        //设置布局容器的高度
        fl.getLayoutParams().height=width/2;


        //声明轮播图控件
        mViewPaper = (ViewPager) findViewById(R.id.vp);

        //初始化显示的图片
        images = new ArrayList<ImageView>();
        //添加图片到图片集合
        for (int i = 0; i < imageIds.length; i++) {
            //初始化控件
            ImageView imageView = new ImageView(this);
            //设置图片背景
            imageView.setBackgroundResource(imageIds[i]);
            //图片添加到集合
            images.add(imageView);
        }

        //初始化轮播图文字
        title = (TextView) findViewById(R.id.title);
        //设置轮播图显示文字
        title.setText(titles[0]);

        //小点集合
        dots = new ArrayList<View>();
        //初始化小点添加到集合
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        //初始化适配器
        adapter = new ViewPagerAdapter();
        //轮播图绑定适配器
        mViewPaper.setAdapter(adapter);
        //轮播图滑动事件监听
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面切换后触发
            @Override
            public void onPageSelected(int position) {
                //设置轮播图文字
                title.setText(titles[position]);
                //设置当前小点图片
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                //设置前一个小点图片
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                //记录小点id
                oldPosition = position;
                //记录当前位置
                currentItem = position;
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
    // 自定义Adapter
    private class ViewPagerAdapter extends PagerAdapter {
        //返回页卡的数量
        @Override
        public int getCount() {
            return images.size();
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;//官方提示这样写
        }
        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
            view.removeView(images.get(position)) ;//删除页卡
        }
        //这个方法用来实例化页卡
        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            //添加图片控件到轮播图控件
            view.addView(images.get(position));
            return images.get(position);
        }
    }
    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        //初始化定时线程
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 设定执行线程计划,初始2s延迟,每次任务完成后延迟2s再执行一次任务
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }
    private class ViewPageTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            //发送消息
            mHandler.sendEmptyMessage(0);
        }
    }
    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

}
