package com.xiaofangfang.lifetatuor.Activity.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment.DomesticFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment.EconomicsFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment.FashionFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment.InternationalFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment.MilitaryFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment.ScienceFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment.SociologyFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment.SportsFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment.TopFragment;
import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.view.adapter.MenuViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements ViewPager.OnPageChangeListener {


    private String[] tab = {"头条", "社会", "国内", "国际", "娱乐",
            "体育", "军事", "科技", "财经", "时尚"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //我们应该注意的是,每一个fragment都是有生命周期的,在生命周期之后,必然都会销毁4
        // ,所以我们不应该在这里发送网络请求,因为我们不知道当前界面的内层的Fragment哪一个
        //被显示,所以我们应该在每一个Fragment里面发送请求


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_news, container, false);

        initView(viewGroup);


        return viewGroup;
    }


    TabLayout taglayout;
    ViewPager news_viewpager;
    MenuViewPagerAdapter newsViewPagerAdapter;

    /**
     * 在这里我们进行子view的初始化工作
     *
     * @param viewGroup
     */
    private void initView(ViewGroup viewGroup) {
        taglayout = viewGroup.findViewById(R.id.news_tab_layout);
        news_viewpager = viewGroup.findViewById(R.id.news_viewpager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TopFragment());//添加头条
        fragments.add(new SociologyFragment());//添加社会
        fragments.add(new DomesticFragment());//国内新闻
        fragments.add(new InternationalFragment());//国际新闻
        fragments.add(new InternationalFragment());//娱乐新闻
        fragments.add(new SportsFragment());//体育新闻
        fragments.add(new MilitaryFragment());//军事新闻
        fragments.add(new ScienceFragment());//科技新闻
        fragments.add(new EconomicsFragment());//财经新闻
        fragments.add(new FashionFragment());//时尚新闻
        newsViewPagerAdapter = new MenuViewPagerAdapter(getFragmentManager(), fragments);
        news_viewpager.setAdapter(newsViewPagerAdapter);
        news_viewpager.addOnPageChangeListener(this);
        //设置预加载数量为1
        news_viewpager.setOffscreenPageLimit(1);
        bindTab(taglayout, news_viewpager);

    }

    private void bindTab(TabLayout taglayout, ViewPager menu_viewpager) {

        for (int i = 0; i < tab.length; i++) {
            taglayout.addTab(taglayout.newTab());
        }
        taglayout.setupWithViewPager(menu_viewpager);

        for (int i = 0; i < taglayout.getTabCount(); i++) {
            taglayout.getTabAt(i).setText(tab[i]);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
