package com.practice.olegtojgildin.crypto.data.models.topCurrency;

import com.google.gson.annotations.SerializedName;

/**
 * Created by olegtojgildin on 26/03/2019.
 */

public class TopCoin {
    @SerializedName("CoinInfo")
    public CryptoCoin cryptoCoin;

    @SerializedName("DISPLAY")
    public Display raw;

    public class Display {
        @SerializedName(value="USD", alternate={"EUR","RUB","JPY","GBP","CHF","CAD","AUD","CNY","HKD","NOK","SGD","RON"})
        public CryptoCurrency cryptoCurrency;
    }
}
