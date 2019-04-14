package com.practice.olegtojgildin.crypto.presentation.presenter;

import com.practice.olegtojgildin.crypto.domain.convert.ConvertInteractor;
import com.practice.olegtojgildin.crypto.domain.news.NewsInteractor;
import com.practice.olegtojgildin.crypto.presentation.view.convert.ConvertView;
import com.practice.olegtojgildin.crypto.presentation.view.news.NewsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class ConvertPresenter {
    private ConvertView convertView;
    private ConvertInteractor convertInteractor;

    public ConvertPresenter(ConvertInteractor convertInteractor) {
        this.convertInteractor = convertInteractor;
    }

    public void attachView(ConvertView view) {
        this.convertView = view;
    }

    public void loadSinglePrice(String from, String to) {
        convertInteractor.getSinglePrice(from, to)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singlePrice -> convertView.setmToCount(singlePrice),
                        throwable -> convertView.showError());

    }
}
