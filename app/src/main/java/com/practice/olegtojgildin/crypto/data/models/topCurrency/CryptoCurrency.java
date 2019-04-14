package com.practice.olegtojgildin.crypto.data.models.topCurrency;


import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class CryptoCurrency {

    @SerializedName("FROMSYMBOL")
    private String fromSymbol;
    @SerializedName("TOSYMBOL")
    private String toSymbol;
    @SerializedName("MARKET")
    private String market;
    @SerializedName("PRICE")
    private String price;
    @SerializedName("LASTUPDATE")
    private String lastUpdate;
    @SerializedName("HIGHDAY")
    private String high;
    @SerializedName("LOWDAY")
    private String low;
    @SerializedName("CHANGEDAY")
    private String changeValue;
    @SerializedName("CHANGEPCT24HOUR")
    private String changePercent;
    @SerializedName("SUPPLY")
    private String supply;

    public CryptoCurrency(){

    }

    public CryptoCurrency(String fromSymbol,
                          String toSymbol,
                          String market,
                          String price,
                          String lastUpdate,
                          String high,
                          String low,
                          String changeValue,
                          String changePercent,
                          String supply)
    {
        this.fromSymbol = fromSymbol;
        this.toSymbol = toSymbol;
        this.market = market;
        this.price = price;
        this.lastUpdate = lastUpdate;
        this.high = high;
        this.low = low;
        this.changeValue = changeValue;
        this.changePercent = changePercent;

        this.supply = supply;

    }

    public String getFromSymbol() {
        return fromSymbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public String getMarket() {
        return market;
    }

    public String getPrice() {
        return price;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }


    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getChangeValue() {
        if (isChangePositive()) {
            return  "+" + changePercent;
        }

        return changeValue;
    }

    public String getChangePercent() {
        if (isChangePositive()) {
            return "+" + changePercent;
        }

        return changePercent;
    }

    public boolean isChangePositive(){
        if (changePercent.charAt(0) == '-'){
            return false;
        }

        return  true;
    }

    public String getSupply(){
        return supply;
    }


    public static Comparator<CryptoCurrency> PriceComparator = new Comparator<CryptoCurrency>() {

        public int compare(CryptoCurrency cryptoCurrency1, CryptoCurrency cryptoCurrency2) {
            Double price1 = Double.valueOf(cryptoCurrency1.getPrice());
            Double price2 = Double.valueOf(cryptoCurrency2.getPrice());

            //descending order
            return price2.compareTo(price1);
        }
    };

}
