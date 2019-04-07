package com.practice.olegtojgildin.crypto.data.repositories;

import com.practice.olegtojgildin.crypto.data.datastore.WebDataStore;
import com.practice.olegtojgildin.crypto.data.models.news.NewsList;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinList;
import com.practice.olegtojgildin.crypto.domain.topCurrency.TopCurrencyRepository;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class TopCurrencyRepositoryImpl implements TopCurrencyRepository{
    private WebDataStore webDataStore;

    public TopCurrencyRepositoryImpl(WebDataStore webDataStore) {
        this.webDataStore = webDataStore;
    }

    @Override
    public Observable<CryptoCoinList> getTopVolume(String limit, String tsym) {
        return webDataStore.getTopVolume(limit,tsym);
    }
}
