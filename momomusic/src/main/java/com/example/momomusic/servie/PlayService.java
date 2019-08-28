package com.example.momomusic.servie;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;


import com.example.momomusic.R;
import com.example.momomusic.dao.MusicDataDb;
import com.example.momomusic.fragment.MusicPlayFragment;
import com.example.momomusic.model.Music;
import com.example.momomusic.model.NearPlay;
import com.example.momomusic.tool.Looger;
import com.example.momomusic.tool.Tools;

import org.greenrobot.eventbus.Logger;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


/**
 * 用于实现后台的播放的service
 * 该程序唯一的一个前台服务，
 * 用于实心歌曲播放，通过startCommand的ACTION来实现同activity的通信
 * binder在这里作用被弱化，但是在最开始的使用过程中使用了binder，导致了startService之前都要bindService，来确保binder被正确创建
 */
public class PlayService extends Service implements MediaPlayer.OnPreparedListener {


    public static final String ACTION = "action";

    /**
     * 这个data是用来直接设置播放源的  得到的是String
     */
    public static final String DATA = "data";

    /**
     * 设置或附加加当前的数据源，得到 的是一个List<Music></>
     */

    public static final String SOURCE = "source";


    /**
     * 保存最近播放列表的最大数量
     */
    public static final int MAX_SAVE_NEAR = 100;


    /**
     * 下面是几种播放状态
     */
    public static final String UP = "up";

    public static final String DOWN = "down";

    public static final String PAUSE_OR_PLAY = "pauseOrPlay";

    public static final String WITH_DATA_PLAY = "withDataPlay";

    public static final String ADDITIONAL_DATA = "add";

    /**
     * 是否是随机播放 代表的值是一个<code>boolean</code>
     */
    public static final String RANDOM_PLAY = "randomPlay";

    /**
     * 是不是下一首播放
     */
    public static final String IS_DOWN_PLAY = "isDownPlay";

    public static final String IS_PLAY = "isPlay";


    /**
     * 随机播放，默认是false
     */
    public boolean randomPlay = false;

    public boolean isDownPlay = false;


