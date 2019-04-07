package com.practice.olegtojgildin.crypto.data.datastore;

import com.practice.olegtojgildin.crypto.data.api.RetrofitHelper;
import com.practice.olegtojgildin.crypto.data.models.news.NewsList;

import io.reactivex.Observable;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public class WebDataStoreImpl implements WebDataStore {
    @Override
    public Observable<NewsList> getNews(String category, String lang) {
        return new RetrofitHelper()
                .getService()
                .getNews(category, "EN");
    }
}
