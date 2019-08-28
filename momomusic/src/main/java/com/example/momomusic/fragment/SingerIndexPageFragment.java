package com.example.momomusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.momomusic.R;
import com.example.momomusic.fragment.local.LocalMUsicGSFragment;
import com.example.momomusic.fragment.local.LocalMusicGQFragment;
import com.example.momomusic.fragment.local.LocalMusicWJJFragment;
import com.example.momomusic.fragment.local.LocalMusicZJFragment;
import com.example.momomusic.view.Adapter.MyFragmentPageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 歌手的首页fragment的显示
 */
public class SingerIndexPageFragment extends ParentFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_singer_index, null);
        ButterKnife.bind(this, view);
        return view;
    }


    List<Fragment> fragments;

    private String[] tabTitle;

    private MyFragmentPageAdapter myPageAdapter;

    private TabLayout tabLayout;

    private ViewPager viewPager;

    private int defaultSelectIndex;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragments = new ArrayList<>();
        fragments.add(new LocalMusicGQFragment());
        fragments.add(new LocalMUsicGSFragment());//精选
        fragments.add(new LocalMusicZJFragment());
        fragments.add(new LocalMusicWJJFragment());


        tabTitle = getResources().getStringArray(R.array.singer_zhuanlan_index);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        /**
         * 记住，这里只能使用getChildFragmentManager()
         */
        myPageAdapter = new MyFragmentPageAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(myPageAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(defaultSelectIndex);
        for (int i = 0; i < tabTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }


        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabTitle.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setText(tabTitle[i]);
        }


    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onError(IOException e, String what) {

    }

    @Override
    public void onSucess(Response response, String what, String... backData) throws IOException {

    }

    @Override
    public Class getClassName() {
        return null;
    }
}
