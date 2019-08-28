package com.example.componentasystemtest.downloadService.activity;

import android.os.Build;
import android.os.Environment;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.componentasystemtest.R;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.DIRECTORY_DOCUMENTS;
import static android.os.Environment.DIRECTORY_MUSIC;


/**
 * 这个activity的作用是测试文件存储的文件夹
 */

public class EnvirnmentFilePathTestActivity extends AppCompatActivity {


    String test;

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 下面的测试使用的是虚拟机
         */
        Log.d("test", "路径" + Environment.getDataDirectory().getAbsolutePath());//                       /data
        Log.d("test", "路径" + Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC));//         /storage/emulated/0/Music
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d("test", "路径" + Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS));//     /storage/emulated/0/Documents
        }
        Log.d("test", "路径" + Environment.getDownloadCacheDirectory().getAbsolutePath());//              /data/cache
        Log.d("test", "路径" + Environment.getExternalStorageDirectory().getAbsolutePath());//            /storage/emulated/0/
        Log.d("test", "路径" + Environment.getRootDirectory().getAbsolutePath());//                       /system


        File file = new File("/storage/self/primary/", "c.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("test", "onCreate:=============== " + file.getAbsolutePath());


    }
}
