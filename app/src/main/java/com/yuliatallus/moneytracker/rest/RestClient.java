package com.yuliatallus.moneytracker.rest;

import com.yuliatallus.moneytracker.rest.api.CheckGoogleTokenApi;
import com.yuliatallus.moneytracker.rest.api.CreateCategoryApi;
import com.yuliatallus.moneytracker.rest.api.CreateExpenseApi;
import com.yuliatallus.moneytracker.rest.api.LoginUserApi;
import com.yuliatallus.moneytracker.rest.api.RegisterUserApi;
import com.yuliatallus.moneytracker.rest.api.SyncCategoryApi;
import com.yuliatallus.moneytracker.rest.api.SyncExpensesApi;

import retrofit.RestAdapter;

public class RestClient {

    private static final String BASE_URL = "http://lmt.loftblog.tmweb.ru";
    private RegisterUserApi registerUserApi;
    private LoginUserApi loginUserApi;
    private CreateCategoryApi createCategoryApi;
    private CreateExpenseApi createExpenseApi;
    private SyncCategoryApi syncCategoryApi;
    private SyncExpensesApi syncExpensesApi;
    private CheckGoogleTokenApi checkGoogleTokenApi;


    public RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        registerUserApi = restAdapter.create(RegisterUserApi.class);
        loginUserApi = restAdapter.create(LoginUserApi.class);
        createCategoryApi = restAdapter.create(CreateCategoryApi.class);
        createExpenseApi = restAdapter.create(CreateExpenseApi.class);
        syncCategoryApi = restAdapter.create(SyncCategoryApi.class);
        syncExpensesApi = restAdapter.create(SyncExpensesApi.class);
        checkGoogleTokenApi = restAdapter.create(CheckGoogleTokenApi.class);
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

    public CreateExpenseApi getCreateExpenseApi() {
        return createExpenseApi;
    }

    public SyncCategoryApi getSyncCategoryApi(){
        return syncCategoryApi;
    }

    public SyncExpensesApi getSyncExpensesApi(){
        return syncExpensesApi;
    }

    public CheckGoogleTokenApi getCheckGoogleTokenApi() {
        return checkGoogleTokenApi;
    }
}
