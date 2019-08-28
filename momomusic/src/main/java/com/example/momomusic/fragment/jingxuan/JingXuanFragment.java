package com.example.momomusic.fragment.jingxuan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.momomusic.R;
import com.example.momomusic.activity.ui.JingXuanView;
import com.example.momomusic.fragment.BaseFragment;
import com.example.momomusic.precenter.JingXuanPresenter;
import com.example.momomusic.view.Adapter.MyFragmentPageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class JingXuanFragment extends BaseFragment<JingXuanView, JingXuanPresenter> {


    private int[] tabTitle = {
            R.string.tuijian,
            R.string.diantai
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jingxuan, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private List<Fragment> fragments;

    private MyFragmentPageAdapter myPageAdapter;

    private int defaultSelectIndex = 0;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);//必须调用超类的实现

        fragments = new ArrayList<>();
        fragments.add(new JingXuanTuiJianFragment());
        fragments.add(new JingXuanDianTaiFragment());//精选

        /**
         * 记住，这里只能使用getChildFragmentManager()
         */
        myPageAdapter = new MyFragmentPageAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(myPageAdapter);
        viewPager.setCurrentItem(defaultSelectIndex);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setText(tabTitle[i]);
        }




    }

    private static final String TAG = "test";

    @Override
    public JingXuanPresenter createPresenter() {
        return null;
    }

    @Override
    public JingXuanView createView() {
        return null;
    }

    @Override
    protected void loadData() {
        Log.d(TAG, "loadData: JingXuanFragment");
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
