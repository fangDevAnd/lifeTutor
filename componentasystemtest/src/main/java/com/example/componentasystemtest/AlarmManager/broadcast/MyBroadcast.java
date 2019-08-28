package com.example.componentasystemtest.AlarmManager.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;

public class MyBroadcast extends BroadcastReceiver {


    AlarmManager alarmManager;
    Calendar calendar;
    PendingIntent sender;


    @Override
    public void onReceive(Context context, Intent intent) {
        // 重复定时任务

        Intent intent1 = new Intent("myBroadcast");
        sender = PendingIntent.getBroadcast(
                context, 0, intent1, 0);

        // We want the alarm to go off 10 seconds from now.
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /**
             * 因为这个判断，导致了在Activity启动的代码只在android4.4的时候运行
             */
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
        // to do something

        Toast.makeText(context, "接收到广播", Toast.LENGTH_SHORT).show();
    }
}

