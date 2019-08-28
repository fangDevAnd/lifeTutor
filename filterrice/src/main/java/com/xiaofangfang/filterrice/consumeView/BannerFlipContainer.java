package com.xiaofangfang.filterrice.consumeView;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaofangfang.filterrice.R;
import com.xiaofangfang.filterrice.ViewDataBean.BannerDataRespnse;
import com.xiaofangfang.filterrice.ViewDataBean.DataResponse;
import com.xiaofangfang.filterrice.tool.UiThread;
import com.xiaofangfang.filterrice.UpdateResponse.UpdateLeavel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BannerFlipContainer extends LinearLayout implements ViewOprate {

    private List<View> points;


    BannerDataRespnse bannerDataRespnse;


    public BannerFlipContainer(Context context) {
        this(context, null);
    }


    public BannerFlipContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public BannerFlipContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setDataResponse(DataResponse dataResponse) {
        this.bannerDataRespnse = (BannerDataRespnse) dataResponse;
        intiView();
    }


    @Override
    public void updateDataResponse(DataResponse dataResponse, int leavel) {

        BannerDataRespnse bannerDataRespnse = (BannerDataRespnse) dataResponse;

        if (leavel == UpdateLeavel.updateText.getLeavle()) {

            for (int i = 0; i < bannerDataRespnse.getSingleBannerDataResponses().size(); i++) {
                BannerDataRespnse.SingleBannerDataResponse sbdr = bannerDataRespnse.getSingleBannerDataResponses().get(i);
                this.bannerDataRespnse.getSingleBannerDataResponses().get(i).setTitle(sbdr.getTitle());
            }

            return;
        } else if (leavel == UpdateLeavel.updateImgAndText.getLeavle()) {

            for (int i = 0; i < bannerDataRespnse.getSingleBannerDataResponses().size(); i++) {
                BannerDataRespnse.SingleBannerDataResponse sbdr = bannerDataRespnse.getSingleBannerDataResponses().get(i);
                this.bannerDataRespnse.getSingleBannerDataResponses().get(i).setTitle(sbdr.getTitle());
                this.bannerDataRespnse.getSingleBannerDataResponses().get(i).setUpdateImageAddress(sbdr.getUpdateImageAddress());

            }
            updateImageResource();
            return;
        } else if (leavel == UpdateLeavel.updateImgAndLink.getLeavle()) {

            for (int i = 0; i < bannerDataRespnse.getSingleBannerDataResponses().size(); i++) {
                BannerDataRespnse.SingleBannerDataResponse sbdr = bannerDataRespnse.getSingleBannerDataResponses().get(i);
                this.bannerDataRespnse.getSingleBannerDataResponses().get(i).setUpdateImageAddress(sbdr.getUpdateImageAddress());
                this.bannerDataRespnse.getSingleBannerDataResponses().get(i).setClickStartActivityName(sbdr.getClickStartActivityName());
            }


            return;
        } else if (leavel == UpdateLeavel.updateImgAndTextAndLink.getLeavle()) {

            for (int i = 0; i < bannerDataRespnse.getSingleBannerDataResponses().size(); i++) {
                BannerDataRespnse.SingleBannerDataResponse sbdr = bannerDataRespnse.getSingleBannerDataResponses().get(i);
                this.bannerDataRespnse.getSingleBannerDataResponses().get(i).setTitle(sbdr.getTitle());
                this.bannerDataRespnse.getSingleBannerDataResponses().get(i).setUpdateImageAddress(sbdr.getUpdateImageAddress());
                this.bannerDataRespnse.getSingleBannerDataResponses().get(i).setClickStartActivityName(sbdr.getClickStartActivityName());
            }
            return;
        }

    }

    /**
     * 默认的数据响应，代表的是采用的是默认的设置
     */
    @Override
    public void defaultDataResponse() {



    }


    private ViewPager viewPager;
    private List<ImageView> imageViews;
    private TextView titleTextView;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;

    private void intiView() {


        int height = getResources().getDisplayMetrics().heightPixels;
        int width = getResources().getDisplayMetrics().widthPixels;
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.bannerimglayout, null, false);

        LayoutParams layoutParams1 = new LayoutParams(width, height / 3);

        this.addView(viewGroup, layoutParams1);


        FrameLayout frameLayout = viewGroup.findViewById(R.id.fl);
        LayoutParams layoutParams = new LayoutParams(width, height / 3);
        frameLayout.setLayoutParams(layoutParams);


        //初始化图片
        imageViews = new ArrayList<>();
        List<BannerDataRespnse.SingleBannerDataResponse> singleBannerDataResponses = bannerDataRespnse.getSingleBannerDataResponses();
        for (int i = 0; i < singleBannerDataResponses.size(); i++) {
            ImageView imageView = new ImageView(getContext());

            BannerDataRespnse.SingleBannerDataResponse sbdr = singleBannerDataResponses.get(i);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(sbdr.getDefaultImageAddress());
            imageView.setOnClickListener((v) -> {

                Intent intent = new Intent(sbdr.getClickStartActivityName());
                intent.putExtra("tableName", sbdr.getTableName());
                getContext().startActivity(intent);
            });
            imageViews.add(imageView);
        }

        //加载显示的文字
        titleTextView = findViewById(R.id.title);
        titleTextView.setText(singleBannerDataResponses.get(0).getTitle());


        //加载小球
        points = new ArrayList<>();
        points.add(viewGroup.findViewById(R.id.dot_0));
        points.add(viewGroup.findViewById(R.id.dot_1));
        points.add(viewGroup.findViewById(R.id.dot_2));
        points.add(viewGroup.findViewById(R.id.dot_3));
        points.add(viewGroup.findViewById(R.id.dot_4));


        MyPageAdapter pageAdapter = new MyPageAdapter();

        viewPager = viewGroup.findViewById(R.id.vp);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }

            @Override
            public void onPageSelected(int position) {

                //设置轮播图文字
                titleTextView.setText(singleBannerDataResponses.get(position).getTitle());
                //设置当前小点图片
                points.get(position).setBackgroundResource(R.drawable.dot_focused);
                //设置前一个小点图片
                points.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                //记录小点id
                oldPosition = position;
                //记录当前位置
                currentItem = position;


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPager.setAdapter(pageAdapter);


        autoRun();//自动运行
    }


    private void updateImageResource() {
        if (bannerDataRespnse.getSingleBannerDataResponses().get(0).getUpdateImageAddress() != null) {

            for (int i = 0; i < bannerDataRespnse.getSingleBannerDataResponses().size(); i++) {

                List<BannerDataRespnse.SingleBannerDataResponse> singleBannerDataResponses = bannerDataRespnse.getSingleBannerDataResponses();

                Log.d("test", "图片的地址=" + singleBannerDataResponses.get(i).getUpdateImageAddress());
                Glide.with(getContext()).load(singleBannerDataResponses.get(i).getUpdateImageAddress()).into(imageViews.get(i));


            }
        }
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


    ScheduledExecutorService scheduledExecutorService;

    public void autoRun() {

        //初始化定时线程
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 设定执行线程计划,初始2s延迟,每次任务完成后延迟2s再执行一次任务
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);

    }

    class ViewPageTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % bannerDataRespnse.getSingleBannerDataResponses().size();
            //发送消息
            UiThread.getUiThread().post(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(currentItem);
                }
            });
        }
    }


}
