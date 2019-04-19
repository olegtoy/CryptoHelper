package com.practice.olegtojgildin.crypto.data.repositories;

import com.practice.olegtojgildin.crypto.data.models.convert.SinglePrice;
import com.practice.olegtojgildin.crypto.data.datastore.web.WebDataStore;
import com.practice.olegtojgildin.crypto.domain.convert.ConvertRepository;

import io.reactivex.Single;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class ConvertRepositoryImpl implements ConvertRepository {
    private WebDataStore webDataStore;

    public ConvertRepositoryImpl(WebDataStore webDataStore) {
        this.webDataStore = webDataStore;
    }

    @Override
    public Single<SinglePrice> getSinglePrice(String fsym, String tsyms) {
        return webDataStore.getSinglePrice(fsym, tsyms);
    }
}
