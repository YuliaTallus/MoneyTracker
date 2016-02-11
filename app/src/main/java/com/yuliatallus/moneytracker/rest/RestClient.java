package com.yuliatallus.moneytracker.rest;

import com.yuliatallus.moneytracker.rest.api.CreateCategoryApi;
import com.yuliatallus.moneytracker.rest.api.LoginUserApi;
import com.yuliatallus.moneytracker.rest.api.RegisterUserApi;
import com.yuliatallus.moneytracker.rest.api.SyncCategoryApi;

import retrofit.RestAdapter;

public class RestClient {

    private static final String BASE_URL = "http://lmt.loftblog.tmweb.ru";
    private RegisterUserApi registerUserApi;
    private LoginUserApi loginUserApi;
    private CreateCategoryApi createCategoryApi;
    private SyncCategoryApi syncCategoryApi;

    public RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        registerUserApi = restAdapter.create(RegisterUserApi.class);
        loginUserApi = restAdapter.create(LoginUserApi.class);
        createCategoryApi = restAdapter.create(CreateCategoryApi.class);
        syncCategoryApi = restAdapter.create(SyncCategoryApi.class);
    }

    public RegisterUserApi getRegisterUserApi() {
        return registerUserApi;
    }

    public LoginUserApi getLoginUserApi() {
        return loginUserApi;
    }

    public CreateCategoryApi getCreateCategoryApi() {
        return createCategoryApi;
    }

    public SyncCategoryApi getSyncCategoryApi(){
        return syncCategoryApi;
    }
}
