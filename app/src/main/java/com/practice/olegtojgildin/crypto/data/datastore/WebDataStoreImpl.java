package com.practice.olegtojgildin.crypto.data.datastore;

import com.practice.olegtojgildin.crypto.data.models.convert.SinglePrice;
import com.practice.olegtojgildin.crypto.data.api.RetrofitHelper;
import com.practice.olegtojgildin.crypto.data.models.news.NewsList;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinList;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class WebDataStoreImpl implements WebDataStore {
    @Override
    public Observable<NewsList> getNews(String category, String lang) {
        return new RetrofitHelper()
                .getService()
                .getNews(category, "EN");
    }

    @Override
    public Observable<CryptoCoinList> getTopVolume(String limit, String tsym) {
        return new RetrofitHelper()
                .getService()
                .getTopVolume(limit, tsym);
    }

    @Override
    public Observable<CryptoCoinFullInfo> getCoinsInfo(String fsyms, String tsyms) {
        return new RetrofitHelper()
                .getService()
                .getCoinsInfo(fsyms, tsyms);
    }

    @Override
    public Single<SinglePrice> getSinglePrice(String fsym, String tsyms) {
        return new RetrofitHelper()
                .getService()
                .getSinglePrice(fsym, tsyms);
    }
}
