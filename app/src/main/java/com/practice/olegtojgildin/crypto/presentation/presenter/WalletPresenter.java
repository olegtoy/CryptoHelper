package com.practice.olegtojgildin.crypto.presentation.presenter;

import android.util.Log;

import com.practice.olegtojgildin.crypto.data.local.PersonalFinance.CoinWithCount;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.domain.wallet.WalletInteractor;
import com.practice.olegtojgildin.crypto.presentation.view.personalFinance.WalletView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class WalletPresenter {
    private WalletView walletView;
    private WalletInteractor walletInteractor;

    public WalletPresenter(WalletInteractor favoritesInteractor) {
        this.walletInteractor = favoritesInteractor;
        coinWithCountList = new ArrayList<>();
        cryptoCoinFullInfos = new ArrayList<>();
    }


    public void attachView(WalletView view) {
        this.walletView = view;
    }

    public List<CoinWithCount> coinWithCountList;
    public List<CryptoCoinFullInfo> cryptoCoinFullInfos;

    private void loadNewsList(List<CoinWithCount> listFav) {


        for (int i = 0; i < listFav.size(); i++) {
            double count = coinWithCountList.get(i).getCount();
            String coin = coinWithCountList.get(i).getCoin();

            walletInteractor.getCoinsInfo(listFav.get(i).getCoin(), "USD")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CryptoCoinFullInfo>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(CryptoCoinFullInfo cryptoCoinFullInfo) {

                            cryptoCoinFullInfo.display.crypto.cryptoCurrency.setCountCoin(count);
                            cryptoCoinFullInfo.display.crypto.cryptoCurrency.setNameCoin(coin);
                            cryptoCoinFullInfos.add(cryptoCoinFullInfo);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            walletView.setData(cryptoCoinFullInfos);
                            Log.d("SIZEFull", Integer.toString(cryptoCoinFullInfos.size()));

                        }
                    });

        }

    }

    public void loadCoin() {
        walletInteractor.getCoinWithCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CoinWithCount>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        coinWithCountList.clear();
                        cryptoCoinFullInfos.clear();
                    }

                    @Override
                    public void onNext(List<CoinWithCount> coinWithCounts) {
                        coinWithCountList.addAll(coinWithCounts);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        Log.d("SIZEcount", Integer.toString(coinWithCountList.size()));
                        loadNewsList(coinWithCountList);
                    }
                });
    }


}
