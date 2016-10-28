package com.lk.comecrazy.myself.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lk.comecrazy.db.DBHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/20.
 */
public class UserDbModel {


    private SQLiteDatabase mDb;

    //创建操作数据库的对象
    public UserDbModel(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.mDb = dbHelper.getWritableDatabase();
    }


    //把照片存到本地数据库
    public void insert(String name, String imageUrl) {
        //把拍照头像保存到本地数据库中
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("url", imageUrl);
        mDb.insert("user", null, values);
    }

    //读取数据库中的数据
    public Map<String, String> read() {
        Map<String, String> map = new HashMap<>();
        Cursor cursor = mDb.rawQuery("select*from user", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                map.put("name", name);
                map.put("url", url);
            }
            //记得关流防止OOM
            cursor.close();
        }
        return map;
    }

    //判断数据库是否为空
    public boolean isDBNull() {
        String name = null;
        String url = null;
        Cursor cursor = mDb.rawQuery("select*from user", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex("name"));
                url = cursor.getString(cursor.getColumnIndex("url"));

            }
            //记得关流防止OOM
            cursor.close();
        }
        if (name == null || url == null) {
            return true;
        }
        return false;
    }


    //删除表
    public void deleteTable() {
        mDb.execSQL("delete from user");
    }
}
