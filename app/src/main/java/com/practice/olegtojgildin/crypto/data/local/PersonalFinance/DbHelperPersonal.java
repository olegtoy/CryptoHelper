package com.practice.olegtojgildin.crypto.data.local.PersonalFinance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class DbHelperPersonal extends SQLiteOpenHelper {

    public static final int VERSION_DB=4;
    public static final String DB_NAME="Currency1.db";
    public static final String PERSONAL_TABLE="PERSONAL";
    public static final String COUNT="COUNT";
    public static final String COIN="COIN";
    public static final String ID="ID";


    public DbHelperPersonal(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DbHelperPersonal(Context context){
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
        database.execSQL( "CREATE TABLE PERSONAL(id integer primary key AUTOINCREMENT, coin text UNIQUE, count real not null)" );
    }
    private void deleteTables(SQLiteDatabase database){
        database.execSQL( "DROP TABLE IF EXISTS PERSONAL" );
    }
}
