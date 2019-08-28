package com.xiaofangfang.lifetatuor.Activity;

import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiaofangfang.lifetatuor.Activity.fragment.JockFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.NewsFragment;
import com.xiaofangfang.lifetatuor.Activity.fragment.WeatherFragment;
import com.xiaofangfang.lifetatuor.Activity.parentActivity.ParentActivity;
import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.DataConvert;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.view.adapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fang
 * @version 1.0
 * @time 2018 10.27
 */
public class MainActivity extends ParentActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static final int requestCode = 0x12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionRequest();
        initComponent();


    }

    /**
     * 请求系统需要的权限
     */
    private void permissionRequest() {
        //这个方法会得到没有请求的数据
        List<String> unobtainPermission = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            unobtainPermission = checkSelfPermission(SettingStandard.REQUEST_PERMISSION);
        }


        if (unobtainPermission.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    DataConvert.list2Array(unobtainPermission), requestCode);
        }

    }


    /**
     * 用来初始化各个控件
     */
    private void initComponent() {
        initToolbar();
        contactNavigationView();
        initDrawlayout();
        initViewPager();
        initSearch();
    }

    /**
     * 全局的搜索
     */
    ImageButton globalSearch;

    private void initSearch() {
        globalSearch = findViewById(R.id.globalSearch);
        globalSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    ViewPager viewPager;
    MyViewPagerAdapter mypa;

    private void initViewPager() {
        viewPager = findViewById(R.id.content_viewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = initFragment();
        mypa = new MyViewPagerAdapter(fragmentManager, fragments);

        //设置预加载的数量为1
        viewPager.setOffscreenPageLimit(1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面被滚动的回调
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset, int positionOffsetPixels) {

            }

            //页面被选中
            @Override
            public void onPageSelected(int position) {
                //在这里我们将对应的图标进行渲染
                changeIntercationIcon(position);
            }

            //页面滚动状态被改变
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(mypa);
    }

    /**
     * 设置指示的图标动态改变颜色，
     *
     * @param position
     */
    private void changeIntercationIcon(int position) {//0

        Looger.d("position=" + position);
        switch (position) {
            case 0:
                resetIcon(position);
                menu_icon.setImageResource(R.drawable.joke_red);
                break;
            case 1:
                resetIcon(position);
                weather_icon.setImageResource(R.drawable.weather_red);
                break;
            case 2:
                resetIcon(position);
                news_icon.setImageResource(R.drawable.new_red);
                break;
        }


    }

    private void resetIcon(int position) {
        switch (position) {
            case 0:
                weather_icon.setImageResource(R.drawable.weather);
                news_icon.setImageResource(R.drawable.news);
                break;
            case 1:
                menu_icon.setImageResource(R.drawable.joke);
                news_icon.setImageResource(R.drawable.news);
                break;
            case 2:
                menu_icon.setImageResource(R.drawable.joke);
                weather_icon.setImageResource(R.drawable.weather);
                break;
        }
    }

    /**
     * 初始化Fragment
     *
     * @param
     */
    private List<Fragment> initFragment() {

        JockFragment jockFragment = new JockFragment();
        NewsFragment newsFragment = new NewsFragment();
        WeatherFragment weatherFragment = new WeatherFragment();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(jockFragment);
        fragmentList.add(weatherFragment);
        fragmentList.add(newsFragment);

        return fragmentList;

    }

    DrawerLayout drawerLayout;

    private void initDrawlayout() {
        drawerLayout = findViewById(R.id.drawerlayout);
    }

    NavigationView navigationView;

    private void contactNavigationView() {

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    Toolbar toolbar;
    ImageView news_icon;
    ImageView weather_icon;
    ImageView menu_icon;

    private void initToolbar() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.upmenu_red));

        news_icon = findViewById(R.id.news_icon);
        news_icon.setOnClickListener(this);
        weather_icon = findViewById(R.id.weather_icon);
        weather_icon.setOnClickListener(this);
        menu_icon = findViewById(R.id.menu_icon);
        menu_icon.setOnClickListener(this);

    }

    /**
     * 导航菜单被选中时的回调
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {


        }

        /**
         * 操作之后都要关闭这个Drawer
         */
        drawerLayout.closeDrawer(Gravity.START);


        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                if (drawerLayout.isDrawerOpen(Gravity.START)) {

                } else {
                    drawerLayout.openDrawer(Gravity.START);
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 这个方法的主要功能是对toolbar上面的图标进行点击实现
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_icon:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.weather_icon:
                viewPager.setCurrentItem(1, true);

                break;
            case R.id.news_icon:
                viewPager.setCurrentItem(2, true);
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MainActivity.requestCode) {
            for (int grantResult : grantResults) {
                if (PackageManager.PERMISSION_DENIED == grantResult) {
                    Toast.makeText(this, "系统权限没有授予完成,请退出授予",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            //没有结束掉,代表的就是执行成功

        }
    }


    /**
     * 重新加载fragment布局
     */
    public void reLoadFragLayout() {

        //移除天气fragment  index===1
        mypa.removeFragment(1);
        /*从FragmentManager中移除*/
        mypa.addFragment(1, new WeatherFragment());


    }


}
