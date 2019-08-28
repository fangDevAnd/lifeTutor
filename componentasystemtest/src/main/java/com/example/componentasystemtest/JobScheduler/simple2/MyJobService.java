package com.example.componentasystemtest.JobScheduler.simple2;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    private static final String TAG = "test";
    private int messageID = 0x134;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }


    private Messenger messenger;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");

        messenger = intent.getParcelableExtra(JobSchedulerActivity.MESSENGER_INTENT_KEY);

        return START_NOT_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {

        /**
         * 这个params类似我们JobInfo.Builder，通过getExtras可以获得PersistableBundle也就是我们存放的数据的地方
         */
        long duration = params.getExtras().getLong(JobSchedulerActivity.WORK_DURATION_KEY);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage(messageID, params.getJobId());

                /*
                当wantsReschedule参数设置为true时，表示任务需要另外规划时间进行执行。
                而这个执行的时间受限与JobInfo的退避规则。
                */
                jobFinished(params, false);
            }
        }, duration);

        /**
         该方法返回false结果表示该任务已经全部做完，此时系统会解绑该JobService，
         最终会调用JobService的onDestroy()方法，
         效果就如同自己调用了jobFinished()方法一样

         返回 true 结果则表示任务已经启动成功，但还没有全部做完，
         此时可以在任务完成后，应用自行调用jobFinished方法。
         */
        return true;
    }

    private void sendMessage(int messageID, Object params) {
        Message message = Message.obtain();
        message.what = messageID;
        message.obj = params;
        if (messenger != null) {
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    返回 true 表示：“任务应该计划在下次继续。”
    返回 false 表示：“不管怎样，事情就到此结束吧，不要计划下次了。”
     */
    @Override
    public boolean onStopJob(JobParameters params) {

        sendMessage(messageID, params.getJobId());
        //返回false代表去drop这个job
        return false;
    }
}
