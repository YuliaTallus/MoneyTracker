package com.yuliatallus.moneytracker.rest.api;


import com.yuliatallus.moneytracker.rest.model.CreateCategoryModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface CreateCategoryApi {

    @GET("/categories/add")
    CreateCategoryModel createCategory(
            @Query("google_token") String gToken,
            @Query("title") String title,
            @Query("auth_token") String token);
}
