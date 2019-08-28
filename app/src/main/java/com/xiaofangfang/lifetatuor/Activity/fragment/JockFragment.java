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

import com.xiaofangfang.lifetatuor.Activity.fragment.menu.Fragment.HotImgFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.menu.Fragment.HotJokeFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.menu.Fragment.NewImgFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.menu.Fragment.NewJokeFragment;
import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.view.adapter.MenuViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 在这里我么么需要实现的是viewLapger与viewTAb实现头部标题
 */
public class JockFragment extends Fragment implements ViewPager.OnPageChangeListener {


    private String[] tab = {"热门", "热图", "最新", "新趣图"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_joke, container,
                false);

        initView(viewGroup);

        return viewGroup;
    }

    TabLayout taglayout;
    ViewPager menu_viewpager;
    MenuViewPagerAdapter menuViewPagerAdapter;

    /**
     * 在这里我们进行子view的初始化工作
     *
     * @param viewGroup
     */
    private void initView(ViewGroup viewGroup) {
        taglayout = viewGroup.findViewById(R.id.menu_tab_layout);
        menu_viewpager = viewGroup.findViewById(R.id.menu_viewpager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HotJokeFragment());
        fragments.add(new HotImgFragment());
        fragments.add(new NewJokeFragment());
        fragments.add(new NewImgFragment());

        menuViewPagerAdapter = new MenuViewPagerAdapter(getFragmentManager(), fragments);
        menu_viewpager.setAdapter(menuViewPagerAdapter);
        menu_viewpager.addOnPageChangeListener(this);

        bindTab(taglayout, menu_viewpager);

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


    /**
     * viewPager的被滑动调用
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * viewPager被选中调用
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
    }

    /**
     * viewPager的滚动状态被改变时调用
     *
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
