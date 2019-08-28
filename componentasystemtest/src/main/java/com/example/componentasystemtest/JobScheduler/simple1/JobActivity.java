package com.example.componentasystemtest.JobScheduler.simple1;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.componentasystemtest.R;

public class JobActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_activity);


        JobScheduler scheduler = this.getSystemService(JobScheduler.class);
        //或者
        scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        //如果当前的jobId已经被安排了则下取消该安排
//        final int jobId = (int) info.mId;
        int jobId = 0;
//        scheduler.cancel(jobId);
        //构建JobInfo  jobId 为jobID。DownloadJobService 为Job接受的Service，该Service必须继承JobService
        final JobInfo.Builder builder = new JobInfo.Builder(jobId, new ComponentName(this, DownloadJobService.class));//这个DownloadService是一个Serivce，具体的请查看manifest代码
        //设置设备重启是执行此任务。前提是需要拥有RECEIVE_BOOT_COMPLETED  权限
//        builder.setPriority(JobInfo.PRIORITY_FOREGROUND_APP);
        //这个设置并不能设置成为前台进程。通知还需要应用自己发。此外该设置会忽略该任务的网络限制。
//        builder.setFlags(JobInfo.FLAG_WILL_BE_FOREGROUND);
        //设置任务延迟执行时间，不可与setPeriodic(long time)同时使用
        builder.setMinimumLatency(3000);
        //设置设备执行的网络条件JobInfo.NETWORK_TYPE_UNMETERED 不计量网络（wifi），JobInfo.NETWORK_TYPE_NOT_ROAMING 非漫游网络， NETWORK_TYPE_ANY任何网络
        //JobInfo.NETWORK_TYPE_NONE 无论是否有网络都执行
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        //设置在设备充电时执行
        builder.setRequiresCharging(true);
        //设置在设备空闲时间执行
        builder.setRequiresDeviceIdle(true);
        //循环没5秒执行一次
        builder.setPeriodic(5000);
        //约定的时间内的条件都没有被触发是5秒以后开始触发
        builder.setOverrideDeadline(5000);
        //生成Job
        JobInfo job = builder.build();
        //安排Job，该方法有返回值JobScheduler.RESULT_SUCCESS 表示安排成功，JobScheduler.RESULT_FAILURE 安排失败
        scheduler.schedule(job);
        //安排Job， packageName 表示那个应用安排的Job（耗电记这个应用的）。 userId表示谁安排的Job
//        scheduler.scheduleAsPackage(builder.build(), packageName, UserHandle.myUserId(), TAG);


    }
}
