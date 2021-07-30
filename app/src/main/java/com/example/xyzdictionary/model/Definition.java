package com.example.xyzdictionary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xyzdictionary.util.DBOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class Definition implements Serializable {
    private int id;
    private int wordId;
    private String imageUrl;
    private String type;
    private String definition;

    public Definition(int id, int wordId, String imageUrl, String type, String definition) {
        this.id = id;
        this.wordId = wordId;
        this.imageUrl = imageUrl;
        this.type = type;
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public static ArrayList<Definition> getWordDefinitions(Context context, String wordId){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getReadableDatabase();
        String selection = DBOpenHelper.WORD_ID + " = ?";
        String[] selectionArgs = { wordId };

        Cursor cursor = db.query(
                DBOpenHelper.DEFINITIONS,
                new String[]{
                        DBOpenHelper.ID,
                        DBOpenHelper.WORD_ID,
                        DBOpenHelper.IMAGE_URL,
                        DBOpenHelper.TYPE,
                        DBOpenHelper.DEFINITION
                },
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.getCount() == 0){
            return new ArrayList<>();
        }else{
            ArrayList<Definition> definitions = new ArrayList<>();
            cursor.moveToFirst();
            do{
                definitions.add(new Definition(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.WORD_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.IMAGE_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.DEFINITION))
                ));
            }while(cursor.moveToNext());
            return definitions;
        }
    }

    public static void insertWordDefinitions(Context context, int wordId, ArrayList<Definition> definitions){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getWritableDatabase();

        for (Definition def : definitions) {
            ContentValues cv = new ContentValues();
            cv.put(DBOpenHelper.WORD_ID, wordId);
            cv.put(DBOpenHelper.IMAGE_URL, def.getImageUrl());
            cv.put(DBOpenHelper.TYPE, def.getType());
            cv.put(DBOpenHelper.DEFINITION, def.getDefinition());

            db.insert(DBOpenHelper.DEFINITIONS, null, cv);
        }
    }

    public static void deleteDefinitions(Context context, String wordId){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getWritableDatabase();
        String selection = DBOpenHelper.WORD_ID + " = ?";
        String[] selectionArgs = { wordId };

        db.delete(
                DBOpenHelper.DEFINITIONS,
                selection,
                selectionArgs
        );
    }
}
