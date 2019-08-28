package com.example.componentasystemtest.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * 用来实现对数据哭的操作,之所以不使用框架,主要的原因是,希望复习一下api
 */
public class SqlHelp extends SQLiteOpenHelper {


    private String tableExe = "create table address(" +
            "id integer primary key autoincrement," +
            "name varchar(20) not null," +
            "tel varchar(13) not null," +
            "address varchar(60) not null," +
            "isDefault bigint(2) default 0" + //这个代表的是是不是默认选择的地址,默认不是,采用的存储数值是0
            ")";
    public static final String DB_NAME = "Rice.db";

    public static final String ADDRESS = "address";


    public static final int VERSION = 1;


    public SqlHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, SqlHelp.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tableExe);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }





}
