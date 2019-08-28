package com.example.momomusic.fragment.zhibo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.momomusic.R;
import com.example.momomusic.activity.ui.ZhiBoView;
import com.example.momomusic.fragment.BaseFragment;
import com.example.momomusic.fragment.person.PersonalCenterFragment;
import com.example.momomusic.precenter.ZhiBoPresenter;
import com.example.momomusic.tool.Tools;
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
import butterknife.OnClick;
import okhttp3.Response;

public class ZhiBoFragment extends BaseFragment<ZhiBoView, ZhiBoPresenter> {
    private static final String TAG = "test";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_zhibo, null);
        ButterKnife.bind(this, view);
        return view;
    }


    private String[] tabTitle;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private List<Fragment> fragments;

    private MyFragmentPageAdapter myPageAdapter;

    private int defaultSelectIndex = 0;

    @BindView(R.id.personal)
    ImageButton personal;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        fragments.add(new ZhiBoTuiJianFragment());
        fragments.add(new ZhiBoNvShenFragment());//精选
        fragments.add(new ZhiBoNanShenFragment());
        fragments.add(new ZhiBoXinXiuFragment());
        fragments.add(new ZhiBoShenyinKongFragment());
        fragments.add(new ZhiBoJingWuMenFragment());
        fragments.add(new ZhiBoDuJiaFragment());
        fragments.add(new ZhiBoBeiJingFragment());
        fragments.add(new ZhiBoPKFragment());


        tabTitle = getResources().getStringArray(R.array.zhibobiaoti);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        /**
         * 记住，这里只能使用getChildFragmentManager()
         */
        myPageAdapter = new MyFragmentPageAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(myPageAdapter);
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

    @OnClick(R.id.personal)
    public void onClick(View view) {
        Tools.startActivity(getActivity(), PersonalCenterFragment.class);
    }


    @Override
    public ZhiBoPresenter createPresenter() {
        return null;
    }

    @Override
    public ZhiBoView createView() {
        return null;
    }

    @Override
    protected void loadData() {
        Log.d(TAG, "loadData: ZhiBoFragment");
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
