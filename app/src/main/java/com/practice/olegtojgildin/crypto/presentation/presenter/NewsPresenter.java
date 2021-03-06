package com.practice.olegtojgildin.crypto.presentation.presenter;

import com.practice.olegtojgildin.crypto.domain.news.NewsInteractor;
import com.practice.olegtojgildin.crypto.data.models.news.NewsList;
import com.practice.olegtojgildin.crypto.presentation.view.news.NewsView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class NewsPresenter {
    private NewsView newsView;
    private NewsInteractor newsInteractor;

    public NewsPresenter(NewsInteractor newsInteractor) {
        this.newsInteractor = newsInteractor;
    }

    public void attachView(NewsView view) {
        this.newsView = view;
    }

    public void loadNewsList(String categoryNews) {
        newsInteractor.getNews(categoryNews, "EN")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newsList -> newsView.setData(newsList),
                        throwable -> newsView.showError());

    }

}
