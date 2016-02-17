package com.yuliatallus.moneytracker.rest.api;


import com.yuliatallus.moneytracker.rest.model.GoogleJsonModel;
import com.yuliatallus.moneytracker.rest.model.GoogleTokenStatusModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface CheckGoogleTokenApi {
    @GET("/gcheck")
    void tokenStatus(@Query("google_token") String gToken, Callback<GoogleTokenStatusModel> modelCallback);

    @GET("/gjson")
    GoogleJsonModel googleJson(@Query("google_token") String gToken);
}
