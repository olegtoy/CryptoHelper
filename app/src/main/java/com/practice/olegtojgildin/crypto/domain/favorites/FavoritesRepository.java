package com.practice.olegtojgildin.crypto.domain.favorites;

import com.practice.olegtojgildin.crypto.data.models.news.NewsList;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public interface FavoritesRepository {
    Observable<List<String>> getFavorites();
    Observable<CryptoCoinFullInfo> getCoinsInfo(String fsyms, String tsyms);
}
