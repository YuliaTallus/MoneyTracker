package com.yuliatallus.moneytracker.rest;

import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.rest.model.CreateCategory;
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

    public CreateCategory createCategory(String title){
        return restClient.getCreateCategoryApi().createCategory(title, MoneyTrackerApplication.getAuthKey());
    }
}
