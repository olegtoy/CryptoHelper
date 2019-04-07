package com.practice.olegtojgildin.crypto.domain.topCurrency;

import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinList;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public interface TopCurrencyRepository {
    Observable<CryptoCoinList> getTopVolume(String limit, String tsym);

}
