package com.practice.olegtojgildin.crypto.data.datastore.web;

import com.practice.olegtojgildin.crypto.data.models.convert.SinglePrice;
import com.practice.olegtojgildin.crypto.data.models.news.NewsList;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinList;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public interface WebDataStore {

    Observable<NewsList> getNews(String category, String lang);

    Observable<CryptoCoinList> getTopVolume( String limit,  String tsym);

    Observable<CryptoCoinFullInfo> getCoinsInfo( String fsyms,  String tsyms);

    Single<SinglePrice> getSinglePrice( String fsym,  String tsyms);
}
