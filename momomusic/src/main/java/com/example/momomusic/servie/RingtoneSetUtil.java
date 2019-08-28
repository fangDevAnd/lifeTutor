package com.example.momomusic.servie;


import android.content.ContentValues;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.momomusic.model.Music;

import java.io.File;

/**
 * 铃声设置的Util
 */
public class RingtoneSetUtil {


    // 将铃声的路径插入contentResolver，以数据库的形式插入

    /**
     * 设置默认振铃
     *
     * @param music 歌曲信息
     */
    public static void setRingtoneImpl(Music music, Context context) {
        ContentValues content = new ContentValues();
        content.put(MediaStore.MediaColumns.DATA, music.getDataUrl());
        content.put(MediaStore.MediaColumns.TITLE, music.getTitle());
        //content.put(MediaStore.MediaColumns.SIZE, ringtoneFile);
        content.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
        //  content.put(MediaStore.Audio.Media.ARTIST, "Madonna");
        //content.put(MediaStore.Audio.Media.DURATION, 230);
        content.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        content.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
        content.put(MediaStore.Audio.Media.IS_ALARM, true);
        content.put(MediaStore.Audio.Media.IS_MUSIC, false);
        // 获取文件是external还是internal的uri路径
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(music.getDataUrl());
        // 铃声通过contentvaues插入到数据库
        final Uri newUri = context.getContentResolver().insert(uri, content);
        RingtoneManager.setActualDefaultRingtoneUri(context,
                RingtoneManager.TYPE_RINGTONE, newUri);
    }


    /**
     * 删除铃声
     *
     * @param music
     * @param context
     */
    public static void deleteRingtone(Music music, Context context) {
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Audio.Media.IS_RINGTONE, false);
        cv.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        cv.put(MediaStore.Audio.Media.IS_ALARM, false);
        cv.put(MediaStore.Audio.Media.IS_MUSIC, true);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(music.getDataUrl());

        // 更新当前铃声的数据，放弃作为铃声的状态
        context.getContentResolver().delete(uri, MediaStore.MediaColumns.DATA + "=?",
                new String[]{music.getDataUrl()});

    }


}