    private MyBinder binder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Looger.D("binder");
        return binder = new MyBinder();
    }


    private int id = 12;

    private NotificationManager manager;

    private String channelId = "12";

    /**
     * 在这里进行前台服务的开启
     */
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(new NotificationChannel("123", "通知", NotificationManager.IMPORTANCE_HIGH));
        createNoti();
        startForeground(12, notification);
    }


    Notification notification;
    RemoteViews remoteViews;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createNoti() {

        notification = new Notification.Builder(this)
                .setCustomContentView(remoteViews = new RemoteViews(getPackageName(), R.layout.noti_play_music))
                .setSmallIcon(R.drawable.rice)
                .setChannelId("123")
                .build();

        Intent intent = new Intent(this, PlayService.class);
        intent.putExtra(ACTION, UP);
        PendingIntent upPend = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.up, upPend);

        Intent intent1 = new Intent(this, PlayService.class);
        intent1.putExtra(ACTION, DOWN);
        PendingIntent downPend = PendingIntent.getService(this, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.down, downPend);


        Intent intent2 = new Intent(this, PlayService.class);
        intent2.putExtra(ACTION, PAUSE_OR_PLAY);
        PendingIntent pauseOrPlayPend = PendingIntent.getService(this, 2, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.pauseOrPlay, pauseOrPlayPend);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Looger.D("onStartCommand");

        /**
         * 执行的活动
         */
        String action = intent.getStringExtra(ACTION);

        /**
         * 当前的播放数据
         */
        String data = intent.getStringExtra(DATA);

        /**
         * 被附加的数据源
         */
        ArrayList<Music> musics = intent.getParcelableArrayListExtra(SOURCE);
        boolean isDonwPlay1 = intent.getBooleanExtra(IS_DOWN_PLAY, false);


        if (action == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        switch (action) {
            case UP:
                binder.upMusic();
                break;
            case DOWN:
                binder.downMusic();
                break;
            case PAUSE_OR_PLAY:
                binder.pauseOrPlayMusic();
                break;
            case WITH_DATA_PLAY:
                binder.playMusic(data);
                break;
            case ADDITIONAL_DATA:
                binder.additionalData(musics, isDonwPlay1);
                break;

            case RANDOM_PLAY:
                binder.randomPlay(musics);
                break;
            case IS_PLAY:
                binder.isPlay();

        }
        return super.onStartCommand(intent, flags, startId);
    }


    private MediaPlayer mediaPlayer = new MediaPlayer();


    /**
     * 播放数据准备好的监听回调
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {

        if (mp.isPlaying()) {

        } else {
            mp.start();
            /**
             * 开始播放的时候就将数据保存进去
             */
            Music music = DataSupport.where("dataUrl=?", binder.currentMusic).find(Music.class).get(0);
            NearPlay nearPlay = new NearPlay(music);
            //在插入前判断数据库中的数据是否超过了100，如果超过了，就去删除
            int count = MusicDataDb.getInstance(PlayService.this).queryTableRows(Music.class.getSimpleName());
            if (count < MAX_SAVE_NEAR) {
            } else {
                List<Music> musics = DataSupport.findAll(Music.class);
                musics.get(musics.size() - 1).delete();//删除最上边的数据
            }
            nearPlay.save();
        }
    }


    public class MyBinder extends Binder implements MusicPlayOpration {

        private String currentMusic;

        public MyBinder() {

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    downMusic();
                    //播放完成之后将播放的列表加入到最近播放列表中
                }
            });
        }


        @Override
        public void playMusic(String dataSource) {
            this.currentMusic = dataSource;
            mediaPlayer.reset();//重置
            try {
                mediaPlayer.setDataSource(dataSource);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(PlayService.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void pauseOrPlayMusic() {
            /**
             * 如果当前music播放的歌曲为null，代表的是没有播放，也就是没有数据
             * 对于这种情况，我们需要重新设置数据源
             *
             */
            if (currentMusic == null) {
                /**
                 * 是不是随机播放,
                 * 如果是随机播放，就产生随机值
                 *
                 */
                if (randomPlay) {
                    int index = generRandom();
                    playMusic(musics.get(index).getDataUrl());
                } else {
                    playMusic(musics.get(0).getDataUrl());
                }

                return;
            }

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {//在这里我们需要判断当前是否存在播放的数据，如果没有，我们需要从新设置
                mediaPlayer.start();
            }
        }


        @Override
        public void upMusic() {

            if (randomPlay) {  //如果岁随机播放
                int index = generRandom();
                playMusic(musics.get(index).getDataUrl());

            } else {//反之采用上一首播放
                playMusic(getUpResource());
            }
        }

        @Override
        public void downMusic() {
            if (randomPlay) {  //如果岁随机播放
                int index = generRandom();
                playMusic(musics.get(index).getDataUrl());
            } else {
                playMusic(getNextResource());
            }
        }

        public String getUpResource() {
            Music upMusic = null;
            if (this.musics.size() > 0) {

                for (int i = 0; i < musics.size(); i++) {
                    if (currentMusic.equals(musics.get(i).getDataUrl())) {
                        if (i == 0) {
                            upMusic = musics.get(musics.size() - 1);
                        } else {
                            upMusic = musics.get(i - 1);
                        }
                    }
                }
            }
            return upMusic.getDataUrl();
        }


        public String getNextResource() {

            Music nextMusic = null;

            if (this.musics.size() > 0) {

                for (int i = 0; i < musics.size(); i++) {
                    if (currentMusic.equals(musics.get(i).getDataUrl())) {
                        if (i == musics.size() - 1) {
                            nextMusic = musics.get(0);
                        } else {
                            nextMusic = musics.get(i + 1);
                        }
                    }
                }
            }
            return nextMusic.getDataUrl();
        }

        private List<Music> musics;

        /**
         * 代表的是重新设置数据源
         * 会清空之前的数据
         *
         * @param musics
         */
        @Override
        public void setDataSources(List<Music> musics) {
            this.musics = musics;
        }


        /**
         * 附加新的数据，对之前播放的数据进行保留
         *
         * @param data
         * @param isDonwPlay1
         */
        public void additionalData(List<Music> data, boolean isDonwPlay1) {
            if (this.musics == null) {//代表的是不在运行状态
                this.musics = new ArrayList<>();
            }

            if (isDonwPlay1) {
                if (this.musics.size() == 0) {
                    this.musics.addAll(data);
                } else {
                    int index = getCurrentPlayMusicIndex();
                    this.musics.addAll(index, data);
                }
            } else {
                this.musics.addAll(data);
            }
        }


        /**
         * 获得当前播放数据源的位置
         * 获得的位置需要加1，代表的是在后面
         *
         * @return
         */
        public int getCurrentPlayMusicIndex() {

            int index = 0;
            for (int i = 0; i < musics.size(); i++) {
                if (currentMusic.equals(musics.get(i).getDataUrl())) {
                    index = i;
                }
            }
            return ++index;
        }


        /**
         * 随机播放
         * 产生随机值，然后进行播放
         * 每一次返回一个index
         *
         * @param musics
         */
        public void randomPlay(ArrayList<Music> musics) {
            //设置数据源
            setDataSources(musics);
            //设置标志位为随机播放
            randomPlay = true;
            int index = generRandom();
            playMusic(musics.get(index).getDataUrl());
        }


        /**
         * 产生随机播放的随机值
         */
        private int generRandom() {
            int size = musics.size(); //1

            int index = (int) Math.random() * size;

            /**
             * 当前的索引 1<=index<=size时 index--;
             *           index<1 index;
             */
            index = index <= size ? index >= 1 ? index-- : index : index;

            return index;
        }


        public void isPlay() {

            if (mediaPlayer.isPlaying()) {//如果是播放状态，就去启动播放界面
                Tools.startActivity(PlayService.this, MusicPlayFragment.class,null,true);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer = null;

    }


    interface MusicPlayOpration {

        void playMusic(String dataSource);

        void pauseOrPlayMusic();

        void upMusic();

        void downMusic();

        void setDataSources(List<Music> music);

    }


}





