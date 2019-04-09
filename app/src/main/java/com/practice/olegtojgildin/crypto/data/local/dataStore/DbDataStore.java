package com.practice.olegtojgildin.crypto.data.local.dataStore;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public interface DbDataStore {
    Observable<List<String>> getFavorites();
}
