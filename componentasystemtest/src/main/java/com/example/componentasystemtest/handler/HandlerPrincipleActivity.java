package com.example.componentasystemtest.handler;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.componentasystemtest.R;

import java.lang.reflect.Method;

public class HandlerPrincipleActivity extends AppCompatActivity {

    private static final String TAG = "test";

    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_handler);

        findViewById(R.id.handlerOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Toast.makeText(HandlerPrincipleActivity.this, "执行了主线程创建的handler", Toast.LENGTH_SHORT).show();
                    }
                };
                //该函数执行在主线程中

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        handler.sendEmptyMessage(12);
                        handler.sendEmptyMessage(11);
                        handler.sendEmptyMessage(10);

//                        getloopMessage();

                    }
                }).start();
            }
        });

        findViewById(R.id.handlerTwo).setOnClickListener((v) -> {


            new Thread(new Runnable() {
                @Override
                public void run() {

                    Looper.prepare();

                    Handler handler = new Handler() {

                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Toast.makeText(HandlerPrincipleActivity.this, "执行了子线程创建的Handler", Toast.LENGTH_SHORT).show();
                        }
                    };

                    handler.sendEmptyMessage(12);

                    Looper.loop();

                }
            }).start();


        });

        /**
         * handlerThree
         *
         * 提供了对HandlerThread的使用
         *
         * 我们根据源码，发现乐意通过重写HandlerThread的getThreadHandler来重写handler实现数据的接受
         * 但是实际的情况我们不能重写这个方法，onLooperPrepared被注解了，不能重写
         *
         *
         */
        findViewById(R.id.handlerThree).setOnClickListener((v) -> {


            class MyHandlerThread extends HandlerThread {

                public MyHandlerThread(String name) {
                    super(name);
                }

                @Override
                protected void onLooperPrepared() {

                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Toast.makeText(HandlerPrincipleActivity.this, "点击操作执行", Toast.LENGTH_SHORT).show();
                        }
                    };
                }
            }
            handler.sendEmptyMessage(23);
        });


    }

    private void getloopMessage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MessageQueue messageQueue = Looper.getMainLooper().getQueue();

            for (; ; ) {
                try {
                    Method[] methods = messageQueue.getClass().getDeclaredMethods();

                    for (Method method : methods) {
                        if (method.getName().equals("next")) {
                            method.setAccessible(true);
                            Message message = (Message) method.invoke(messageQueue);
                            if (message == null) {
                                // No message indicates that the message queue is quitting.
                                break;
                            }
                            Log.d(TAG, "获得当前主线程的消息队列的数据" + message.what);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
