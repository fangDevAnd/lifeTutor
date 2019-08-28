package com.example.componentasystemtest.downloadService.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.componentasystemtest.R;
import com.example.componentasystemtest.downloadService.service.DownloadService;

public class DownloadFileTestActivity extends AppCompatActivity {


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_file);


        findViewById(R.id.down).setOnClickListener((v) -> {

            Intent intent = new Intent(DownloadFileTestActivity.this, DownloadService.class);
            Bundle bundle = new Bundle();
            bundle.putString(DownloadService.URL, "http://p.gdown.baidu.com/384e6ba5977db18a4c4d73f75b16011158a16a5a55b98dda289d0e10c4a046d4a561d4af7d695e0a144a4d93770f0eee882332ec5186b32b7ebbc6ec5fb8e10f8b9566608c08042480b5bd9d93ab9e38e2e6a6f7b2618a787f02f48a317f1706b24efdb7faefbcf367f92653d61743b52b7a0ec3641ef918c03b8b6d7a00f87696b8e035a411b273d71142ac7b1483f2d9a554f2a1de9941c1250aa4f4faf971f1314b8fbbe3a3e546cc44ed95e244c8874a04d3d2045a2ac4522a25937b515fc4233f889863cbb7b5948427941fce6d4517c50049d1e40e4adc72a9f2bd2c040c3d34db4753f8ebb8420f96f1a4d67c");
            intent.putExtras(bundle);
            startService(intent);

        });

    }


}
