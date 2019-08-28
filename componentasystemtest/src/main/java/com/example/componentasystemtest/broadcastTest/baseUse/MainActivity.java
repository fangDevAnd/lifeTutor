package com.example.componentasystemtest.broadcastTest.baseUse;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.componentasystemtest.R;
import com.example.componentasystemtest.broadcastTest.netStatusChange.NetChangeBroadcast;
import com.example.componentasystemtest.tools.Looger;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // dynamicBroadcast();

//        dynamicLocalBroadcast();

        setContentView(R.layout.broadcast_test);

        findViewById(R.id.sendNormal).setOnClickListener((v) -> {

            //发送一个广播
            Intent intent = new Intent("com.example.componentasystemtest.broadcastTest");
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                intent.setComponent(new ComponentName(this.getPackageName(), "com.example.componentasystemtest.broadcastTest"));
            }
            Looger.d("发送广播");
            sendBroadcast(intent);


        });


        findViewById(R.id.sendLocal).setOnClickListener((v) -> {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(new Intent("com.example.componentasystemtest.broadcastTest"));//发送本地广播
        });


        findViewById(R.id.sendOrder).setOnClickListener((V) -> {
            //发送有序广播
            Intent intent = new Intent("com.example.componentasystemtest.broadcastTest");
            sendOrderedBroadcast(intent, "LifeTatour");
        });
    }


    private void dynamicLocalBroadcast() {
        /**
         * 下边的代码是动态的注册了一个本地广播
         */
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        NetChangeBroadcast activityTest = new NetChangeBroadcast();
        localBroadcastManager.registerReceiver(activityTest, new IntentFilter("com.example.componentasystemtest.broadcastTest"));
    }

    private void dynamicBroadcast() {
        NetChangeBroadcast braodcast = new NetChangeBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.example.componentasystemtest.broadcastTest");
        registerReceiver(braodcast, intentFilter);
    }
}
