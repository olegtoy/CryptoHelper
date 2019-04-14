package com.practice.olegtojgildin.crypto.presentation.presenter;

import android.util.Log;

import com.practice.olegtojgildin.crypto.domain.favorites.FavoritesInteractor;
import com.practice.olegtojgildin.crypto.presentation.view.favorites.FavoritesView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public class FavoritesPresenter {
    private FavoritesView favoritesView;
    private FavoritesInteractor favoritesInteractor;

    public FavoritesPresenter(FavoritesInteractor favoritesInteractor) {
        this.favoritesInteractor = favoritesInteractor;
    }

    public void attachView(FavoritesView view) {
        this.favoritesView = view;
    }

    public void loadNewsList(List<String> listFav,String toSymbol) {
        Log.d("SIZE2",Integer.toString(listFav.size()));
        for (int i = 0; i < listFav.size(); i++) {
            favoritesInteractor.getCoinsInfo(listFav.get(i), toSymbol)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            coin -> {
                                favoritesView.setData(coin);
                            },
                            throwable -> {
                            });
        }
    }

    public void loadCoin() {
        favoritesInteractor.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsList -> {
                    favoritesView.setDataFavoriteList(newsList);
                }, throwable -> favoritesView.showError());
    }


}
