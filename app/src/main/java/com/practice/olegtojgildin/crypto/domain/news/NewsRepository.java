package com.practice.olegtojgildin.crypto.domain.news;

import com.practice.olegtojgildin.crypto.data.models.news.NewsList;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public interface NewsRepository {
    Observable<NewsList> getNews(String category, String lang);
}
