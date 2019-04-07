package com.practice.olegtojgildin.crypto.data.models.news;

import com.google.gson.annotations.SerializedName;
import com.practice.olegtojgildin.crypto.data.models.news.CryptoNewsArticle;

import java.util.List;

/**
 * Created by olegtojgildin on 16/03/2019.
 */

public class NewsList {
    @SerializedName("Data")
    private List<CryptoNewsArticle> items;

    public NewsList(List<CryptoNewsArticle> items) {
        this.items = items;
    }

    public List<CryptoNewsArticle> getItems() {
        return items;
    }
}