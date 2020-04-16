package com.ck19.infodroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class InformationOpenHelper extends SQLiteOpenHelper {

    @NotNull
    @Contract("_ -> new")
    public static InformationOpenHelper getInstance(Context context) {
        return new InformationOpenHelper(context);
    }

    private static final int VERSION = 1;
    private static final String DB_NAME = "information.db";

    public static final int TYPE_SYSTEM = 0;
    public static final int TYPE_APPS = 1;
    public static final int TYPE_USERS = 2;
    public static final int TYPE_OTHERS = 3;

    public InformationOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS title (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(128)," +
                "type INTEGER" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS value (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "content VARCHAR(128)," +
                "title_id INTEGER," +
                "FOREIGN KEY(title_id) REFERENCES title(_id)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
