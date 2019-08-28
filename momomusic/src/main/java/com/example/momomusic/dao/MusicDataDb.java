package com.example.momomusic.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;

import com.example.momomusic.model.Music;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.example.momomusic.servie.LocalMusicIndexUtil.DEFAULT_VALUE;

public class MusicDataDb {

    private DaoHelper daoHelper;

    public static final String TABLE_SCHEMA = "table_schema";

    private static MusicDataDb musicDataDb;


    public static final String TABLE_1 = "music";

    public static final String TABLE_2 = "android_metadata";

    public static final String TABLE_3 = "sqlite_sequence";

    public static final String TABLE_4 = "sqlite_master";


    private MusicDataDb(Context context) {
        if (daoHelper == null) {
            daoHelper = new DaoHelper(context, null, null, -1);
        }
    }

    /**
     * 获得当前的
     *
     * @param context
     * @return
     */
    public static MusicDataDb getInstance(Context context) {
        if (musicDataDb == null) {
            musicDataDb = new MusicDataDb(context);
        }
        return musicDataDb;
    }


    public List<String> queryTable() {


        List<String> names = new ArrayList<>();

        SQLiteDatabase sql = daoHelper.getReadableDatabase();
        Cursor cursor = sql.query(TABLE_4, new String[]{TableSchema.NAME}, "type=?", new String[]{"table"}, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            names.add(name);
        }
        return names;
    }

    public void createTable(String table) {
        SQLiteDatabase sql = daoHelper.getReadableDatabase();
        sql.execSQL("create table " + table + "(" +
                "id INTEGER primary key autoIncrement," +
                "dataUrl varchar not null," +
                "FOREIGN KEY(dataUrl) REFERENCES music(dataUrl) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")");
    }

    public void insertData(String table, List<String> dataUrl) {
        SQLiteDatabase sql = daoHelper.getWritableDatabase();
        sql.beginTransaction();
        for (int i = 0; i < dataUrl.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(TableMusic.DATA_URL, dataUrl.get(i));
            sql.insert(table, null, cv);
        }
        sql.endTransaction();

    }


    class TableSchema {
        public static final String NAME = "name";

    }

    class TableMusic {
        public static final String DATA_URL = "dataUrl";
    }


    /**
     * 查询新建的歌单
     *
     * @return
     */
    public List<String> queryNewMusicSheet() {
        List<String> tables = queryTable();
        tables.remove(TABLE_1);
        tables.remove(TABLE_2);
        tables.remove(TABLE_3);
        tables.remove(TABLE_SCHEMA);
        return tables;
    }


    public DaoHelper getDaoHelper() {
        return daoHelper;
    }

    /**
     * private int duration;
     * <p>
     * private int track;
     * <p>
     * private int artist_id;
     * <p>
     * private int albumId;
     * <p>
     * private String albumKey;
     * <p>
     * private int bookMark;
     * <p>
     * private int dateAdded;
     * <p>
     * private String composer;
     * <p>
     * private String title;
     * <p>
     * private String dataUrl;
     * <p>
     * <p>
     * private String displayName;
     * <p>
     * private String albumName;
     * <p>
     * private String artist;
     * <p>
     * private float size;
     *
     * @param table
     * @param condi
     * @param values
     * @return
     */
    public List<Music> queryBySql(String table, String condi, String[] values) {
        SQLiteDatabase database = daoHelper.getReadableDatabase();
        Cursor cursor = database.query(table, new String[]{"dataUrl", "albumName", "displayName", "artist", "size",
                "duration", "track", "artist_id", "albumId", "albumKey", "bookMark", "dateAdded", "composer", "title"}, condi, values, null, null, null);

        List<Music> musics = new ArrayList<>();

        if (cursor == null) {
            return musics;
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

            musics.add(music);
        }
        return musics;
    }


    /**
     * 查询表的行数
     *
     * @param tableName
     * @return
     */
    public int queryTableRows(String tableName) {
        Cursor cursor = DataSupport.findBySQL("select count(*) from " + tableName);

        int count = 0;
        if (cursor != null) {

            while (cursor.moveToNext()) {
                count++;
            }
        }
        return count;
    }


}
