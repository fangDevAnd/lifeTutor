package com.example.momomusic.servie;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * activity的任务栈的管理工具
 */
public class ActivityTaskManagerUtil {


    private ActivityManager am;


    public ActivityTaskManagerUtil(Context context) {
        am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public void insertActivityToTop(Activity activity) {



    }

}
