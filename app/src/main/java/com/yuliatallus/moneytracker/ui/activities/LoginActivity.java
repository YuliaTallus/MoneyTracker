package com.yuliatallus.moneytracker.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.CreateCategory;
import com.yuliatallus.moneytracker.rest.model.UserLoginModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity{

    private static final String TAG = LoginActivity.class.getSimpleName();

    @AfterViews
    void ready(){
        login();
    }

    @Background
    void login(){
        RestService restService = new RestService();
        UserLoginModel userLoginModel = restService.login("user2", "test1");
        MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());
        Log.d(TAG, "Status: " + userLoginModel.getStatus() + ", token: " + MoneyTrackerApplication.getAuthKey());

        CreateCategory createCategory = restService.createCategory("Test1");
        Log.d(TAG, "Status: " + createCategory.getStatus() +
                ", Title: " + createCategory.getData().getTitle() +
                ", Id: " + createCategory.getData().getId());
    }
}
