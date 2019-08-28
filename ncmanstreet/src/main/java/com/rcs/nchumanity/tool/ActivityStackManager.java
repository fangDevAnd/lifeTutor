package com.rcs.nchumanity.tool;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

public class ActivityStackManager {


    private static ActivityStackManager stackManager;

    private Context context;

    private ActivityStackManager(Context context) {
        this.context = context;
    }

    public static ActivityStackManager getInstance(Context context) {
        if (stackManager == null) {
            stackManager = new ActivityStackManager(context);
        }
        return stackManager;
    }


    /**
     * 获得栈的大小
     *
     * @return
     */
    public int getStackSize() {
        int size = 0;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        size = am.getRunningTasks(1).size();
        return size;
    }

    /**
     * 获得运行栈顶部Activity的name
     *
     * @return
     */
    public String getTopStackPackageName() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return am.getRunningTasks(1).get(0).topActivity.flattenToString();
    }

    /**
     * @return
     */
    public String getRunTaskState() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return (String) am.getRunningTasks(1).get(0).description;
    }


}
