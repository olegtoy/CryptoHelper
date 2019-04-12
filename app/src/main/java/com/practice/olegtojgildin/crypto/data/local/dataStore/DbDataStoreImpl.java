package com.practice.olegtojgildin.crypto.data.local.dataStore;

import android.content.Context;

import com.practice.olegtojgildin.crypto.data.local.DbHelper;
import com.practice.olegtojgildin.crypto.data.local.DbManager;
import com.practice.olegtojgildin.crypto.data.local.PersonalFinance.CoinWithCount;
import com.practice.olegtojgildin.crypto.data.local.PersonalFinance.DbManagerPersonal;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public class DbDataStoreImpl implements DbDataStore {
    private DbManager dbManager;
    private DbManagerPersonal dbManagerPersonal;

    public DbDataStoreImpl(Context context) {
        this.dbManager = DbManager.getInstance(context);
        this.dbManagerPersonal=DbManagerPersonal.getInstance(context);
    }

    @Override
    public Observable<List<String>> getFavorites() {
        return dbManager.getAllNotes();
    }

    @Override
    public Observable<List<CoinWithCount>> getCoinWithCount() {
        return dbManagerPersonal.getAllNotes();
    }
}
