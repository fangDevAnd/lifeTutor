package com.xiaofangfang.rice2_verssion.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.widget.ListView;

import com.xiaofangfang.rice2_verssion.activity.AddressManager;
import com.xiaofangfang.rice2_verssion.model.AddressMode;

import org.litepal.exceptions.DatabaseGenerateException;

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


    /**
     * 获得所以的数据
     *
     * @return
     */
    //这里我们需要返回数据的id,因为在删除的时候需要
    public List<AddressMode> getAllData() {
        List<AddressMode> addressModes = new ArrayList<>();
        SQLiteDatabase sql = sqlHelp.getReadableDatabase();
        Cursor cursor = sql.query(SqlHelp.ADDRESS, new String[]{"id", "name", "tel", "address", "isDefault"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            AddressMode addressMode = new AddressMode(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4) == 1);
            addressModes.add(addressMode);
        }

        return addressModes;
    }

    /**
     * 添加一个新的地址
     *
     * @param addressMode
     * @return
     */
    public long writeAddress(AddressMode addressMode) {
        SQLiteDatabase sd = sqlHelp.getWritableDatabase();
        long rows;
        ContentValues cv = new ContentValues();
        cv.put("name", addressMode.getName());
        cv.put("tel", addressMode.getTel());
        cv.put("address", addressMode.getAddress());
        cv.put("isDefault", addressMode.isDefault() == true ? 1 : 0);
        rows = sd.insert(SqlHelp.ADDRESS, null, cv);

        if (rows == -1) {
            throw new SQLiteDatabaseCorruptException("插入数据出错");
        }
        return rows;
    }

    /**
     * 删除新的地址
     *
     * @param id
     * @return
     */
    public long removeAddress(int id) {
        SQLiteDatabase sd = sqlHelp.getWritableDatabase();
        int rows = sd.delete(SqlHelp.ADDRESS, "id=?", new String[]{"" + id,});
        if (rows == -1) {
            throw new SQLiteDatabaseCorruptException("删除数据出错");
        }
        return rows;
    }

    public long updateAddress(AddressMode addressMode) {

        ContentValues cv = new ContentValues();

        cv.put("name", addressMode.getName());
        cv.put("tel", addressMode.getTel());
        cv.put("address", addressMode.getAddress());
        cv.put("isDefault", addressMode.isDefault());

        SQLiteDatabase sd = sqlHelp.getWritableDatabase();
        int rows = sd.update(SqlHelp.ADDRESS, cv, "id=?", new String[]{"" + addressMode.getId()});
        if (rows == -1) {
            throw new SQLiteDatabaseCorruptException("删除数据出错");
        }
        return rows;
    }


    public void updateOtherStatus() {
        SQLiteDatabase sd = sqlHelp.getWritableDatabase();
        sd.execSQL("update " + SqlHelp.ADDRESS + " set isDefault=0");
    }


}
