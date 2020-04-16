package com.ck19.infodroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class InformationDao {

    private SQLiteDatabase db;

    public InformationDao(Context context) {
        db = InformationOpenHelper.getInstance(context).getWritableDatabase();
    }

    public static final int TYPE_SYSTEM = 0;
    public static final int TYPE_APPS = 1;
    public static final int TYPE_USERS = 2;
    public static final int TYPE_OTHERS = 3;


    /**
     * @param title The information's key you want to save, like "Location"
     * @param value The information's content you want to save
     * @param type  TYPE_SYSTEM, TYPE_APPS, TYPE_USERS, TYPE_OTHERS
     */
    public void insert(String title, String value, int type) {
        Cursor cursor = this.db.query("title", new String[]{"_id"}, "name=? AND type=?",
                new String[]{title, Integer.toString(type)},
                null, null, null);
        while (cursor.moveToNext()) {
            int _idIndex = cursor.getColumnIndex("_id");
            int _id = Integer.parseInt(cursor.getString(_idIndex));

            ContentValues valueValues = new ContentValues();
            valueValues.put("content", value);
            valueValues.put("title_id", _id);
            this.db.insert("value", null, valueValues);
            cursor.close();
            return;
        }
        cursor.close();
        ContentValues titleValues = new ContentValues();
        titleValues.put("name", title);
        titleValues.put("type", type);
        int titleId = (int) this.db.insert("title", null, titleValues);

        ContentValues valueValues = new ContentValues();
        valueValues.put("content", value);
        valueValues.put("title_id", titleId);
        this.db.insert("value", null, valueValues);
    }

    /**
     * @param title  The information's key you want to save, like "Location"
     * @param values The information's contents you want to save
     * @param type   TYPE_SYSTEM, TYPE_APPS, TYPE_USERS, TYPE_OTHERS
     */
    public void insert(String title, String[] values, int type) {
        Cursor cursor = this.db.query("title", new String[]{"_id"}, "name=? AND type=?",
                new String[]{title, Integer.toString(type)},
                null, null, null);
        while (cursor.moveToNext()) {
            int _idIndex = cursor.getColumnIndex("_id");
            int _id = Integer.parseInt(cursor.getString(_idIndex));

            for (String value : values) {
                ContentValues valueValues = new ContentValues();
                valueValues.put("content", value);
                valueValues.put("title_id", _id);
                this.db.insert("value", null, valueValues);
            }
            cursor.close();
            return;
        }
        cursor.close();
        ContentValues titleValues = new ContentValues();
        titleValues.put("name", title);
        titleValues.put("type", type);
        int titleId = (int) this.db.insert("title", null, titleValues);

        for (String value : values) {
            ContentValues valueValues = new ContentValues();
            valueValues.put("content", value);
            valueValues.put("title_id", titleId);
            this.db.insert("value", null, valueValues);
        }
    }


    /**
     * @param type TYPE_SYSTEM, TYPE_APPS, TYPE_USERS, TYPE_OTHERS
     * @return     Results saved before.
     */
    public HashMap<String, Object> queryAll(int type) {

        HashMap<String, Object> results = new HashMap<String, Object>();

        Cursor titleCursor = this.db.query("title", new String[]{"_id", "name"},
                "type=?", new String[]{Integer.toString(type)},
                null, null, null);
        while (titleCursor.moveToNext()) {
            int _idIndex = titleCursor.getColumnIndex("_id");
            int _id = Integer.parseInt(titleCursor.getString(_idIndex));

            int nameIndex = titleCursor.getColumnIndex("name");
            String name = titleCursor.getString(nameIndex);

            ArrayList<String> contents = new ArrayList<>();
            Cursor valueCursor = this.db.query("value", new String[]{"content"},
                    "title_id=?", new String[]{Integer.toString(_id)},
                    null, null, null);
            while (valueCursor.moveToNext()) {
                int contentIndex = valueCursor.getColumnIndex("content");
                String content = valueCursor.getString(contentIndex);
                contents.add(content);
            }
            valueCursor.close();

            if (contents.size() == 1) {
                results.put(name, contents.get(0));
            } else {
                String[] values = contents.toArray(new String[contents.size()]);
                results.put(name, values);
            }
        }
        titleCursor.close();
        return results;
    }

    public void clearAll() {
        this.db.execSQL("DELETE FROM title;");
        this.db.execSQL("DELETE FROM value;");
    }
}
