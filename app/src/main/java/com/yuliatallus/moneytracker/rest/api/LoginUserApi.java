package com.yuliatallus.moneytracker.rest.api;

import com.yuliatallus.moneytracker.rest.model.UserLoginModel;

import retrofit.http.GET;
import retrofit.http.Query;


public interface LoginUserApi {

    @GET("/auth")
    UserLoginModel loginUser(@Query("login") String login,
                             @Query("password") String password);
}
