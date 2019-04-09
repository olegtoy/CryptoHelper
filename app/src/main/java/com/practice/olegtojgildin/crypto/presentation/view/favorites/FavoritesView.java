package com.practice.olegtojgildin.crypto.presentation.view.favorites;

import com.practice.olegtojgildin.crypto.data.models.news.NewsList;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;

import java.util.List;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public interface FavoritesView {
    void setData(CryptoCoinFullInfo newsList);
    void setDataFavoriteList(List<String> list);
    void showError();
}
