package com.practice.olegtojgildin.crypto.domain.topCurrency;

import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinList;
import com.practice.olegtojgildin.crypto.domain.news.NewsRepository;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class TopCurrencyInteractorImpl implements TopCurrencyInteractor {
    @Override
    public Observable<CryptoCoinList> getTopVolume(String limit, String tsym) {
        return topCurrencyRepository.getTopVolume(limit,tsym);
    }

    private TopCurrencyRepository topCurrencyRepository;

    public TopCurrencyInteractorImpl(TopCurrencyRepository topCurrencyRepository) {
        this.topCurrencyRepository = topCurrencyRepository;
    }
}
