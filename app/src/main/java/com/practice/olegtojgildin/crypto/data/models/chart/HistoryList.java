package com.practice.olegtojgildin.crypto.data.models.chart;

import com.google.gson.annotations.SerializedName;
import com.practice.olegtojgildin.crypto.data.models.chart.CryptoHistoryPoint;

import java.util.List;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class HistoryList {
    @SerializedName("Data")
    private List<CryptoHistoryPoint> items;

    public HistoryList(List<CryptoHistoryPoint> items) {
        this.items = items;
    }

    public List<CryptoHistoryPoint> getItems() {
        return items;
    }
}