package com.practice.olegtojgildin.crypto.presentation.presenter;

import android.util.Log;

import com.practice.olegtojgildin.crypto.data.datastore.WebDataStoreImpl;
import com.practice.olegtojgildin.crypto.data.models.news.NewsList;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinList;
import com.practice.olegtojgildin.crypto.data.repositories.TopCurrencyRepositoryImpl;
import com.practice.olegtojgildin.crypto.domain.news.NewsInteractor;
import com.practice.olegtojgildin.crypto.domain.topCurrency.TopCurrencyInteractor;
import com.practice.olegtojgildin.crypto.domain.topCurrency.TopCurrencyInteractorImpl;
import com.practice.olegtojgildin.crypto.presentation.view.news.NewsView;
import com.practice.olegtojgildin.crypto.presentation.view.topCurrency.TopCurrencyView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class TopCurrencyPresenter {
    private TopCurrencyView topCurrencyView;
    private TopCurrencyInteractor topCurrencyInteractor;

    public TopCurrencyPresenter(TopCurrencyInteractor topCurrencyInteractor) {
        this.topCurrencyInteractor=topCurrencyInteractor;
    }

    public void attachView(TopCurrencyView view) {
        this.topCurrencyView = view;
    }

    public void loadTopCurrency(String limit, String tsym) {

        topCurrencyInteractor
                .getTopVolume(limit,tsym)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CryptoCoinList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CryptoCoinList newsList) {
                        topCurrencyView.setData(newsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        //  mSwipeRefreshLayout.setRefreshing(false);

                    }
                });


    }
}
