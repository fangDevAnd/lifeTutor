package com.example.componentasystemtest.broadcastTest.baseUse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.componentasystemtest.tools.Looger;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Looger.d("当前的context对象是=" + context.getClass().getName());
    }
}
