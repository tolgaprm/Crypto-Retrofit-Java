package com.tolgapirim.cryptoretrofit.service;

import com.tolgapirim.cryptoretrofit.model.CryptoDetailModel;
import com.tolgapirim.cryptoretrofit.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CryptoAPI {


    //https://api.nomics.com/v1/ticker?key=d36e6480ea93d8a7dfd580e5b5ffdc1d54002a7d

    @GET("prices")
    Observable<List<CryptoModel>> getData(
            @Query("key") String key
    );


    // https://api.nomics.com/v1/currencies?key=your-key-here&ids=BTC,ETH,XRP&attributes=id,name,logo_url
    @GET("currencies")
    Observable<List<CryptoDetailModel>> getDetail(
            @Query("key") String key,
            @Query("ids") String id,
            @Query("attributes") String attributes

            );





}
