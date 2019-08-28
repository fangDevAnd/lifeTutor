package com.example.momomusic.servie;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.momomusic.model.Music;
import com.orhanobut.logger.Logger;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;


/**
 * 本地音乐检索工具
 */
public class LocalMusicIndexUtil {

    /**
     * MediaStore.Audio.Media.DATA,
     * MediaStore.Audio.Media.ALBUM,
     * MediaStore.Audio.Media.DISPLAY_NAME,
     * MediaStore.Audio.Media.ARTIST,
     * MediaStore.Audio.Media.SIZE,
     * MediaStore.Audio.Media.DURATION,
     * MediaStore.Audio.Media.TRACK,
     * MediaStore.Audio.Media.ARTIST_ID,
     * MediaStore.Audio.Media.ALBUM_ID,
     * MediaStore.Audio.Media.ALBUM_KEY,
     * MediaStore.Audio.Media.BOOKMARK,
     * MediaStore.Audio.Media.DATE_ADDED,
     * MediaStore.Audio.Media.COMPOSER,
     * MediaStore.Audio.Media.TITLE,
     * <p>
     * 下面定义常量，用来指定当数据没有查到的缺省值
     */

    public static final String DEFAULT_VALUE = "未知";


    private static LocalMusicIndexUtil util = new LocalMusicIndexUtil();

    private LocalMusicIndexUtil() {

    }

    public static LocalMusicIndexUtil getInstance() {
        return util;
    }


    /**
     * 添加标志位,避免重复点击导致的线程问题
     */
    private boolean isRuning = false;

    public void indexLocalMusicI(Context context) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isRuning) {
                    Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.ALBUM,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.SIZE,
                            MediaStore.Audio.Media.DURATION,
                            MediaStore.Audio.Media.TRACK,
                            MediaStore.Audio.Media.ARTIST_ID,
                            MediaStore.Audio.Media.ALBUM_ID,
                            MediaStore.Audio.Media.ALBUM_KEY,
                            MediaStore.Audio.Media.BOOKMARK,
                            MediaStore.Audio.Media.DATE_ADDED,
                            MediaStore.Audio.Media.COMPOSER,
                            MediaStore.Audio.Media.TITLE,
                    }, null, null, null);

                    if (cursor == null) {
                        return;
                    }
                    while (cursor.moveToNext()) {
                        String data = cursor.getString(0);
                        String albumName = cursor.getString(1);
                        String displayName = cursor.getString(2);
                        String artist = cursor.getString(3);
                        float size = cursor.getInt(4) / 1024f / 1024f;
                        int duration = cursor.getInt(5);
                        int track = cursor.getInt(6);
                        int artist_id = cursor.getInt(7);
                        int albumId = cursor.getInt(8);
                        String albumKey = cursor.getString(9);
                        int bookMark = cursor.getInt(10);
                        int dateAdded = cursor.getInt(11);
                        String composer = cursor.getString(12);
                        String title = cursor.getString(13);

                        data = data == null ? DEFAULT_VALUE : data;
                        albumName = albumName == null ? DEFAULT_VALUE : albumName;
                        displayName = displayName == null ? DEFAULT_VALUE : displayName;
                        artist = artist == null ? DEFAULT_VALUE : artist;
                        albumKey = albumKey == null ? DEFAULT_VALUE : albumKey;
                        composer = composer == null ? DEFAULT_VALUE : composer;
                        title = title == null ? DEFAULT_VALUE : title;

                        Music music = new Music(duration, track, artist_id, albumId, albumKey, bookMark, dateAdded, composer, title, data, displayName, albumName, artist, size);

                        if (musicScaleListener != null) {
                            musicScaleListener.scaling(music);
                        }
                    }
                    if (musicScaleListener != null) {
                        musicScaleListener.scaleComplate();

                    }
                    isRuning = false;
                }
            }
        }).start();
    }


    public interface MusicScaleListener {

        void scaling(Music music);

        default void scaleComplate() {
            Logger.d("扫描音乐完成");
        }
    }

    private MusicScaleListener musicScaleListener;


    public void setMusicScaleListener(MusicScaleListener musicScaleListener) {
        this.musicScaleListener = musicScaleListener;
    }


}
