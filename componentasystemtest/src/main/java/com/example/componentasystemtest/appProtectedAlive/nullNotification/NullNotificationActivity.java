package com.example.componentasystemtest.appProtectedAlive.nullNotification;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.componentasystemtest.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NullNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_noti);

        Intent intent = new Intent(this, KeepLiveService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }

    }
}
