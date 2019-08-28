package com.funcList.ANR;

import android.app.Application;
import android.os.StrictMode;

import com.funcList.ANR.anrTest2.AppContext;
import com.github.moduth.blockcanary.BlockCanary;

public class ANRApplication extends Application {


    private boolean debug=true;

    @Override
    public void onCreate() {
        super.onCreate();

        if(debug){
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls()//检测自定义耗时操作
                    .detectDiskReads()//检测是否存在磁盘读取操作
                    .detectDiskWrites()//检测是否存在磁盘的写操作
                    .detectNetwork().build()//检测是否村牛奶在网络操作
            );

            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder()
                    .detectActivityLeaks()//检测是否存在activity泄露
                    .detectLeakedClosableObjects()//检测是否存在没有关闭的Closable对象泄露
                    .detectLeakedSqlLiteObjects()//检测是否存在sqlite对象泄露
//                    .setClassInstanceLimit()//检测类的实例是否超过限制
                    .build()
            );
        }




    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
