package com.practice.olegtojgildin.crypto.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int VERSION_DB=2;
    public static final String DB_NAME="Currency.db";
    public static final String FAVORITES_TABLE="FAVORITES";
    public static final String NAME="NAME";
    public static final String ID="id";

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DbHelper(Context context){
        this(context,DB_NAME,null,VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createEmptyTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        deleteTables(db);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }

    public void createEmptyTables(SQLiteDatabase database){
        database.execSQL( "CREATE TABLE FAVORITES(id integer primary key AUTOINCREMENT, name text not null)" );
    }
    private void deleteTables(SQLiteDatabase database){
        database.execSQL( "DROP TABLE IF EXISTS FAVORITES" );
    }
}