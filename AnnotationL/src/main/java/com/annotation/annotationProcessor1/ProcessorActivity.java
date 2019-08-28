package com.annotation.annotationProcessor1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiaofangfang.precentlayoutdemo.R;


import com.annotation.annotaion2.BindView;


public class ProcessorActivity extends AppCompatActivity {

    @BindView( R.id.one)
    public TextView one;

    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpocessor1);

        BindViewTool.bind(this);



        one.setText("form BindView");







    }







}
