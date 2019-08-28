package com.example.componentasystemtest.broadcastTest.netStatusChange;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.componentasystemtest.R;


public class NetChangeActivity extends AppCompatActivity {


    private int id = 12;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_net_change);


        /**
         * 系统权限，申请不到的，没用
         */
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MODIFY_PHONE_STATE}, 12);

    }

    public void onClick(View view) {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            JobInfo.Builder builder = new JobInfo.Builder(id,
//                    new ComponentName(this, MyJobService.class))
//                    .setMinimumLatency(2000000)
//                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
////            代表的是有网络的时候执行
//            JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//            int code = tm.schedule(builder.build());
//
//            if (code == JobScheduler.RESULT_SUCCESS) {
//                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
//            }
//
//        }


        NetChangeBroadcast netChangeBroadcast = new NetChangeBroadcast();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netChangeBroadcast, itFilter);
    }


}
