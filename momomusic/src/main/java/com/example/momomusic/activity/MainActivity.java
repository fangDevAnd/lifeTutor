package com.example.momomusic.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.momomusic.R;
import com.example.momomusic.activity.ui.MainView;
import com.example.momomusic.fragment.MusicPlayFragment;
import com.example.momomusic.fragment.jingxuan.JingXuanFragment;
import com.example.momomusic.fragment.MeFragment;
import com.example.momomusic.fragment.shiping.ShiPinFragment;
import com.example.momomusic.fragment.zhibo.ZhiBoFragment;
import com.example.momomusic.precenter.MainPresenter;
import com.example.momomusic.tool.Tools;
import com.example.momomusic.view.Adapter.MyFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 *当前
 */
public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {


    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> fragments;

    private int defaultSelectIndex = 0;

    private MyFragmentPageAdapter myPageAdapter;

    private String tintColor = "#d81e06";
    private String primaryColor = "#111111";

    private int oldIndex;

    @BindView(R.id.my)
    Button my;
    @BindView(R.id.jingxuan)
    Button jingxuan;
    @BindView(R.id.zhibo)
    Button zhidao;
    @BindView(R.id.shipin)
    Button shipin;


    @BindView(R.id.bofang)
    ImageButton bofang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter();
//        DaggerMainActivityComponent.create().inject(this);
        ButterKnife.bind(this);
        initView();
        requestPermission(permissionas, REQUEST_CODE_PERMISSION);
    }

    private void initView() {

        Button[] buttons = {
                my, jingxuan, zhidao, shipin
        };

        Tools.setDrawableColor(Color.parseColor(tintColor), buttons[defaultSelectIndex]);

        for (int i = 0; i < buttons.length; i++) {

            int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI);
                    Tools.setDrawableColor(Color.parseColor(primaryColor), buttons[oldIndex]);
                    Tools.setDrawableColor(Color.parseColor(tintColor), buttons[finalI]);
                    oldIndex = finalI;
                }
            });
        }


        fragments = new ArrayList<>();
        fragments.add(new MeFragment());
        fragments.add(new JingXuanFragment());//精选
        fragments.add(new ZhiBoFragment());//直播
        fragments.add(new ShiPinFragment());//视频
        myPageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myPageAdapter);
        viewPager.setOffscreenPageLimit(4);//设置页面缓存为4个，但是使用了懒加载机制
        viewPager.setCurrentItem(defaultSelectIndex);

        oldIndex = defaultSelectIndex;

    }


    @Override
    public MainView createView() {
        return this;
    }

    @Override
    public MainPresenter createPresenter() {
        return mainPresenter;
    }


    @OnClick(
            value = {R.id.bofang,}
    )
    public void menuClick(View view) {
        switch (view.getId()) {

            case R.id.bofang:
                Tools.startActivity(this, MusicPlayFragment.class);
                break;
        }
    }


    /**
     * 当请求权限成功的时候执行  ，当然在已经获得权限的情况下，依然执行这个函数，这个可以保证我们在获得权限成功的情况下做一些操作
     * 保证软件的正常运行
     *
     * @param requestCode
     */
    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);

        /**
         *  在这个函数的目的
         *
         *  1.判断是否开启了自动进入播放界面的开关
         *  2.如果开启了----> 判断当前的歌曲是否在播放，通过启动service，通过ACTION==ISPLAY判断
         *
         */





    }
}
