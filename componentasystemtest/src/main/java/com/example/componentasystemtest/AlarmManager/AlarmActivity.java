package com.example.componentasystemtest.AlarmManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.componentasystemtest.AlarmManager.service.OneShotAlarm;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;


/**
 * android定时执行的代码实现
 */
public class AlarmActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getClass().getSimpleName());

        /**
         * 在19以上版本，setRepeating中设置的频繁只是建议值， 5.0 以上的源码中最小值是60s
         * 这也是我为什么在log里面看到执行时间被改为１分钟
         *
         *
         *
         */

        //正计时
//        demo1();
//        demo2();
//        demo3();
        demo4();
        demo5();

        //倒计时
        demo6();


    }

    private void demo6() {


    }

    private void demo5() {

    }

    private void demo4() {


        Intent intent = new Intent(this, OneShotAlarm.class);
        sender = PendingIntent.getBroadcast(
                this, 0, intent, FLAG_UPDATE_CURRENT);

        // We want the alarm to go off 10 seconds from now.
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
    }

    AlarmManager alarmManager;
    Calendar calendar;
    PendingIntent sender;

    private void demo3() {

        Intent intent = new Intent("myBroadcast");
        sender = PendingIntent.getBroadcast(
                this, 0, intent, FLAG_UPDATE_CURRENT);

        // We want the alarm to go off 10 seconds from now.
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // pendingIntent 为发送广播
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android 6.0以上。应对 对低电耗模式和应用待机模式进行针对性优化
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10 * 1000, sender);
        }

    }


    /**
     * 重复闹钟
     */
    private void demo2() {

        Intent intent = new Intent(this,
                OneShotAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                this, 0, intent, 0);

        // We want the alarm to go off 10 seconds from now.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 10 * 1000, sender);

    }


    /**
     * 一次性闹钟
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void demo1() {

        Intent intent = new Intent(this, OneShotAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                this, 0, intent, FLAG_UPDATE_CURRENT);

        // We want the alarm to go off 10 seconds from now.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);


        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        /**
         * 使用的是绝对时间，具有ｃｐｕ的唤醒功能
         */
        am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }
}
