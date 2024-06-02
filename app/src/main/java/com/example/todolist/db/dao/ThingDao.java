package com.example.todolist.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todolist.db.domain.Thing;
import com.example.todolist.db.util.ThingOpenHelper;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.List;

public class ThingDao {
    private  static ThingDao thingDao = null;

    private final ThingOpenHelper thingOpenHelper;

    public ThingDao(Context context) {
        this.thingOpenHelper = new ThingOpenHelper(context);
    }

    public static ThingDao getInstance(Context context){
        if (thingDao == null) {
            thingDao = new ThingDao(context);
        }
        return thingDao;
    }

    // 增
    public void insert(Thing thing){
        SQLiteDatabase db = thingOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", thing.getTitle());
        values.put("des", thing.getDes());
        db.insert("thing", null, values);
        db.close();
    }

    // 删
    // 改
    // 查
    public List<Thing> findAll(){
        SQLiteDatabase db = thingOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("thing", new String[]{"_id", "title", "des"}, null, null, null, null, null);
        ArrayList<Thing> things = new ArrayList<>();
        while (cursor.moveToNext()) {
            Thing thing = new Thing();
            thing.setId(cursor.getInt(0));
            thing.setTitle(cursor.getString(1));
            thing.setDes(cursor.getString(2));
            things.add(thing);
        }
        cursor.close();
        db.close();
        return things;
    }
}
