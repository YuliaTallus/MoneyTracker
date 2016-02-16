package com.yuliatallus.moneytracker.rest;

import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.rest.model.CreateCategoryModel;
import com.yuliatallus.moneytracker.rest.model.CreateExpenseModel;
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

    public CreateCategoryModel createCat(String title){
        return restClient.getCreateCategoryApi().createCategory(title, MoneyTrackerApplication.getAuthKey());
    }

    public CreateExpenseModel createExpense(String sum, String comment, String categoryId, String date){
        return restClient.getCreateExpenseApi().createExpense(sum, comment, categoryId, date);
    }

    public SyncCategoryModel syncCategory(String data){
        return restClient.getSyncCategoryApi().syncCategory(data, MoneyTrackerApplication.getAuthKey());
    }

    public SyncExpensesModel syncExpense(String data){
        return restClient.getSyncExpensesApi().syncExpense(data, MoneyTrackerApplication.getAuthKey());
    }
}
