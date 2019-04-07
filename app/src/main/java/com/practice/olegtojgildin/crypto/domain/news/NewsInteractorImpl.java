package com.practice.olegtojgildin.crypto.domain.news;

import com.practice.olegtojgildin.crypto.data.models.news.NewsList;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class NewsInteractorImpl implements NewsInteractor {
    private NewsRepository newsRepository;

    public NewsInteractorImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public Observable<NewsList> getNews(String category, String lang) {
        return newsRepository.getNews( category,  lang);
    }
}
