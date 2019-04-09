package com.practice.olegtojgildin.crypto.data.local.dataStore;

import android.content.Context;

import com.practice.olegtojgildin.crypto.data.local.DbHelper;
import com.practice.olegtojgildin.crypto.data.local.DbManager;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public class DbDataStoreImpl implements DbDataStore {
    private DbManager dbManager;

    public DbDataStoreImpl(Context context) {
        this.dbManager = new DbManager(context);
    }

    @Override
    public Observable<List<String>> getFavorites() {
        return dbManager.getAllNotes();
    }
}
