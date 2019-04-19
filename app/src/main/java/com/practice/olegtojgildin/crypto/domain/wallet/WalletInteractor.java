package com.practice.olegtojgildin.crypto.domain.wallet;

import com.practice.olegtojgildin.crypto.data.models.wallet.CoinWithCount;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public interface WalletInteractor {
    Observable<List<CoinWithCount>> getCoinWithCount();

    Observable<CryptoCoinFullInfo> getCoinsInfo(String fsyms, String tsyms);

}
