package com.practice.olegtojgildin.crypto.data.models.topCurrency;

import com.google.gson.annotations.SerializedName;

/**
 * Created by olegtojgildin on 28/03/2019.
 */

public class CryptoCoinFullInfo {

    @SerializedName("DISPLAY")
    public Display display;

    public Display getDisplay() {
        return display;
    }

    public class Display {

        @SerializedName(value = "BTC", alternate = {"EOS", "ETH", "LTC", "XRP", "BCH", "QTUM", "PPT", "ZB", "TRX", "NEO", "BNB", "DASH", "ZEC", "ADA", "OKB", "BGG", "MANA", "ETC", "XLM", "CELR", "OMG", "HT", "BSV", "ONT", "XMR", "WABI", "USDT", "RVN", "FET", "TCH", "GXS", "BAT", "EVX", "ABT", "PAX", "BTS", "TRUE", "XEM", "AE", "NULS"})
        public Crypto crypto;

        public Crypto getCrypto() {
            return crypto;
        }

        public class Crypto {
            @SerializedName(value = "USD", alternate = {"EUR", "RUB", "JPY", "GBP", "CHF", "CAD", "AUD", "CNY", "HKD", "NOK", "SGD", "RON"})
            public Coin cryptoCurrency;

            public Coin getCryptoCurrency() {
                return cryptoCurrency;
            }

            public class Coin {
                private String nameCoin;
                private Double countCoin;
                private String FROMSYMBOL;
                private String TOSYMBOL;
                private String PRICE;
                private String CHANGE24HOUR;
                private String HIGH24HOUR;
                private String LOW24HOUR;
                private String OPENDAY;
                private String MKTCAP;
                private String SUPPLY;
                private String CHANGEPCT24HOUR;
                private String VOLUME24HOUR;
                private String IMAGEURL;
                private String MARKET;

                public String getTOSYMBOL() {
                    return TOSYMBOL;
                }

                public String getNameCoin() {
                    return nameCoin;
                }

                public void setNameCoin(String nameCoin) {
                    this.nameCoin = nameCoin;
                }

                public void setCountCoin(Double countCoin) {
                    this.countCoin = countCoin;
                }

                public Double getCountCoin() {
                    return countCoin;
                }

                public String getMARKET() {
                    return MARKET;
                }

                public String getIMAGEURL() {
                    return "https://www.cryptocompare.com/"+IMAGEURL;
                }

                public String getFROMSYMBOL() {
                    return FROMSYMBOL;
                }

                public String getPRICE() {
                    return PRICE;
                }

                public String getCHANGE24HOUR() {
                    return CHANGE24HOUR;
                }

                public String getHIGH24HOUR() {
                    return HIGH24HOUR;
                }

                public String getLOW24HOUR() {
                    return LOW24HOUR;
                }

                public String getOPENDAY() {
                    return OPENDAY;
                }

                public String getMKTCAP() {
                    return MKTCAP;
                }

                public String getSUPPLY() {
                    return SUPPLY;
                }

                public String getCHANGEPCT24HOUR() {
                    return CHANGEPCT24HOUR;
                }

                public String getVOLUME24HOUR() {
                    return VOLUME24HOUR;
                }
            }
        }

    }

    @SerializedName("RAW")
    public Raw raw;

    public Raw getRaw() {
        return raw;
    }

    public class Raw {

        @SerializedName(value = "BTC", alternate = {"EOS", "ETH", "LTC", "XRP", "BCH", "QTUM", "PPT", "ZB", "TRX", "NEO", "BNB", "DASH", "ZEC", "ADA", "OKB", "BGG", "MANA", "ETC", "XLM", "CELR", "OMG", "HT", "BSV", "ONT", "XMR", "WABI", "USDT", "RVN", "FET", "TCH", "GXS", "BAT", "EVX", "ABT", "PAX", "BTS", "TRUE", "XEM", "AE", "NULS"})
        public Crypto crypto;

        public Crypto getCrypto() {
            return crypto;
        }

        public class Crypto {
            @SerializedName(value = "USD", alternate = {"EUR", "RUB", "JPY", "GBP", "CHF", "CAD", "AUD", "CNY", "HKD", "NOK", "SGD", "RON"})
            public Coin cryptoCurrency;

            public Coin getCryptoCurrency() {
                return cryptoCurrency;
            }

            public class Coin {
                private String nameCoin;
                private Double countCoin;
                private String FROMSYMBOL;
                private String TOSYMBOL;
                private String PRICE;
                private String CHANGEDAY;
                private String HIGHDAY;
                private String LOWDAY;
                private String OPENDAY;
                private String MKTCAP;
                private String SUPPLY;
                private String CHANGEPCTDAY;
                private String VOLUME24HOUR;
                private String IMAGEURL;
                private String MARKET;

                public String getTOSYMBOL() {
                    return TOSYMBOL;
                }

                public String getNameCoin() {
                    return nameCoin;
                }

                public void setNameCoin(String nameCoin) {
                    this.nameCoin = nameCoin;
                }

                public void setCountCoin(Double countCoin) {
                    this.countCoin = countCoin;
                }

                public Double getCountCoin() {
                    return countCoin;
                }

                public String getMARKET() {
                    return MARKET;
                }

                public String getIMAGEURL() {
                    return "https://www.cryptocompare.com/"+IMAGEURL;
                }

                public String getFROMSYMBOL() {
                    return FROMSYMBOL;
                }

                public String getPRICE() {
                    return PRICE;
                }

                public String getCHANGEDAY() {
                    return CHANGEDAY;
                }

                public String getHIGHDAY() {
                    return HIGHDAY;
                }

                public String getLOWDAY() {
                    return LOWDAY;
                }

                public String getOPENDAY() {
                    return OPENDAY;
                }

                public String getMKTCAP() {
                    return MKTCAP;
                }

                public String getSUPPLY() {
                    return SUPPLY;
                }

                public String getCHANGEPCTDAY() {
                    return CHANGEPCTDAY;
                }

                public String getVOLUME24HOUR() {
                    return VOLUME24HOUR;
                }
            }
        }

    }
}
