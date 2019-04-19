package com.practice.olegtojgildin.crypto.presentation.presenter;

import com.practice.olegtojgildin.crypto.domain.topCurrency.TopCurrencyInteractor;
import com.practice.olegtojgildin.crypto.presentation.view.topCurrency.TopCurrencyView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class TopCurrencyPresenter {
    private TopCurrencyView topCurrencyView;
    private TopCurrencyInteractor topCurrencyInteractor;

    public TopCurrencyPresenter(TopCurrencyInteractor topCurrencyInteractor) {
        this.topCurrencyInteractor = topCurrencyInteractor;
    }

    public void attachView(TopCurrencyView view) {
        this.topCurrencyView = view;
    }

    public void loadTopCurrency(String limit, String tsym) {
        topCurrencyInteractor
                .getTopVolume(limit, tsym)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        cryptoCoinList -> topCurrencyView.setData(cryptoCoinList),
                        throwable -> topCurrencyView.showError());

    }
}
