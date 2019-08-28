package com.xiaofangfang.opensourceframeworkdemo.AspectJ;

import android.os.Bundle;
import android.util.Log;

import com.xiaofangfang.opensourceframeworkdemo.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AspectJMainActivity extends AppCompatActivity {


    private static final String TAG = "test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspectj);

        Log.d(TAG, "onCreate: ");

    }
}
