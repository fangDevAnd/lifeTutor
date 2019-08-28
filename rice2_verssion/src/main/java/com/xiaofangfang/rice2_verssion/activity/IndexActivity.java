package com.xiaofangfang.rice2_verssion.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaofangfang.rice2_verssion.Fragment.MainFragment;
import com.xiaofangfang.rice2_verssion.Fragment.MeFragment;
import com.xiaofangfang.rice2_verssion.Fragment.ParentFragment;
import com.xiaofangfang.rice2_verssion.Fragment.TutorFragment;
import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.model.City;
import com.xiaofangfang.rice2_verssion.tool.SystemSet;
import com.xiaofangfang.rice2_verssion.tool.Tools;
import com.xiaofangfang.rice2_verssion.view.adapter.MyFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

public class IndexActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestPermission(permissionas, REQUEST_CODE_PERMISSION);
        setContentView(R.layout.activity_index);
        super.onCreate(savedInstanceState);
    }

    private ViewPager viewPager;
    private MyFragmentPageAdapter myPageAdapter;
    private int defaultSelectIndex;
    private String tintColor = "#d81e06";
    private String primaryColor = "#111111";
    int oldIndex;


    List<ParentFragment> fragments;

    @Override
    public void initView() {

        viewPager = findViewById(R.id.viewPager);
        fragments = new ArrayList<>();
        fragments.add(new MainFragment());
        fragments.add(new TutorFragment());
        fragments.add(new MeFragment());
        myPageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myPageAdapter);
        viewPager.setOffscreenPageLimit(3);

        viewPager.setCurrentItem(defaultSelectIndex);

        View[] menuTransfer = {
                findViewById(R.id.main),
                findViewById(R.id.tutor),
                findViewById(R.id.me)
        };

        oldIndex = defaultSelectIndex;

        for (int i = 0; i < menuTransfer.length; i++) {
            int finalI = i;
            menuTransfer[i].setOnClickListener((v) -> {
                viewPager.setCurrentItem(finalI);
                Tools.setDrawableColor(Color.parseColor(primaryColor), (TextView) menuTransfer[oldIndex]);
                Tools.setDrawableColor(Color.parseColor(tintColor), (TextView) menuTransfer[finalI]);
                oldIndex = finalI;
            });
        }

        initData();
    }

    /**
     * 初始化系统的当前城市
     */
    private void initData() {
        if (city == null) {
            city = new City();
        }
        int cityId = getMyApplication().setting.getInt(SystemSet.DEFALUT_LOCATION_CITY_ID_NAME, SystemSet.DEF_LOC_PARAM_VAL);
        String cityName = getMyApplication().setting.getString(SystemSet.DEFALUT_LOCATION_CITY_NAME, SystemSet.DEF_LOC_NAME_VAL);
        int provinceId = getMyApplication().setting.getInt(SystemSet.DEFAULT_PROVINCE_ID_NAME, SystemSet.DEF_PRO_PARAM_VAL);

        city.setCityId(cityId);
        city.setName(cityName);
        city.setProvinceId(provinceId);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        fragments.get(fragments.size() - 1).user.setText(ParentActivity.getUserId(this));
    }
}
