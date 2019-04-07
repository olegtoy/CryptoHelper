package com.practice.olegtojgildin.crypto.presentation.view.news;

import com.practice.olegtojgildin.crypto.data.models.news.NewsList;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public interface NewsView {
    void setData(NewsList newsList);

    void showError();
}
