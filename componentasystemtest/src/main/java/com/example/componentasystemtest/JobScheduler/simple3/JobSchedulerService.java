package com.example.componentasystemtest.JobScheduler.simple3;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {

        Toast.makeText(this, "开始执行", Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
