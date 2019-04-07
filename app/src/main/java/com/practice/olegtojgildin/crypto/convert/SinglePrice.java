package com.practice.olegtojgildin.crypto.convert;

import com.google.gson.annotations.SerializedName;

/**
 * Created by olegtojgildin on 29/03/2019.
 */

public class SinglePrice {
    @SerializedName(value = "USD", alternate = {"RUB", "EUR", "JPY", "GBR", "CHF", "CAD", "AUD", "CNY", "HKD", "NOK", "SGD", "RON", "EOS", "ETH", "LTC", "XRP", "BCH", "QTUM", "PPT", "ZB", "TRX", "NEO", "BNB", "DASH", "ZEC", "ADA", "OKB", "BGG", "MANA", "ETC", "XLM", "CELR", "OMG", "HT", "BSV", "ONT", "XMR", "WABI", "USDT", "RVN", "FET", "TCH", "GXS", "BAT", "EVX", "ABT", "PAX", "BTS", "TRUE", "XEM", "AE", "NULS","BTC"})
    private String prices;

    public String getPrices() {
        return prices;
    }
}
