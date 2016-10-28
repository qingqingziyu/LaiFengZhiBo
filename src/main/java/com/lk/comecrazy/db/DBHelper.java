package com.lk.comecrazy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/26.
 */
public class DBHelper extends SQLiteOpenHelper {
    private final static String name = "users.db";
    private final static int version = 1;

    //, String name, SQLiteDatabase.CursorFactory factory, int version
    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE user (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,name CHAR(50),url CHAR(100))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
