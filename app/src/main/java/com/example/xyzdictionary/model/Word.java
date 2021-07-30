package com.example.xyzdictionary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xyzdictionary.util.DBOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class Word implements Serializable {
    private int id;
    private String word;
    private ArrayList<Definition> definitions;

    public Word(int id, String word, ArrayList<Definition> definitions) {
        this.id = id;
        this.word = word;
        this.definitions = definitions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public static boolean favoriteWordAlreadyExists(Context context, String word){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getReadableDatabase();
        String selection = DBOpenHelper.WORD + " LIKE ?";
        String[] selectionArgs = { word };

        Cursor cursor = db.query(
                DBOpenHelper.WORDS,
                new String[]{
                        DBOpenHelper.ID,
                        DBOpenHelper.WORD
                },
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.getCount() == 0)
            return false;
        return true;
    }

    public static ArrayList<Word> getMyFavoriteWords(Context context){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getReadableDatabase();

        Cursor cursor = db.query(
                DBOpenHelper.WORDS,
                new String[]{
                        DBOpenHelper.ID,
                        DBOpenHelper.WORD,
                },
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.getCount() == 0){
            return new ArrayList<>();
        }else{
            ArrayList<Word> words = new ArrayList<>();
            cursor.moveToFirst();
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.ID));
                words.add(new Word(
                        id,
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.WORD)),
                        Definition.getWordDefinitions(context, String.valueOf(id))
                ));
            }while(cursor.moveToNext());
            return words;
        }
    }

    public static long insertFavoriteWord(Context context, Word word){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBOpenHelper.WORD, word.getWord());

        return db.insert(DBOpenHelper.WORDS, null, cv);
    }

    public static int deleteFavoriteWord(Context context, String ID){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getWritableDatabase();
        String selection = DBOpenHelper.ID + " = ?";
        String[] selectionArgs = { ID };

        return db.delete(
                DBOpenHelper.WORDS,
                selection,
                selectionArgs
        );
    }
}
