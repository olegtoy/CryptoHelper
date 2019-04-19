package com.practice.olegtojgildin.crypto.data.repositories;

import com.practice.olegtojgildin.crypto.data.datastore.web.WebDataStore;
import com.practice.olegtojgildin.crypto.domain.news.NewsRepository;
import com.practice.olegtojgildin.crypto.data.models.news.NewsList;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class NewsRepositoryImpl implements NewsRepository {

    private WebDataStore webDataStore;

    public NewsRepositoryImpl(WebDataStore webDataStore) {
        this.webDataStore = webDataStore;
    }

    @Override
    public Observable<NewsList> getNews(String category, String lang) {
        return webDataStore.getNews(category,lang);
    }
}
