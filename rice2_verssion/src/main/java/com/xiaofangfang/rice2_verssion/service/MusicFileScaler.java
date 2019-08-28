package com.xiaofangfang.rice2_verssion.service;


import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.xiaofangfang.rice2_verssion.model.Music;

import java.util.List;

/**
 * 音乐文件的扫描器,对系统的MP3
 * 的文件进行文件的检索,本身使用的是contentProvider
 */
public class MusicFileScaler {


    public static List<Music> getMusicList(Context context) {
        List<Music> musics;
        /**
         * 查询整个系统的媒体文件属于耗时操作,推荐使用的是
         *
         *
         */

        AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(context.getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);


            }
        };

        asyncQueryHandler.startQuery(0, null, MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, null);
        return null;
    }


}
