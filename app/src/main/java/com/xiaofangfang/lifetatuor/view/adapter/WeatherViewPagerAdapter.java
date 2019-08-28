package com.xiaofangfang.lifetatuor.view.adapter;


import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class WeatherViewPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fm;
    private List<Fragment> fragmentList;

    public WeatherViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragmentList = fragments;
    }



    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
