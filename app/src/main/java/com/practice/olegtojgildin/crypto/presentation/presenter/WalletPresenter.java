package com.practice.olegtojgildin.crypto.presentation.presenter;

import com.practice.olegtojgildin.crypto.domain.wallet.WalletInteractor;
import com.practice.olegtojgildin.crypto.presentation.view.personalFinance.WalletView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class WalletPresenter {
    private WalletView walletView;
    private WalletInteractor walletInteractor;

    public WalletPresenter(WalletInteractor favoritesInteractor) {
        this.walletInteractor = favoritesInteractor;
    }

    public void attachView(WalletView view) {
        this.walletView = view;
    }

    public void loadNewsList(List<String> listFav) {

        for (int i = 0; i < listFav.size(); i++) {
            walletInteractor.getCoinsInfo(listFav.get(i), "USD")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            coin -> {
                                walletView.setData(coin);
                            },
                            throwable -> {
                            });
        }
    }

    public void loadCoin() {
        walletInteractor.getCoinWithCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsList -> {
                    walletView.setDataWallet(newsList);
                }, throwable -> walletView.showError());
    }


}
