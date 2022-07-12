package com.Shirai_Kuroko.DLUTMobile.Managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MsgHistoryManager {
    private SQLiteDatabase db;

    public MsgHistoryManager(Context context) {
        sql sql = new sql(context, "MsgHistory", null, 1);
        db = sql.getReadableDatabase();
    }

    public void insert(Long timestamp,String content) {
        String sql = "insert into MsgHistory values(?,?)";
        db.beginTransaction();
        db.execSQL(sql, new Object[]{timestamp,content});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    //删除
    public void Remove(Long timestamp) {
        String sql = "delete from MsgHistory where timestamp=?";
        db.execSQL(sql, new Object[]{timestamp});
    }

    @SuppressLint("Range")
    public String query(Long timestamp) {
        String sql = "select * from MsgHistory";
        Cursor cursor = db.rawQuery(sql, null);
        List<String> strings = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if(cursor.getLong(cursor.getColumnIndex("timestamp"))==timestamp)
                {
                    return cursor.getString(cursor.getColumnIndex("content"));
                }
            }
        }
        return null;
    }

    //修改
    public void update(Long timestamp,String content) {
        String sql = "update MsgHistory set content=? where timestamp=?";
        db.execSQL(sql, new Object[]{content, timestamp});
    }

    //查询
    public List<String> select() {
        String sql = "select * from MsgHistory";
        Cursor cursor = db.rawQuery(sql, null);
        List<String> strings = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
                strings.add(content);
            }
        }
        return strings;
    }
}
