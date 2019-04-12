package com.practice.olegtojgildin.crypto.data.local.dataStore;

import com.practice.olegtojgildin.crypto.data.local.PersonalFinance.CoinWithCount;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public interface DbDataStore {
    Observable<List<String>> getFavorites();
    Observable<List<CoinWithCount>> getCoinWithCount();
}
