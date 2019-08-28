package com.annotation.annotationProcessor4;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.annotation.annotation4.BindClick;
import com.xiaofangfang.precentlayoutdemo.R;


public class Processor4Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpocessor4);
        BindClickTool.bind(this);
    }

    @BindClick(R.id.four)
    public void onClick(View view){
        Toast.makeText(this, "four被点击", Toast.LENGTH_SHORT).show();
    }

    @BindClick(R.id.five)
    public void onClick1(View view){
        Toast.makeText(this, "five被点击", Toast.LENGTH_SHORT).show();
    }





}
