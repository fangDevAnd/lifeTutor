package com.example.componentasystemtest.JobScheduler.simple2;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.example.componentasystemtest.R;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class JobSchedulerActivity extends AppCompatActivity {


    public static final String WORK_DURATION_KEY = "work";
    public static final String TAG = "test";
    public static final String MESSENGER_INTENT_KEY = "messenger";
    int id = 0x12;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobscheduler);


        JobInfo.Builder builder = new JobInfo.Builder(id,
                new ComponentName(this, MyJobService.class));
        //设置至少延迟多久才执行
        builder.setMinimumLatency(3000);
        //设置最多延迟多久后才执行
        builder.setOverrideDeadline(5000);
        //需要的网络类型
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        //设备是否是在空闲的时候执行
        builder.setRequiresDeviceIdle(false);
        //设置执行是在电量不低的情况下执行
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setRequiresBatteryNotLow(true);
        }
        //设置是否在充电的情况下执行
        builder.setRequiresCharging(true);
        //
        PersistableBundle bundle = new PersistableBundle();
        bundle.putLong(WORK_DURATION_KEY, 1000);//工作持续时间
        builder.setExtras(bundle);

        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(builder.build());
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage:---------->接受到消息");
        }
    };


    public void onClick(View view) {
        Intent intent = new Intent(this, MyJobService.class);
        Messenger messenger = new Messenger(handler);
        intent.putExtra(MESSENGER_INTENT_KEY, messenger);
        startService(intent);
    }
}
