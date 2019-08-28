package com.example.momomusic.view.Adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MyViewPagerAdapter<T extends View> extends PagerAdapter {

    private List<T> viewList;

    public MyViewPagerAdapter(List<T> viewList) {
        if (viewList == null) {
            this.viewList = new ArrayList<>();
        }
        this.viewList = viewList;
    }

    public void setViewList(List<T> viewList) {
        this.viewList = viewList;
    }

    public List<T> getViewList() {
        return viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewList.get(position));
    }
}