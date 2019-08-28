package com.example.componentasystemtest.JobScheduler.simple1;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DownloadJobService extends JobService {
    //开始执行Job接口。（必须实现）如果返回false则表示这个Job已经被执行完毕。如果true则表示这个Job正在被执行。
    public boolean onStartJob(JobParameters params) {
        final int id = params.getJobId();
//            ....开始执行Job
//        对于一个瞬间能够完成的任务，此处可以return false.
//                如果是耗时任务，则需要在异步线程中执行,并且返回true。此外


        final Handler myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        myHandler.removeMessages(id);
        myHandler.sendEmptyMessage(id);
        //执行完后不要忘记执行jobFinished
        return false;
    }


    //Job执行停止，必须实现 ,当接收到任务取消时，如果该任务没有被结束，则执行该方法，否则不执行。
    public boolean onStopJob(JobParameters params) {
        //当然此处可以重新安排被取消的任务。
//        scheduler.schedule(job);
        return false;

    }

    //还有一个方法。jobFinished(JobParameters params, boolean needsRescheduled) 该方法是在任务执行完成时通知系统（不代表任务被执行成功）。needsRecheduled表示该任务是否被重复执行。
    //例如onStartJob执行结果是True。则实际上任务还在执行。这时候如果任务执行完了。就必须调用这个方法否则后面的任务。

}