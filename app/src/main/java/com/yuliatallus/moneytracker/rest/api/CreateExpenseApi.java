package com.yuliatallus.moneytracker.rest.api;


import com.yuliatallus.moneytracker.rest.model.CreateExpenseModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface CreateExpenseApi {

    @GET("/transactions/add")
    CreateExpenseModel createExpense (@Query("sum") String sum,
                                           @Query("comment") String comment,
                                           @Query("category_id") String category_id,
                                           @Query("tr_date") String tr_date);
}
