package com.xiaofangfang.butterknitedemo.butter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;
import com.xiaofangfang.butterknitedemo.butter.consumeAnni.Bind;
import com.xiaofangfang.butterknitedemo.butter.consumeAnni.BindProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Bind(bindContent = R.layout.activity_butter_knifer)
public class ButterKniferActivity extends AppCompatActivity {


    @BindView(value = R.id.btn1)
    Button send1;
    @BindView(value = R.id.btn2)
    Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindProgress.bindContentView(this);
        ButterKnife.bind(this);
    }


    @OnClick(value = {
            R.id.btn2,
            R.id.btn1
    })
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Toast.makeText(this, "你點擊的是btn1", Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn2:
                Toast.makeText(this, "你點擊的是btn2", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    protected void test() {
    }

}
