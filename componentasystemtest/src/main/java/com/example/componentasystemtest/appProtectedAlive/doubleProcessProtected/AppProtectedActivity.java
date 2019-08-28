package com.example.componentasystemtest.appProtectedAlive.doubleProcessProtected;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.example.componentasystemtest.R;
import com.example.componentasystemtest.Service_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AppProtectedActivity extends AppCompatActivity {

    private boolean con = false;

    private Service_1 service_1;


    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            con = true;
            service_1 = Service_1.Stub.asInterface(service);

            try {
                //尽管我们没有使用跨进程,但是我们还是使用了 aidl,但是是可以的
                Toast.makeText(AppProtectedActivity.this, "获得消息" + service_1.getName(), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            con = false;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MyConn conn = new MyConn();

        setContentView(R.layout.activity_app_protected);

        findViewById(R.id.button3).setOnClickListener((v) -> {//start
            Intent intent = new Intent(this, LocalService.class);
            startService(intent);
        });
        findViewById(R.id.button4).setOnClickListener((V) -> {//bind`
            Intent intent = new Intent(this, LocalService.class);
            bindService(intent, conn, BIND_AUTO_CREATE);
            //刚刚使用bind_import 没有作用
        });
        findViewById(R.id.button5).setOnClickListener((v) -> {//stop
            Intent intent = new Intent(this, LocalService.class);
            stopService(intent);
        });
        findViewById(R.id.button6).setOnClickListener((V) -> {//unbind
            if (con) {
                unbindService(conn);
                con = !con;
            }
        });

    }
}
