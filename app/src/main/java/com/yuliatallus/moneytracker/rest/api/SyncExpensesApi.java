package com.yuliatallus.moneytracker.rest.api;


import com.yuliatallus.moneytracker.rest.model.SyncExpensesModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface SyncExpensesApi {


    @GET("/transactions/synch")
    SyncExpensesModel syncExpense(
            @Query("google_token") String gToken,
            @Query("data") String data,
            @Query("auth_token") String token);
}
