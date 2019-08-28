package com.example.momomusic.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DaoHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "musicDb.db";


    public static final int VERSION = 3;


    public DaoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DaoHelper.VERSION);
    }


    /**
     * onCreate方法只有在第一次创建数据库的时候才会被调用，
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    /**
     * 当数据库进行升级后会调用这个表
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
