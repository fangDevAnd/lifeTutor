package com.xiaofangfang.lifetatuor.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.controller.WeatherHandler;

import java.util.ArrayList;
import java.util.Set;

public class WeatherCityManager extends AppCompatActivity {


    Set<String> citySet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_city_list);
        initView();
        //访问文件查找
        initData();

    }

    ListView cityList;
    FloatingActionButton floatButton;

    private void initView() {
        cityList = findViewById(R.id.citylist);
    }

    private void initData() {
        citySet = WeatherHandler.getBindedtLocationInfo(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(citySet));
        cityList.setAdapter(arrayAdapter);
        cityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {

                return true;  //直接进行拦截
            }
        });

        floatButton = findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里跳转到添加城市的界面
                Intent intent = new Intent(WeatherCityManager.this
                        , WeatherSearch.class);

            }
        });
    }


}
