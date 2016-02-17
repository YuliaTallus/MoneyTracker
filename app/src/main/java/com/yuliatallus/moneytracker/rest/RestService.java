package com.yuliatallus.moneytracker.rest;

import android.content.Context;

import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.rest.model.CreateCategoryModel;
import com.yuliatallus.moneytracker.rest.model.CreateExpenseModel;
import com.yuliatallus.moneytracker.rest.model.GoogleJsonModel;
import com.yuliatallus.moneytracker.rest.model.SyncCategoryModel;
import com.yuliatallus.moneytracker.rest.model.SyncExpensesModel;
import com.yuliatallus.moneytracker.rest.model.UserLoginModel;
import com.yuliatallus.moneytracker.rest.model.UserRegistrationModel;

public class RestService {

    private static final  String REGISTER_FLAG = "1";
    private RestClient restClient;

    public RestService(){
        restClient = new RestClient();
    }

    public UserRegistrationModel register(String login, String password){
        return restClient.getRegisterUserApi().registerUser(login, password, REGISTER_FLAG);
    }

    public UserLoginModel login(String login, String password){
        return restClient.getLoginUserApi().loginUser(login, password);
    }

    public CreateCategoryModel createCat(Context context, String title){
        return restClient.getCreateCategoryApi().createCategory(MoneyTrackerApplication.getGoogleKey(context),title, MoneyTrackerApplication.getAuthKey());
    }

    public CreateExpenseModel createExpense(Context context,String sum, String comment, String categoryId, String date){
        return restClient.getCreateExpenseApi().createExpense(MoneyTrackerApplication.getGoogleKey(context),sum, comment, categoryId, date);
    }

    public SyncCategoryModel syncCategory(Context context, String data){
        return restClient.getSyncCategoryApi().syncCategory(MoneyTrackerApplication.getGoogleKey(context),data, MoneyTrackerApplication.getAuthKey());
    }

    public SyncExpensesModel syncExpense(Context context, String data){
        return restClient.getSyncExpensesApi().syncExpense(MoneyTrackerApplication.getGoogleKey(context),data, MoneyTrackerApplication.getAuthKey());
    }

    public GoogleJsonModel getJsonModel(Context context){
        return restClient.getCheckGoogleTokenApi().googleJson(MoneyTrackerApplication.getGoogleKey(context));
    }
}
