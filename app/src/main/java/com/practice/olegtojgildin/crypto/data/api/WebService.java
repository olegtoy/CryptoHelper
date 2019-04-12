package com.practice.olegtojgildin.crypto.data.api;

import com.practice.olegtojgildin.crypto.data.models.HistoryList;
import com.practice.olegtojgildin.crypto.data.models.convert.SinglePrice;
import com.practice.olegtojgildin.crypto.data.models.news.NewsList;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinList;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by olegtojgildin on 16/03/2019.
 */

public interface WebService {

   @GET("v2/news/?")
   Observable<NewsList> getNews(@Query("categories") String category,@Query("lang") String lang);

   @GET("v2/news/?")
   Observable<NewsList> getLatestNews(@Query("lang") String lang);

   @GET("top/totalvolfull")
   Observable<CryptoCoinList> getTopVolume(@Query("limit") String limit, @Query("tsym") String tsym);

   @GET("pricemultifull?")
   Observable<CryptoCoinFullInfo> getCoinsInfo(@Query("fsyms") String fsyms, @Query("tsyms") String tsyms);

   @GET("price?")
   Single<SinglePrice> getSinglePrice(@Query("fsym") String fsym, @Query("tsyms") String tsyms);


   @GET("histoday")
   Observable<HistoryList> getHistoryDay(@Query("fsym") String fsyms, @Query("tsym") String tsyms, @Query("limit") String limit);

}