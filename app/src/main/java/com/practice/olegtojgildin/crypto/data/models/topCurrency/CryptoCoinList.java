package com.practice.olegtojgildin.crypto.data.models.topCurrency;

import com.google.gson.annotations.SerializedName;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.TopCoin;

import java.util.List;

/**
 * Created by olegtojgildin on 26/03/2019.
 */

public class CryptoCoinList {
    @SerializedName("Data")
    private List<TopCoin> items;

    public CryptoCoinList(List<TopCoin> items) {
        this.items = items;
    }

    public List<TopCoin> getItems() {
        return items;
    }
}
