import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//package com.example.componentasystemtest.musicPlay.simple1;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.ServiceConnection;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.RemoteViews;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.example.componentasystemtest.R;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.util.LinkedList;
//
//
///**
// * 五步走
// * 【1】 绑定服务 ，并获取中间人对象（IBinder）
// * 【2】 通过中间人进行相关操作
// * 包括Notification（图标、下一次事件等）更新；Activity界面的更新
// * 【3】定时更新播放进度、监听准备成功以及播放完成后的操作
// * 【4】动态注册广播，通知栏触发——PendingIntent
// * 【5】根据广播内容，更新状态等
// * <p>
// * 【6】释放资源
// * 解除绑定、清除通知栏信息；注销广播接收者
// * <p>
// * 注意
// * 清单文件中，添加网络权限，以及声明service
// * 同时设置android:launchMode="singleTask"，避免通知栏点击创建多个activity
// */
//public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener {
//    private static Button button;
//
//    static final String TAG = "音频";
//    String url = "https://mbrbimg.oss-cn-shanghai.aliyuncs.com/picturebook/audio/35_591d9f4cb39e2_08o.mp3";
//
//    public static MusicService.MyBinder myBinder;//中间人对象
//    private MyConn myConn;
//
//    private static int duration;//音频总长度
//    private static int currentPosition;//当前进度
//
//    private static int status; //0初始状态，1暂停，2播放，3播放完成
//    private static SeekBar seekbar;
//    private static TextView tv_current_time;
//    private static TextView tv_time;
//    public static Notification notification;
//    private static NotificationManager notificationManager;
//    private static Context mContext;
//    private MusicReceiver receive;
//
//    static String PALYER_TAG;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        EventBus.getDefault().register(this);
//        mContext = this;
//        PALYER_TAG = AppUtils.getPackageName(this);
//
//
//        initView();
//
//        Intent intent = new Intent(this, MusicService.class);
//        intent.putExtra("url", url);
//        myConn = new MyConn();
//        //【1】绑定服务,并在MyConn中获取中间人对象（IBinder）
//        bindService(intent, myConn, BIND_AUTO_CREATE);
//
//
//        //【4】动态注册广播（具体操作由通知栏触发---PendingIntent）
//        receive = new MusicReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(PALYER_TAG);
//        registerReceiver(receive, filter);
//
//
//    }
//
//    /**
//     * 初始化控件
//     */
//    private void initView() {
//        button = (Button) findViewById(R.id.btn);
//        button.setOnClickListener(this);
//        seekbar = (SeekBar) findViewById(R.id.seekbar);
//        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                myBinder.seekToPosition(seekBar.getProgress());
//                tv_current_time.setText(TiUtils.getTime(seekBar.getProgress() / 1000));
//            }
//        });
//
//        tv_current_time = (TextView) findViewById(R.id.tv_current_time);
//        tv_time = (TextView) findViewById(R.id.tv_time);
//
//    }
//
//    /**
//     * 初始化通知栏
//     */
//    private void initNotification() {
//        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
////        notification = new Notification();
//
////        notification.icon = R.mipmap.ic_launcher;//图标
//        RemoteViews contentView = new RemoteViews(getPackageName(),
//                R.layout.notification_control);
////        notification.contentView = contentView;
//
//        contentView.setImageViewResource(R.id.iv_pic, R.drawable.pic);//图片展示
//
//        contentView.setImageViewResource(R.id.iv_play, R.drawable.ting);//button显示为正在播放
//
//
//        Intent intentPause = new Intent(PALYER_TAG);
//        intentPause.putExtra("status", "pause");
//        PendingIntent pIntentPause = PendingIntent.getBroadcast(this, 2, intentPause, PendingIntent.FLAG_UPDATE_CURRENT);
//        contentView.setOnClickPendingIntent(R.id.iv_play, pIntentPause);
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent intent = PendingIntent.getActivity(this, 0,
//                notificationIntent, 0);
//
//        mBuilder.setContent(contentView).setSmallIcon(R.mipmap.ic_launcher).setContentIntent(intent);//点击事件
//
//        notification = mBuilder.build();
//        notification.flags = notification.FLAG_NO_CLEAR;//设置通知点击或滑动时不被清除
//
//
//        notificationManager.notify(PALYER_TAG, 111, notification);//开启通知
//
//
//    }
//
//    /**
//     * 更新状态栏
//     *
//     * @param type 图标样式：1是正在播放状态，2是停止状态； 3播放完成
//     */
//    public static void updateNotification(int type) { //【5】更新操作
//        if (type == 1) {//播放中
//            status = 2;
//            notification.contentView.setImageViewResource(R.id.iv_play, R.drawable.ting);
//            Intent intentPlay = new Intent(PALYER_TAG);//下一次意图，并设置action标记为"play"，用于接收广播时过滤意图信息
//            intentPlay.putExtra("status", "pause");
//            PendingIntent pIntentPlay = PendingIntent.getBroadcast(mContext, 2, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);
//            notification.contentView.setOnClickPendingIntent(R.id.iv_play, pIntentPlay);//为控件注册事件
//
//            button.setText("暂停");
//
//        } else {//暂停或者播放完成
//
//            notification.contentView.setImageViewResource(R.id.iv_play, R.drawable.play);
//
//            Intent intentPause = new Intent(PALYER_TAG);
//
//            if (type == 2) {//暂停
//                status = 1;
//                button.setText("继续");
//                intentPause.putExtra("status", "continue");
//            } else {//3播放完成
//                button.setText("重新开始");
//                intentPause.putExtra("status", "replay");//下一步
//            }
//            PendingIntent pIntentPause = PendingIntent.getBroadcast(mContext, 2, intentPause, PendingIntent.FLAG_UPDATE_CURRENT);
//            notification.contentView.setOnClickPendingIntent(R.id.iv_play, pIntentPause);
//
//
//        }
//        notificationManager.notify(PALYER_TAG, 111, notification);//开启通知
//
//    }
//
//    @Override
//    public void onClick(View v) { //【2】 通过中间人进行相关操作
//
//        Log.d("当前状态", "status = " + status);
//        switch (status) {
//            case 0://初始状态
//                //播放
//                myBinder.play();
//                status = 2;
//                button.setText("暂停");
//
//                //初始化通知栏
//                initNotification();
//                break;
//
//            case 1://暂停
//                //继续播放
//                myBinder.moveon();
//                status = 2;
//                button.setText("暂停");
//                updateNotification(1);
//                break;
//
//            case 2://播放中
//                //暂停
//                myBinder.pause();
//                status = 1;
//                button.setText("继续播放");
//                updateNotification(2);
//                break;
//
//            case 3://播放完成
//                //重新开始
//                myBinder.rePlay();
//                status = 2;
//                button.setText("暂停");
//                updateNotification(1);
//                break;
//
//
//        }
//    }
//
//    public class MyConn implements ServiceConnection {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) { //获取中间人对象
//            myBinder = (MusicService.MyBinder) service;
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN) //3.0之后，需要加注解
//    public void onEventMainThread(UpdateUI updateUI) {
//        int flag = updateUI.getFlag();
//
//        Log.d("音频状态", status + " ---- " + flag);
//        //【3】设置进度
//        if (flag == 1) {//准备完成，获取音频长度
//            duration = (int) updateUI.getData();
//            Log.d(TAG, "总长度" + duration);
//            //设置总长度
//            seekbar.setMax(duration);
//            tv_time.setText(TiUtils.getTime(duration / 1000));
//
//
//        } else if (flag == 2) {//播放完成
//            Log.d(TAG, "播放完成～");
//
//            status = 3;//已完成
//            //重置信息
//            seekbar.setProgress(0);
//            tv_current_time.setText("00:00");
//            button.setText("重新播放");
//
//            updateNotification(3);
//
//        } else if (flag == 3) {//更新进度
//            if (status == 3)//避免播放完成通知与线程通知冲突
//                return;
//            currentPosition = (int) updateUI.getData();
//            Log.d(TAG, "当前进度" + currentPosition);
//
//            //设置进度
//            tv_current_time.setText(TiUtils.getTime(currentPosition / 1000));
//            seekbar.setProgress(currentPosition);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        Log.d("销毁", "onDestroy()");
//        //【6】解除绑定并注销
//        unbindService(myConn);
//        myConn = null;
//
//        if (notificationManager != null)
//            notificationManager.cancel(PALYER_TAG, 111);//通过tag和id,清除通知栏信息
//
//        EventBus.getDefault().unregister(this);
//        unregisterReceiver(receive);
//
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//
////        finish();
//
//    }
//}
