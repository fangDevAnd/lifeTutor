package com.xiaofangfang.lifetatuor.Activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.xiaofangfang.lifetatuor.R;

/**
 * 天气的搜索界面的显示效果
 */
public class WeatherSearch extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_search);
        initView();
    }

    SearchView citySearch;
    TextView searchResult;

    private void initView() {

        citySearch = findViewById(R.id.citySearch);
        citySearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //如果输入的是回车，就进行搜索

                return false;
            }
        });
    }
}
