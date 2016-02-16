package com.yuliatallus.moneytracker.rest.api;


import com.yuliatallus.moneytracker.rest.model.SyncCategoryModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface SyncCategoryApi {

    @GET("/categories/synch")
    SyncCategoryModel syncCategory(
            @Query("google_token") String gToken,
            @Query("data") String data,
            @Query("auth_token") String token);
}
