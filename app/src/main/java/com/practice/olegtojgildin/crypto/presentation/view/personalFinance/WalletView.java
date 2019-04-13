package com.practice.olegtojgildin.crypto.presentation.view.personalFinance;

import com.practice.olegtojgildin.crypto.data.local.PersonalFinance.CoinWithCount;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;

import java.util.List;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public interface WalletView {
    void setData(List<CryptoCoinFullInfo> newsList);
  //  void setDataWallet(List<CoinWithCount> list);
    void showError();
}
