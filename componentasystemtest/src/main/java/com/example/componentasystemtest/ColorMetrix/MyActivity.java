package com.example.componentasystemtest.ColorMetrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.componentasystemtest.R;


/**
 * Created by fang on 2018/6/8.
 */

public class MyActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    SeekBar hue;//色调
    SeekBar saturation;//亮度
    SeekBar lum;//饱和度
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo5);


        hue = (SeekBar) findViewById(R.id.hue);
        hue.setOnSeekBarChangeListener(this);
        img = (ImageView) findViewById(R.id.img);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d("test", "当前的进度为" + progress);
        progressTest();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meizi);


    }

    private void progressTest() {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
