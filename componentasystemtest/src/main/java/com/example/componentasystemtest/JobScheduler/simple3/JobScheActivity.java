package com.example.componentasystemtest.JobScheduler.simple3;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JobScheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JobScheduler mJobScheduler;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(12,
                    new ComponentName(getPackageName(), JobSchedulerService.class.getName()));

            builder.setPeriodic(10 * 1000); //每隔60秒运行一次
            builder.setRequiresCharging(true);
            builder.setPersisted(true); //设置设备重启后，是否重新执行任务
//            builder.setRequiresDeviceIdle(true);

            if (mJobScheduler.schedule(builder.build()) <= 0) {
                //If something goes wrong
            } else {
                Toast.makeText(this, "运行成功", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
