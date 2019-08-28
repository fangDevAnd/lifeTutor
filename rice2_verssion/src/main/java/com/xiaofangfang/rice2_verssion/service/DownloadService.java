package com.xiaofangfang.rice2_verssion.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.NotificationTool;
import com.xiaofangfang.rice2_verssion.tool.Tools;
import java.io.File;

public class DownloadService extends Service {


    public static final String URL = "url";


    private int id = 12;

    public static final String CHANNEL_ID = "342";

    private NotificationManager manager;

    private MyAsyncTask myAsyncTask;

    private File saveFile;

    private String fileName = "rice.apk";

    private String url;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(new NotificationChannel("123", "通知", NotificationManager.IMPORTANCE_HIGH));

        url = intent.getStringExtra(URL);

        saveFile = new File(DownloadService.this.getExternalCacheDir().getAbsolutePath(), fileName);

        createNoti();
        startForeground(id, notification);


        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("下载");


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }


    Notification notification;
    RemoteViews remoteViews;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createNoti() {

        NotificationTool<String> notificationTool = new NotificationTool(this) {

            @Override
            protected void bindView(Notification notification, RemoteViews remoteViews, Object[] objs) {
                DownloadService.this.remoteViews = remoteViews;
                remoteViews.setTextViewText(R.id.time, (String) objs[0]);
            }
        };
        notification = notificationTool.createNotification(CHANNEL_ID, R.layout.download_noti, Tools.dateFormat("HH:mm"));

    }

    class MyAsyncTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(DownloadService.this, "下载完成", Toast.LENGTH_SHORT).show();

            //设置NOtification的paddingIntent
            installApk(saveFile);
            //关闭服务
            stopSelf();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
            if (values[0] >= 200) {
                Toast.makeText(DownloadService.this, "发生错误", Toast.LENGTH_SHORT).show();
            } else {
                remoteViews.setProgressBar(R.id.progressBar, 100, progress, false);
                manager.notify(id, notification);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DownloadService.this, "开始下载", Toast.LENGTH_SHORT).show();
        }


        private int progress;


        @Override
        protected Void doInBackground(String... strings) {

            NetRequest.onDown(url, DownloadService.this.getExternalCacheDir().getAbsolutePath(), fileName, new NetRequest.DownListener() {
                @Override
                public void failth(Exception e) {
                    MyAsyncTask.this.progress = 200;
                    publishProgress(progress);
                }

                @Override
                public void downProgress(int progress) {
                    MyAsyncTask.this.progress = progress;
                    publishProgress(progress);
                }

                @Override
                public void success(String filePath) {

                }
            });
            return null;
        }


    }


    private void installApk(File file) {
        //新下载apk文件存储地址
        File apkFile = file;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        startActivity(intent);
        stopForeground(true);
        manager.cancel(id);//取消通知
    }


}
