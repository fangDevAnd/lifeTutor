package com.xiaofangfang.butterknitedemo.eventBus;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaofangfang.butterknitedemo.R;
import com.xiaofangfang.butterknitedemo.eventBus.event.Boundle;
import com.xiaofangfang.butterknitedemo.eventBus.event.Message;

import org.greenrobot.eventbus.EventBus;

public class EventBusTwoActivity extends AppCompatActivity {

    @BindView(R.id.sendOne)
    public Button send;
    @BindView(R.id.sendTwo)
    public Button sendTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_two);
        ButterKnife.bind(this);
    }


    @OnClick(value = {
            R.id.sendOne,
            R.id.sendTwo
    })
    public void onClick(View view) {

        if (view == send) {
            //发送消息
            EventBus.getDefault().post(new Message("hello world"));
            finish();
        }
        if (view == sendTwo) {
            EventBus.getDefault().postSticky(new Boundle("我是" + getClass().getName()));
            finish();
        }
    }


}
