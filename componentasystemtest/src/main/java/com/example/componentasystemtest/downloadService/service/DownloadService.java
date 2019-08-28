package com.example.componentasystemtest.downloadService.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.componentasystemtest.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends Service {


    int id = 12;

    NotificationManager manager;

    MyAsyncTask myAsyncTask;


    public static final String URL = "url";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String url = intent.getExtras().getString(URL);

        if (url == null) {
            throw new IllegalArgumentException("loss param url");
        }

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("123", "通知", NotificationManager.IMPORTANCE_HIGH));
        }

        createNoti();
        startForeground(12, builder.build());

        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("下载");

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    private int progress;

    private Notification.Builder builder;

    private String url;

    public void createNoti() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.rice)
                    .setChannelId("123")
                    .setContentText("正在下载" + progress + "%")
                    .setProgress(100,
                            progress,
                            false);
        } else {
            builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.rice)
                    .setContentText("正在下载" + progress + "%")
                    .setProgress(100,
                            progress,
                            false);
        }
    }

    class MyAsyncTask extends AsyncTask<String, Integer, Void> {


        /**
         * 4 最后执行
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(DownloadService.this, "下载完成", Toast.LENGTH_SHORT).show();
            manager.cancel(id);
            if (file != null && file.exists()) {
                //点击安装代码块
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse(file.toString()), "application/vnd.android.package-archive");
                startActivity(intent);
            }
        }


        /**
         * 3 执行
         *
         * @param values
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            builder.setProgress(100, values[0], false)
                    .setContentText("正在下载" + progress + "%");
            manager.notify(id, builder.build());
        }

        /**
         * 1 执行
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DownloadService.this, "开始下载", Toast.LENGTH_SHORT).show();
        }

        private String name = "南昌人道.apk";


        private File file;

        /**
         * 2   执行
         *
         * @param strings
         * @return
         */
        @Override
        protected Void doInBackground(String... strings) {

            int total;
            BufferedInputStream bufi = null;
            BufferedOutputStream bfo = null;

            try {
                URLConnection connection = new URL("http://p.gdown.baidu.com/384e6ba5977db18a4c4d73f75b16011158a16a5a55b98dda289d0e10c4a046d4a561d4af7d695e0a144a4d93770f0eee882332ec5186b32b7ebbc6ec5fb8e10f8b9566608c08042480b5bd9d93ab9e38e2e6a6f7b2618a787f02f48a317f1706b24efdb7faefbcf367f92653d61743b52b7a0ec3641ef918c03b8b6d7a00f87696b8e035a411b273d71142ac7b1483f2d9a554f2a1de9941c1250aa4f4faf971f1314b8fbbe3a3e546cc44ed95e244c8874a04d3d2045a2ac4522a25937b515fc4233f889863cbb7b5948427941fce6d4517c50049d1e40e4adc72a9f2bd2c040c3d34db4753f8ebb8420f96f1a4d67c").openConnection();

                connection.setDoInput(true);
                connection.setReadTimeout(8000);
                connection.connect();

                InputStream is = connection.getInputStream();
                total = connection.getContentLength();
                bufi = new BufferedInputStream(is);

                bfo = new BufferedOutputStream(new FileOutputStream(
                        file = new File(getExternalCacheDir(), name)
                ));

                if (!file.exists()) {
                    file.createNewFile();
                    Log.d("test", "doInBackground: ==============开始创建文件");
                }


                byte[] b = new byte[1024];
                int len;

                int pro = 0;


                while ((len = bufi.read(b, 0, b.length)) != -1) {
                    bfo.write(b, 0, len);

                    pro += len;

                    progress = (int) (pro * 1.0f / total * 100);

                    Log.d("test", "当前进度= " + progress);
                    publishProgress(progress);
                }

                bfo.flush();

            } catch (IOException e) {
                publishProgress(2000);
                e.printStackTrace();
            } finally {

                try {
                    if (bfo != null) {
                        bfo.close();
                    }
                    if (bufi != null) {
                        bufi.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }


}
