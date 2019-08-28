package com.example.momomusic.servie;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;

import com.example.momomusic.model.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * 播放服务的绑定与解绑的服务,
 * 提供的就是对service的启动和绑定
 */
public class PlayServiceBindService {


    public static void randomPlayFun(List<Music> selectedMusic, Context context) {

        /**
         * 启动service ，产生一个random 的Action
         */

        Intent intent = new Intent(context, PlayService.class);
        intent.putExtra(PlayService.ACTION, PlayService.RANDOM_PLAY);
        intent.putParcelableArrayListExtra(PlayService.SOURCE, (ArrayList<? extends Parcelable>) selectedMusic);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        }, Context.BIND_AUTO_CREATE);

        context.startService(intent);//重新启动服务
    }


    /**
     * 附加数据到播放列表中
     *
     * @param context
     * @param selectedMusic
     */
    public static void additionalData(Context context, List<Music> selectedMusic, boolean isDownplay) {
        Intent intent = new Intent(context, PlayService.class);
        intent.putExtra(PlayService.ACTION, PlayService.ADDITIONAL_DATA);
        intent.putExtra(PlayService.IS_DOWN_PLAY, isDownplay);
        intent.putParcelableArrayListExtra(PlayService.SOURCE, (ArrayList<? extends Parcelable>) selectedMusic);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
        context.startService(intent);//重新启动服务
    }


    public static void IsPlay(Context context) {
        Intent intent = new Intent(context, PlayService.class);
        intent.putExtra(PlayService.ACTION, PlayService.IS_PLAY);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
        context.startService(intent);//重新启动服务
    }


}
