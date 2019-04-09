package com.practice.olegtojgildin.crypto.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public class DbManager {
    private DbHelper dbHelper;

    public DbManager(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    public void addCurrency(String note) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            if (db.inTransaction())
                addNoteInternal(db, getContentValues(note));
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null) {
                db.endTransaction();
            }
            db.close();
        }
    }


    public void deleteNote(String note) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            if (db.inTransaction())
                db.delete(DbHelper.FAVORITES_TABLE, String.format("%1$s= '%2$s'", DbHelper.NAME, note), null);
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null) {
                db.endTransaction();
            }
            db.close();
        }
    }

    public Observable<List<String>> getAllNotes() {
        List<String> mNoteList = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            db.beginTransaction();
            String selectQuery = "SELECT  * FROM " + DbHelper.FAVORITES_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst() && db.inTransaction()) {
                do {
                    mNoteList.add(new String(cursor.getString(1)));
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null) {
                db.endTransaction();
            }
            db.close();
        }
        return Observable.fromArray(mNoteList);
    }


    private ContentValues getContentValues(String note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.NAME, note);
        return contentValues;
    }

    private void addNoteInternal(SQLiteDatabase db, ContentValues contentValues) {
        db.insertOrThrow(DbHelper.FAVORITES_TABLE, null, contentValues);
    }
}