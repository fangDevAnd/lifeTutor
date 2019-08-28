package com.xiaofangfang.pluginFramework.virtualApk.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;
import com.xiaofangfang.pluginFramework.R;

import java.io.File;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "test";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 加载plugin.apk插件包
        PluginManager pluginManager = PluginManager.getInstance(this);

        Log.d(TAG, "filePath: " + getExternalStorageDirectory().getAbsolutePath());

        File apk = new File(getExternalStorageDirectory(), "rice_plugin.apk");//
        if (apk.exists()) {
            Toast.makeText(this, "文件存在", Toast.LENGTH_SHORT).show();
            try {
                pluginManager.loadPlugin(apk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }

        initView();
    }

    private void initView() {
        findViewById(R.id.startButton).setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.setClassName("com.xiaofangfang.rice2_verssion.activity",
                    "com.xiaofangfang.rice2_verssion.activity.IndexActivity");
            startActivity(intent);
        });
    }


}

