package com.yuliatallus.moneytracker.rest;

import com.yuliatallus.moneytracker.rest.api.RegisterUserApi;

import retrofit.RestAdapter;

public class RestClient {

    private static final String BASE_URL = "http://lmt.loftblog.tmweb.ru";
    private RegisterUserApi registerUserApi;

    public RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        registerUserApi = restAdapter.create(RegisterUserApi.class);
    }

    public RegisterUserApi getRegisterUserApi() {
        return registerUserApi;
    }
}
