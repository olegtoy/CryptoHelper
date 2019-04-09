package com.practice.olegtojgildin.crypto.domain.favorites;

import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public class FavoritesInteractorImpl implements FavoritesInteractor {
    private FavoritesRepository favoritesRepository;

    public FavoritesInteractorImpl(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public Observable<List<String>> getFavorites() {
        return favoritesRepository.getFavorites();
    }

    @Override
    public Observable<CryptoCoinFullInfo> getCoinsInfo(String fsyms, String tsyms) {
        return favoritesRepository.getCoinsInfo(fsyms,tsyms);
    }
}
