package com.xiaofangfang.lifetatuor.Activity.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;


import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.xiaofangfang.lifetatuor.Activity.MainActivity;

import com.xiaofangfang.lifetatuor.Activity.WeatherCityManager;
import com.xiaofangfang.lifetatuor.Activity.fragment.weather.Fragment.WeatherInfoFragment;
import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.controller.WeatherHandler;
import com.xiaofangfang.lifetatuor.model.LocationInfo;
import com.xiaofangfang.lifetatuor.server.interfaces.LocationCallback;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.view.adapter.WeatherViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 这个是天气的Fragment,实现是对天气的检测
 * 对于我们的天气设置的位置可能是多个,那么我们为了解决滑动的界面过多,使用
 * 下动滑动,这个视图里面的viewPager的Fragment的个数是动态加载的,
 * 在没有设置当前的位置的时候,加载首选项的数据为空.就会加载当前的位置,
 * 并保存到当前的首选项中,下次再进行加载的时候,会发现首选项里面存在数据,就不在加载当前的位置
 * 当我们设置一个位置时,修改配置文件,添加一个location
 * viewPager在加载Fragment时,会动态的为添加的地区创建一个Fragment,然后
 * 进行显示
 * <p>
 * weatherLocation
 */
public class WeatherFragment extends Fragment {


    /**
     * 在fragment不可见的时候,去初始化一些参数,查询地址信息
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateLocation();

    }

    private HashSet<String> set;
    private Set<LocationInfo> locationInfos;

    private void updateLocation() {
        //获得位置信息的集合
        set = WeatherHandler.getBindedtLocationInfo(getContext());
        if (set.size() == 0) {
            //给一个默认的显示视图"北京"  ,由于我们不写入持久层,该程序在下次寻星不会加载
            set.add("北京;海淀区;中关村");
            //代表的是没有相关的位置信息,进行位置查找与更新,斌自动将位置所在地的天气情况下载下来,保存
            WeatherHandler.locationFind(getContext(), new LocationCallback() {
                @Override
                public void locationInfo(LocationInfo info) {
                    //这里执行在子线程中
                    if (info.getCountry() == null || info.getCity() == null) {
                        //这个是在我们第一次进入,同时定位失败的情况,我们默认加载北京的数据
                    } else {
                        set.clear();
                        set.add(info.getCity()
                                + ";"
                                + info.getProvince()
                                + ";" + info.getStreet());
                        //这里解析完成,我门需要调用一个自定一方法,里面是对fragment的从新加载布局
                        Looger.d("得到位置信息" + info);
                        WeatherHandler.setBindedLacationInfo(getContext(),
                                set);
                        ((MainActivity) getActivity()).reLoadFragLayout();
                        //从新进行布局
                    }
                }
            });
        }
    }


    private void progressResponseWeatherData(String responseData) {

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_weather,
                container, false);

        initView(viewGroup);

        return viewGroup;
    }


    private ViewPager viewPager;
    private TabLayout weather_tab_layout;
    private WeatherViewPagerAdapter wvpa;
    private ImageButton weatherSetting;
    private ImageButton addCity;


    /**
     * 在这里我们使用自定义视图,在自定义的ViewGroup里面动态的添加view
     *
     * @param viewGroup
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView(ViewGroup viewGroup) {


        viewPager = viewGroup.findViewById(R.id.weather_viewPager);
        weather_tab_layout =
                viewGroup.findViewById(R.id.weather_tab_layout);

        List<Fragment> fragments = new ArrayList<>();
        List<String> cityNames = new ArrayList<>();
        //直接加载数据
        for (String info : set) {//set存放的数据是　城市　县　　街道
            LocationInfo li = new LocationInfo();
            String[] value = info.split(";");
            li.setCity(value[0]);
            li.setCountry(value[1]);
            li.setStreet(value[2]);
            fragments.add(new WeatherInfoFragment(li));
            cityNames.add(value[0]);
        }
        wvpa = new WeatherViewPagerAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(wvpa);
        viewPager.setOffscreenPageLimit(1);
        bindTab(weather_tab_layout, viewPager, cityNames);


        weatherSetting = viewGroup.findViewById(R.id.weatherSetting);
        addCity = viewGroup.findViewById(R.id.addCity);

        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里跳转到城市的管理界面
                Intent intent = new Intent(getContext(),
                        WeatherCityManager.class);
                startActivity(intent);
            }
        });


        weatherSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在里面打开一个dialog,
                if (alertDialog == null) {
                    alertDialog = (AlertDialog) createDialog();
                    initDialogComponent(alertDialog);
                }
                if (alertDialog.isShowing()) {
                    alertDialog.cancel();
                } else {
                    alertDialog.show();
                }
            }
        });


    }

    private void initDialogComponent(AlertDialog alertDialog) {
        Button audioPlay = alertDialog.findViewById(R.id.audioPlay);
        Button sharedWeather = alertDialog.findViewById(R.id.sharedWeather);
        Button weatherSetting = alertDialog.findViewById(R.id.weatherSetting);
        audioPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里进行语音的播放，播放的是当天的天气
            }
        });
        sharedWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里进行第三方的分享
            }
        });
        weatherSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入天气的设置界面的显示
                Intent intent = new Intent();

            }
        });
    }


    AlertDialog alertDialog;

    public Dialog createDialog() {
        alertDialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.weather_setting_list)
                .setCancelable(true).create();
        Window window = alertDialog.getWindow();
        window.setWindowAnimations(R.style.app_animation);
        window.setGravity(Gravity.BOTTOM);
        return alertDialog;
    }

    private void bindTab(TabLayout taglayout, ViewPager city_viewpager,
                         List<String> cityName) {

        for (int i = 0; i < cityName.size(); i++) {
            taglayout.addTab(taglayout.newTab());
        }
        taglayout.setupWithViewPager(city_viewpager);

        for (int i = 0; i < cityName.size(); i++) {
            taglayout.getTabAt(i).setText(cityName.get(i));
        }
    }

}
