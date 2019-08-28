package com.example.componentasystemtest.appProtectedAlive.jobScheduler;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.componentasystemtest.JobScheduler.simple2.JobSchedulerActivity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class JobActivity extends AppCompatActivity {


    private static final String TAG = "test";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startJobSheduler();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startJobSheduler() {
        try {

            ComponentName componentName = new ComponentName(JobActivity.this, MyJobService.class.getName());
            JobInfo.Builder builder;
            builder = new JobInfo.Builder(12, componentName);
            //在7.0调用setPeriodic()之后在获取间隔时间getIntervalMillis() 强制使用了最小时间15分钟。所以想通过setPeriodic()来设置小于15分钟间隔是不行的。所以如果小于15分钟需要通过设置setMinimumLatency ()
            builder.setPeriodic(5000);
            builder.setMinimumLatency(5000);
            JobInfo jobInfo;
            jobInfo = builder.build();

            JobScheduler jobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(jobInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public class MyJobService extends JobService {

        @Override
        public void onCreate() {
            super.onCreate();
            startJobSheduler();

            Log.d(TAG, "oncreateJob: ");
        }


        @Override
        public boolean onStartJob(JobParameters jobParameters) {
            Log.d(TAG, "onStartJob: ");
            return false;
        }

        @Override
        public boolean onStopJob(JobParameters jobParameters) {
            Log.d(TAG, "onStopJob: ");
            return false;
        }
    }


}
