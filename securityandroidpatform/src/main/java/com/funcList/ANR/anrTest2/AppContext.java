package com.funcList.ANR.anrTest2;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.funcList.ANR.ANRApplication;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.BuildConfig;

public class AppContext extends BlockCanaryContext {


    private Application application;

    public AppContext(Application application) {
        this.application = application;
    }


    private static final String TAG = "test";


    /**
     * 获取应用版本信息
     */
    @Override
    public String provideQualifier() {
        String qualifier = "";
        try {
            PackageInfo info = application.getPackageManager()
                    .getPackageInfo(application.getPackageName(), 0);
            qualifier += info.versionCode + "_" + info.versionName + "_YYB";
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "provideQualifier exception", e);
        }
        return qualifier;
    }


    /**
     * 获取用户uid
     * @return
     */
    @Override
    public String provideUid() {
        return super.provideUid();
    }

    /**
     * 设置监控时长,eg:100000ms
     * @return
     */
    @Override
    public int provideMonitorDuration() {
        return super.provideMonitorDuration();
    }

    /**
     * 设置监控卡顿阀值,eg:1000ms
     * @return
     */
    @Override
    public int provideBlockThreshold() {
        return 500;
    }

    /**
     * displayNotification: 设置是否在桌面展示,eg: true or false
     * @return
     */
    @Override
    public boolean displayNotification() {
        return true;
    }

    /**
     * 设置log保存地址, eg:BlockTest
     * @return
     */
    @Override
    public String providePath() {
        return super.providePath();
    }
}
