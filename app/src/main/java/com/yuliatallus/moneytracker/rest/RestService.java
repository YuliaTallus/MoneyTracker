package com.yuliatallus.moneytracker.rest;

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
}
