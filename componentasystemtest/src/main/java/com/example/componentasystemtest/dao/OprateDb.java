package com.example.componentasystemtest.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;


import java.util.ArrayList;
import java.util.List;

/**
 * 提供了对数据库的访问能力
 * 使用的是单利模式的实现方案,
 * 因为存储就是单个的
 */
public class OprateDb {


    private SqlHelp sqlHelp;

    private static OprateDb oprateDb;


    private OprateDb(Context context) {
        sqlHelp = new SqlHelp(context, null, null, -1);
    }

    public static OprateDb getInstance(Context context) {
        if (oprateDb == null) {
            oprateDb = new OprateDb(context);
        }
        return oprateDb;
    }


    public void updateOtherStatus() {
        SQLiteDatabase sd = sqlHelp.getWritableDatabase();
        sd.execSQL("update " + SqlHelp.ADDRESS + " set isDefault=0");
    }


}
