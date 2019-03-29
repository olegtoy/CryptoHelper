package com.practice.olegtojgildin.crypto.topCurrency;


import java.util.Comparator;

public class CryptoCoin {
    private String Name;
    private String ImageUrl;
    private String FullName;
    private String Algorithm;
    private String ProofType;

    public CryptoCoin(String name,
                      String imageUrl,
                      String fullName,
                      String algorithm,
                      String proofType) {

        this.Name = name;
        this.ImageUrl = imageUrl;
        this.FullName = fullName;
        this.Algorithm = algorithm;
        this.ProofType = proofType;

    }

    public String getName() {
        return Name;
    }

    public String getImageUrl() {
        return  "https://www.cryptocompare.com/"+ImageUrl;
    }

    public String getFullName() {
        return FullName;
    }

    public String getAlgorithm(){
        return Algorithm;
    }

    public String getProofType() {
        return ProofType;
    }



}
