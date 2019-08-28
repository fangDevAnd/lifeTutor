package com.example.componentasystemtest.asyncTask;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.componentasystemtest.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

public class AsyncTask {


    public static void threadProcess(Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }


    public static void handlerThreadProcess(Context context) {

        /**
         *
         * handlerThread内部帮我们维持了一个Looper循环，也就是我们不需要关注是否在主线程或者子线程的Looper问题
         */
        HandlerThread handlerThread = new HandlerThread("handleThread") {

            @Override
            protected void onLooperPrepared() {
                super.onLooperPrepared();
                //该方法执行在执行looper循环之前，运行在主线程中

            }
        };

        handlerThread.start();


        Handler handler = new Handler(handlerThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 20) {
                    Toast.makeText(context, "接受到消息", Toast.LENGTH_SHORT).show();
                }
            }
        };

        handler.sendEmptyMessage(20);

    }

    public static Handler handler;

    public static void handlerThread1Process(Context context) {


        HandlerThread handlerThread = new HandlerThread("handlerThread1") {

            @Override
            protected void onLooperPrepared() {
                super.onLooperPrepared();
                Toast.makeText(context, "打通了looper循环", Toast.LENGTH_SHORT).show();
            }
        };

        handlerThread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                handler = new Handler(handlerThread.getLooper()) {

                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Toast.makeText(context, "接收到消息", Toast.LENGTH_SHORT).show();
                    }
                };
                handler.sendEmptyMessage(20);
                //如果采用传统的方法 ，那么我们需要自己在handler创建的之前调用 Looper.prepare();
                //在创建之后Loop.loop(),使用HandlerThread包装了handler和Thread,所以不需要我们再创建
            }
        }).start();
    }


    public static <T extends AppCompatActivity> void asyncQueryHandlerProcess(T context) {


        ContentResolver cr = context.getContentResolver();


        AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(cr) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);

                if (cursor == null || cursor.getCount() == 0) {
                    Toast.makeText(context, "没有找到数据", Toast.LENGTH_SHORT).show();
                    return;
                }

                while (cursor.moveToNext()) {

                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                    Log.d("test", "onQueryComplete:===== " + url + " ---->" + name);
                }


                VideoView videoView = context.findViewById(R.id.video);


                while (cursor.moveToNext()) {
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                    int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                    int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    Log.d("test", "onQueryComplete: " + title + " " + size + " " + duration + " " + path);

                    //代码做演示，比较垃圾
                    MediaController mediaController = new MediaController(context);
                    mediaController.setAnchorView(videoView);
                    mediaController.show();

                    videoView.setVideoPath(path);

                }

                cursor.close();
            }

            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                super.onInsertComplete(token, cookie, uri);


            }

            @Override
            protected void onUpdateComplete(int token, Object cookie, int result) {
                super.onUpdateComplete(token, cookie, result);
            }

            @Override
            protected void onDeleteComplete(int token, Object cookie, int result) {
                super.onDeleteComplete(token, cookie, result);
            }
        };

        int token = 20;//消息对象的arg1
        //这里的cookie是对象数据的传递


        //参数：url、查询哪些列、选择条件占位符、选择条件占位符？用什么替换、查询后排序方式
        asyncQueryHandler.startQuery(token, null, MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Media.TITLE,//视频名称
                        MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATA},//大小、时长、视频路径
                null, null, null);

    }


}
