package com.example.aidltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Constructor;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //case1();








    }

    private void case1() {
        /**
         * mConnector = new NativeDaemonConnector(
         * new NetdCallbackReceiver(), socket, 10, NETD_TAG, 160, wl,
         * FgThread.get().getLooper());
         * <p>
         * 上面的代码是原程序编写的，我们同样需要得到这个实例
         *
         * 实现不了，找不到这个类，原因是属于native和framework的通信代码 ，找不到
         *
         */
        try {
            Class conntorClass = Class.forName("com.android.server.NativeDaemonConnector");


            Constructor[] constructors = conntorClass.getDeclaredConstructors();


            for (int i = 0; i < constructors.length; i++) {
                int length = constructors[i].getParameterTypes().length;
                Log.d(TAG, "当前参数长度" + length);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
