package com.annotation.annotaionProcessor2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.annotation.annotaion2.BindView;
import com.xiaofangfang.precentlayoutdemo.R;
import com.annotation.annotation.BindText;


public class Processor2Activity extends AppCompatActivity {

    @BindText("ssgdsgseg")
    String name;

    @BindView(R.id.one)
    TextView one;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpocessor2);

        BindTextTool.bind(this);
        BindViewTool.bind(this);


        one.setText(name);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里回调我们的方法
                //host.fangfa(v);
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }


}
