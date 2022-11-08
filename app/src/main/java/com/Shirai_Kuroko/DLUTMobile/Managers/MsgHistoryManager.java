package com.Shirai_Kuroko.DLUTMobile.Managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationHistoryDataBaseBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.DLUTNoticeContentBean;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class MsgHistoryManager {
    private SQLiteDatabase db;

    public MsgHistoryManager(Context context) {
        MessageSQL sql = new MessageSQL(context, "message", null, 1);
        db = sql.getReadableDatabase();
    }

    public void insert(String msg_id,String create_time,String app_id,int is_read,String title, String msg_content) {
        String sql = "insert into message values(null,?,?,?,?,?,?)";
        db.beginTransaction();
        db.execSQL(sql, new Object[]{msg_id, create_time,app_id,is_read,title,msg_content});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void closedb() {
        db.close();
    }

    //删除
    public void Remove(String _id) {
        String sql = "delete from message where _id=?";
        db.execSQL(sql, new Object[]{_id});
        db.close();
    }

    @SuppressLint("Range")
    public NotificationHistoryDataBaseBean query(String msg_id) {
        String sql = "select * from message where msg_id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{msg_id});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                NotificationHistoryDataBaseBean notificationHistoryDataBaseBean = new NotificationHistoryDataBaseBean();
                notificationHistoryDataBaseBean.set_id(cursor.getString(cursor.getColumnIndex("_id")));
                notificationHistoryDataBaseBean.setMsg_id(cursor.getString(cursor.getColumnIndex("msg_id")));
                notificationHistoryDataBaseBean.setApp_id(cursor.getString(cursor.getColumnIndex("app_id")));
                notificationHistoryDataBaseBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                notificationHistoryDataBaseBean.setCreate_time(cursor.getString(cursor.getColumnIndex("create_time")));
                notificationHistoryDataBaseBean.setIs_read(cursor.getInt(cursor.getColumnIndex("is_read")));
                notificationHistoryDataBaseBean.setMsg_content(JSON.parseObject(cursor.getString(cursor.getColumnIndex("msg_content")), DLUTNoticeContentBean.class));
                return notificationHistoryDataBaseBean;
            }
            cursor.close();
        }
        db.close();
        return null;
    }

    //修改
    public void update(String msg_id, String content,String appid) {
        try {
            String sql = "update message set msg_content=?,app_id=? where msg_id=?";
            db.execSQL(sql, new Object[]{content,appid,msg_id});
            db.close();
        } catch (Exception e) {
            db.close();
        }
    }

    //修改
    public void SetRead(String msg_id) {
        try {
            String sql = "update message set is_read=1 where msg_id=?";
            db.execSQL(sql, new Object[]{msg_id});
            db.close();
        } catch (Exception e) {
            db.close();
        }
    }

    //修改
    public void SetReadAll() {
        try {
            String sql = "update message set is_read=1 where is_read=0";
            db.execSQL(sql);
            db.close();
        } catch (Exception e) {
            db.close();
        }
    }

    //查询
    @SuppressLint("Range")
    public List<NotificationHistoryDataBaseBean> select() {
        String sql = "select * from message ORDER BY create_time";
        Cursor cursor = db.rawQuery(sql, null);
        List<NotificationHistoryDataBaseBean> strings = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                NotificationHistoryDataBaseBean notificationHistoryDataBaseBean = new NotificationHistoryDataBaseBean();
                notificationHistoryDataBaseBean.set_id(cursor.getString(cursor.getColumnIndex("_id")));
                notificationHistoryDataBaseBean.setMsg_id(cursor.getString(cursor.getColumnIndex("msg_id")));
                notificationHistoryDataBaseBean.setApp_id(cursor.getString(cursor.getColumnIndex("app_id")));
                notificationHistoryDataBaseBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                notificationHistoryDataBaseBean.setCreate_time(cursor.getString(cursor.getColumnIndex("create_time")));
                notificationHistoryDataBaseBean.setIs_read(cursor.getInt(cursor.getColumnIndex("is_read")));
                notificationHistoryDataBaseBean.setMsg_content(JSON.parseObject(cursor.getString(cursor.getColumnIndex("msg_content")), DLUTNoticeContentBean.class));
                strings.add(notificationHistoryDataBaseBean);
            }
            cursor.close();
        }
        db.close();
        return strings;
    }
}
