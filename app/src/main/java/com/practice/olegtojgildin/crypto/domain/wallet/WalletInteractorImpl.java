package com.practice.olegtojgildin.crypto.domain.wallet;

import com.practice.olegtojgildin.crypto.data.local.wallet.CoinWithCount;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class WalletInteractorImpl implements WalletInteractor {

    private WalletRepository walletRepository;

    public WalletInteractorImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Observable<List<CoinWithCount>> getCoinWithCount() {
        return walletRepository.getCoinWithCount();
    }

    @Override
    public Observable<CryptoCoinFullInfo> getCoinsInfo(String fsyms, String tsyms) {
        return walletRepository.getCoinsInfo(fsyms,tsyms);
    }
}
