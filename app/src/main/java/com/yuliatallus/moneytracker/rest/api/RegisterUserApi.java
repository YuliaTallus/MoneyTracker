package com.yuliatallus.moneytracker.rest.api;

import com.yuliatallus.moneytracker.rest.model.UserRegistrationModel;

import retrofit.http.GET;
import retrofit.http.Query;


public interface RegisterUserApi {
    @GET("/auth")
    UserRegistrationModel registerUser(@Query("login") String login,
                                       @Query("password") String password,
                                       @Query("register") String register);
}
