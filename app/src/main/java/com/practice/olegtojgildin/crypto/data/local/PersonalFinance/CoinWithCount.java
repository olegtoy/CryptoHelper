package com.practice.olegtojgildin.crypto.data.local.PersonalFinance;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class CoinWithCount {
    private String coin;
    private double count;

    public String getCoin() {
        return coin;
    }

    public double getCount() {
        return count;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public CoinWithCount(String coin, double count) {
        this.coin = coin;
        this.count = count;
    }
}
