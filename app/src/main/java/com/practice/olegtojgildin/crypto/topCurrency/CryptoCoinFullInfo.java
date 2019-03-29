package com.practice.olegtojgildin.crypto.topCurrency;

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

    public class Display{

        @SerializedName(value="BTC", alternate={"EOS","ETH","LTC", "XRP", "BCH", "QTUM", "PPT","ZB", "TRX", "NEO", "BNB", "DASH", "ZEC", "ADA", "OKB", "BGG", "MANA", "ETC", "XLM", "CELR", "OMG", "HT", "BSV", "ONT", "XMR", "WABI", "USDT", "RVN", "FET", "TCH", "GXS", "BAT", "EVX", "ABT","PAX","BTS", "TRUE", "XEM","AE","NULS"})
        public  Crypto crypto;

        public Crypto getCrypto() {
            return crypto;
        }

        public class  Crypto{
            @SerializedName(value="USD", alternate={"EUR","RUB","JPY","GBP","CHF","CAD","AUD","CNY","HKD","NOK","SGD","RON"})
            public Coin cryptoCurrency;

            public Coin getCryptoCurrency() {
                return cryptoCurrency;
            }

            public class Coin{
                private String PRICE;
                private String CHANGEDAY;
                private String HIGHDAY;
                private String LOWDAY;
                private String OPENDAY;
                private String MKTCAP;
                private String SUPPLY;
                private String CHANGEPCTDAY;
                private String VOLUME24HOUR;

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