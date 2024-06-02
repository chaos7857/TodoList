package com.example.todolist.db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ThingOpenHelper extends SQLiteOpenHelper {
    public ThingOpenHelper(Context context) {
        super(context, "thing.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table thing(_id integer primary key autoincrement,title varchar(20),des varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
