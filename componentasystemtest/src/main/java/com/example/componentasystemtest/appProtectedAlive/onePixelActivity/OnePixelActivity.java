package com.example.componentasystemtest.appProtectedAlive.onePixelActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.componentasystemtest.R;

import androidx.appcompat.app.AppCompatActivity;

public class OnePixelActivity extends AppCompatActivity {

    private static final String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ScreenManager screenManager = ScreenManager.getInstance(OnePixelActivity.this);
        ScreenBroadcastListener listener = new ScreenBroadcastListener(this);
        listener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                screenManager.finishActivity();
                Log.d(TAG, "onScreenOn: ");
            }

            @Override
            public void onScreenOff() {
                screenManager.startActivity();
                Log.d(TAG, "onScreenOff: ");
            }
        });
    }
}