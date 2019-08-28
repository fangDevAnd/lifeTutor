package com.xiaofangfang.rice2_verssion.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.network.NetRequest;

import java.io.File;

public class BaseService extends Service {


    private MyAsyncTask myAsyncTask;
    private boolean isExecute = false;
    private File root;
    private String fileName = "filterRice.apk";

    @Override
    public void onCreate() {
        super.onCreate();
        root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (isExecute) {
            return super.onStartCommand(intent, flags, startId);
        }


        String url = intent.getStringExtra("url");

        myAsyncTask = new MyAsyncTask(url, "开始执行");

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int progress;
    private String filePath;


    public class MyAsyncTask extends AsyncTask<String, Integer, Void> {

        private String url;
        private String perTip;

        public MyAsyncTask(String url, String preTip) {
            this.url = url;
            this.perTip = preTip;
        }


        @Override
        protected Void doInBackground(String... strings) {

            NetRequest.onDown(url, root.getAbsolutePath(), fileName, new NetRequest.DownListener() {
                @Override
                public void failth(Exception e) {
                    e.printStackTrace();
                    Message message = handler.obtainMessage();
                    message.what = 0045;
                }

                @Override
                public void downProgress(int progress) {
                    BaseService.this.progress = progress;
                }

                @Override
                public void success(String filePath) {
                    BaseService.this.filePath = filePath;
                }
            });

            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(BaseService.this, "开始下载", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void v) {
            Toast.makeText(BaseService.this, "文件下载成功", Toast.LENGTH_LONG).show();


            super.onPostExecute(v);
        }

    }


    private Handler handler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0045) {
                Toast.makeText(BaseService.this, "下载失败", Toast.LENGTH_LONG).show();
            }
        }
    };


}
