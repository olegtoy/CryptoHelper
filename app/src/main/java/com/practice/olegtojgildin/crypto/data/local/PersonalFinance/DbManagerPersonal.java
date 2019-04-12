package com.practice.olegtojgildin.crypto.data.local.PersonalFinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.practice.olegtojgildin.crypto.data.local.DbHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class DbManagerPersonal {

    private static volatile DbManagerPersonal INSTANCE;

    public static DbManagerPersonal getInstance(final Context context) {
        DbManagerPersonal instance = INSTANCE;
        if (instance == null) {
            synchronized (DbManagerPersonal.class) {
                instance = INSTANCE;
                if (instance == null) {
                    instance = INSTANCE = new DbManagerPersonal(context);
                }
            }
        }
        return instance;
    }

    private DbHelperPersonal dbHelper;

    public DbManagerPersonal(Context context) {
        this.dbHelper = new DbHelperPersonal(context);
    }

    public void addCurrency(String name, double count) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            if (db.inTransaction())
                addNoteInternal(db, getContentValues(new CoinWithCount(name, count)));
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

    public void updateCurrency(String name, double count) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();

            double oldCount;
            if (db.inTransaction()) {
                String selectQuery = "SELECT  * FROM " + DbHelperPersonal.PERSONAL_TABLE + " WHERE " + String.format("%1$s ='%2$s'", DbHelperPersonal.COIN, name);
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor != null && cursor.moveToFirst() && db.inTransaction()) {
                    oldCount = Double.parseDouble(cursor.getString(2));
                    db.update(DbHelperPersonal.PERSONAL_TABLE, getContentValues(new CoinWithCount(name, count+oldCount)), String.format("%1$s='%2$s'", DbHelperPersonal.COIN, name), null);
                }
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
    }


    public void deleteNote(String name) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            if (db.inTransaction())
                db.delete(DbHelperPersonal.PERSONAL_TABLE, String.format("%1$s= '%2$s'", DbHelperPersonal.COIN, name), null);
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

    public Observable<List<CoinWithCount>> getAllNotes() {
        List<CoinWithCount> mNoteList = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            db.beginTransaction();
            String selectQuery = "SELECT  * FROM " + DbHelperPersonal.PERSONAL_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst() && db.inTransaction()) {
                do {
                    mNoteList.add(new CoinWithCount(cursor.getString(1), Double.parseDouble(cursor.getString(2))));
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


    private ContentValues getContentValues(CoinWithCount coinWithCount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelperPersonal.COIN, coinWithCount.getCoin());
        contentValues.put(DbHelperPersonal.COUNT, coinWithCount.getCount());
        return contentValues;
    }

    private void addNoteInternal(SQLiteDatabase db, ContentValues contentValues) {
        db.insertOrThrow(DbHelperPersonal.PERSONAL_TABLE, null, contentValues);
    }
}
