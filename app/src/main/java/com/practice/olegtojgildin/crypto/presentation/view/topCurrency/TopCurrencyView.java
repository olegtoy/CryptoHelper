package com.practice.olegtojgildin.crypto.presentation.view.topCurrency;

import com.practice.olegtojgildin.crypto.data.models.news.NewsList;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinList;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public interface TopCurrencyView {
    void setData(CryptoCoinList newsList);
    void showError();
}
