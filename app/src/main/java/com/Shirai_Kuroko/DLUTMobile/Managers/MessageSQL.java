package com.Shirai_Kuroko.DLUTMobile.Managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MessageSQL extends SQLiteOpenHelper {
    public MessageSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL("CREATE TABLE message (_id integer primary key autoincrement, msg_id varchar(32), create_time varchar(32), app_id varchar(128), is_read integer DEFAULT 0, title text, msg_content text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
