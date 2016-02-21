package com.yuliatallus.moneytracker.rest.api;

import com.yuliatallus.moneytracker.rest.model.AllCategoriesModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface AllCategoriesApi {

    @GET("/categories")
    AllCategoriesModel getAllCategories (
            @Query("google_token") String gToken,
            @Query("token")String token);
}
