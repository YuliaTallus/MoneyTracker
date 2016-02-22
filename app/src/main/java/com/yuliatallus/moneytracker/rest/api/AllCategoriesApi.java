package com.yuliatallus.moneytracker.rest.api;

import com.yuliatallus.moneytracker.rest.model.GetAllCategoriesModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface AllCategoriesApi {

    @GET("/categories")
    GetAllCategoriesModel getAllCategories (
            @Query("google_token") String gToken,
            @Query("token")String token);
}
