package com.example.xyzdictionary.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    public final static String IS_FAVORITE = "isFavorite";
    public final static String DB_NAME = "XYZDictionary.db";
    public final static int DB_VERSION = 1;

    public final static String WORDS = "Words";
    public final static String ID = "ID";
    public final static String WORD = "Word";

    public final static String DEFINITIONS = "Definitions";
    public final static String WORD_ID = "WordId";
    public final static String IMAGE_URL = "ImageUrl";
    public final static String TYPE = "Type";
    public final static String DEFINITION = "Definition";

    private static DBOpenHelper db = null;

    private DBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBOpenHelper getInstance(Context context){
        return db = (db == null)? new DBOpenHelper(context) : db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + WORDS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WORD + " TEXT NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE " + DEFINITIONS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WORD_ID + " INTEGER NOT NULL," +
                IMAGE_URL + " TEXT NOT NULL," +
                TYPE + " TEXT NOT NULL," +
                DEFINITION + " TEXT NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
