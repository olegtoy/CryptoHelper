package com.practice.olegtojgildin.crypto.data.repositories;

import com.practice.olegtojgildin.crypto.data.datastore.web.WebDataStore;
import com.practice.olegtojgildin.crypto.data.models.wallet.CoinWithCount;
import com.practice.olegtojgildin.crypto.data.local.dataStore.DbDataStore;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.domain.wallet.WalletRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class WalletRepositoryImpl implements WalletRepository {

    private WebDataStore webDataStore;
    private DbDataStore dbDataStore;

    public WalletRepositoryImpl(WebDataStore webDataStore, DbDataStore dbDataStore) {
        this.webDataStore = webDataStore;
        this.dbDataStore=dbDataStore;
    }

    @Override
    public Observable<List<CoinWithCount>> getCoinWithCount() {
        return dbDataStore.getCoinWithCount();
    }

    @Override
    public Observable<CryptoCoinFullInfo> getCoinsInfo(String fsyms, String tsyms) {
        return webDataStore.getCoinsInfo(fsyms, tsyms);
    }
}
