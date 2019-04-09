package com.practice.olegtojgildin.crypto.data.repositories;

import com.practice.olegtojgildin.crypto.data.datastore.WebDataStore;
import com.practice.olegtojgildin.crypto.data.local.dataStore.DbDataStore;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.domain.favorites.FavoritesRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public class FavoritesRepositoryImpl implements FavoritesRepository {

    private WebDataStore webDataStore;
    private DbDataStore dbDataStore;

    public FavoritesRepositoryImpl(WebDataStore webDataStore, DbDataStore dbDataStore) {
        this.webDataStore = webDataStore;
        this.dbDataStore=dbDataStore;
    }

    @Override
    public Observable<List<String>> getFavorites() {
        return dbDataStore.getFavorites();
    }

    @Override
    public Observable<CryptoCoinFullInfo> getCoinsInfo(String fsyms, String tsyms) {
        return webDataStore.getCoinsInfo(fsyms, tsyms);
    }
}
