package com.practice.olegtojgildin.crypto.presentation.view.convert;

import com.practice.olegtojgildin.crypto.data.models.convert.SinglePrice;
import com.practice.olegtojgildin.crypto.data.models.news.NewsList;

/**
 * Created by olegtojgildin on 07/04/2019.
 */

public interface ConvertView {
    void setData(NewsList newsList);
    void showError();
    void setmToCount(SinglePrice price);
}
