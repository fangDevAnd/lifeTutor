package com.example.componentasystemtest.broadcastTest.netStatusChange;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    private static final String TAG = "test";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        Toast.makeText(this, "接收到网络状态改变的工作", Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
