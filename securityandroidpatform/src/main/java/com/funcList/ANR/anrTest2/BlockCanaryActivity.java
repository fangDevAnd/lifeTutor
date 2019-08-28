package com.funcList.ANR.anrTest2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.securityandroidpatform.R;
import com.github.moduth.blockcanary.BlockCanary;

public class BlockCanaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_canary);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);
        }
        initAfterPhonePermission();


    }


    @RequiresPermission(
            allOf = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE
            })
    void initAfterPhonePermission() {
        //blockCanary框架初始化代码
        BlockCanary.install(this, new AppContext(getApplication())).start();
        new BlockThread().start();
    }


    public static class BlockThread extends Thread {

        private static final String TAG = "test";

        private static boolean isRunning;

        private PackageManager pm;

//        private final String sdcardpath;

        @Override
        public synchronized void start() {
            isRunning = true;
        }

        /**
         * 规则
         * 1.初始化运行之前先把目录下面缓存文件删除
         * 2.启动app以后，更具looptime循环查找卡顿文件，并上报log日志
         * 3.上报log日志成功后，删除日志，避免下次重新上传日志
         * 4.如果上传失败，不删除 ，继续放到sdcard下面
         */
        @Override
        public void run() {

        }
    }


}
