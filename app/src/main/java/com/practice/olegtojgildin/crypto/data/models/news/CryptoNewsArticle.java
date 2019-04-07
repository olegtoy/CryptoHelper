package com.practice.olegtojgildin.crypto.data.models.news;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by olegtojgildin on 16/03/2019.
 */

public class CryptoNewsArticle implements Parcelable{

    private String title;
    private String body;
    private String url;
    private String imageurl;
    private String source;

    private Long published_on;
    private Integer sortOrder;

    public CryptoNewsArticle(String title, String body, String url, String imageUrl, String source, Long date, Integer sortOrder) {

        this.title = title;
        this.body = body;
        this.url = url;
        this.imageurl = imageUrl;
        this.source = source;
        this.published_on = date;
        this.sortOrder = sortOrder;
    }

    protected CryptoNewsArticle(Parcel in) {
        title = in.readString();
        body = in.readString();
        url = in.readString();
        imageurl = in.readString();
        source = in.readString();
        if (in.readByte() == 0) {
            published_on = null;
        } else {
            published_on = in.readLong();
        }
        if (in.readByte() == 0) {
            sortOrder = null;
        } else {
            sortOrder = in.readInt();
        }
    }

    public static final Creator<CryptoNewsArticle> CREATOR = new Creator<CryptoNewsArticle>() {
        @Override
        public CryptoNewsArticle createFromParcel(Parcel in) {
            return new CryptoNewsArticle(in);
        }

        @Override
        public CryptoNewsArticle[] newArray(int size) {
            return new CryptoNewsArticle[size];
        }
    };

    public String getImageurl() {
        return imageurl;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getSource() {
        return source;
    }

    public Long getPublished_on() {
        return published_on;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public static Comparator<CryptoNewsArticle> SortOrderComparator = new Comparator<CryptoNewsArticle>() {

        public int compare(CryptoNewsArticle cryptoCoin1, CryptoNewsArticle cryptoCoin2) {
            Integer sortOrder1 = cryptoCoin1.getSortOrder();
            Integer sortOrder2 = cryptoCoin2.getSortOrder();

            // Ascending order
            return sortOrder1.compareTo(sortOrder2);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeString(url);
        parcel.writeString(imageurl);
        parcel.writeString(source);
        if (published_on == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(published_on);
        }
        if (sortOrder == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(sortOrder);
        }
    }
}
