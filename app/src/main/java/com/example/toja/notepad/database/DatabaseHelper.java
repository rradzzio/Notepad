package com.example.toja.notepad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.toja.notepad.database.model.Note;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Notes.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String note, String date) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Note.COL_NOTE, note);
        contentValues.put(Note.COL_DATE, date);
        long result = database.insert(Note.TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }

    public boolean updateData(long id, String note) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Note._ID, id);
        contentValues.put(Note.COL_NOTE, note);
        int result = database.update(Note.TABLE_NAME, contentValues,Note._ID + " = " + id, null);
        database.close();
        return result > 0;
    }
}
