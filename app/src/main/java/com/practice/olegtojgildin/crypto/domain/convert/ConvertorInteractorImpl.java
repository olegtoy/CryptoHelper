package com.practice.olegtojgildin.crypto.domain.convert;

import com.practice.olegtojgildin.crypto.data.models.convert.SinglePrice;

import io.reactivex.Single;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class ConvertorInteractorImpl implements ConvertInteractor {
    private ConvertRepository convertRepository;

    public ConvertorInteractorImpl(ConvertRepository newsRepository) {
        this.convertRepository = newsRepository;
    }


    @Override
    public Single<SinglePrice> getSinglePrice(String fsym, String tsyms) {
        return convertRepository.getSinglePrice(fsym,tsyms);
    }
}
