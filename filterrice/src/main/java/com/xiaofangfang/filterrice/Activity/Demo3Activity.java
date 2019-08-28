package com.xiaofangfang.filterrice.Activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.xiaofangfang.filterrice.R;

public class Demo3Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo3);


        initView();
    }

    ScrollView scrollView;
    ListView listView;

    private void initView() {

        listView = findViewById(R.id.listView);
//        scrollView = findViewById(R.id.scrollView);
//
//        int height = getResources().getDisplayMetrics().heightPixels;
//        int width = getResources().getDisplayMetrics().widthPixels;

//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);


//        listView.setLayoutParams(layoutParams);


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundColor(Color.RED);

        linearLayout.setLayoutParams(new AbsListView.LayoutParams(-1, 500));


        listView.addHeaderView(linearLayout);

        String[] value = {
                "飒飒发", "sgwg", "wetww", "sywh", "二哥哥我",
                "飒飒发", "sgwg", "wetww", "sywh", "二哥哥我",
                "飒飒发", "sgwg", "wetww", "sywh", "二哥哥我",
                "飒飒发", "sgwg", "wetww", "sywh", "二哥哥我"
        };


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, value);

        listView.setAdapter(arrayAdapter);
    }


}
