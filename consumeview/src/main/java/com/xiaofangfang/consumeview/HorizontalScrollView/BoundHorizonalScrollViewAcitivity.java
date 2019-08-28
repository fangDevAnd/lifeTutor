package com.xiaofangfang.consumeview.HorizontalScrollView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiaofangfang.consumeview.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BoundHorizonalScrollViewAcitivity extends AppCompatActivity {


    BoundHorizonalScrollView bhsv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_horizonal_scrollview);


        bhsv = findViewById(R.id.scrollView);

        List<TextView> textViews = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            TextView textView = new TextView(this);
            textView.setPadding(0, 0, 0, 0);
            textView.setText("点击");
            textView.setBackgroundColor(getResources().getColor(R.color.textColor));
            textViews.add(textView);
        }
        bhsv.setViewList(textViews);


    }
}
