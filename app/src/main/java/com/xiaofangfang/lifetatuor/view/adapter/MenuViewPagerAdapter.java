package com.xiaofangfang.lifetatuor.view.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * 笑话的VIewPager的适配器
 */
public class MenuViewPagerAdapter extends FragmentPagerAdapter {


    /**
     * 直接初始化参数配置为0,避免空指针
     */
    List<Fragment> fragments = new ArrayList<>();

    public MenuViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    /**
     * 菜单的显示,显示的
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container,
                            int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
