package com.yuliatallus.moneytracker.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.UserRegistrationModel;
import com.yuliatallus.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity{

    public static final String TAG = "LoginActivity";

    @ViewById(R.id.login_activity)
    LinearLayout linLayout;

    @ViewById(R.id.edit_login)
    EditText editLogin;

    @ViewById(R.id.edit_password)
    EditText editPassword;

    @ViewById(R.id.login_button)
    Button loginButton;

    @Click(R.id.login_button)
    public void loginButtonCicked(){
        if (new NetworkStatusChecker().isNetworkAvailable(this)){
            String login = editLogin.getText().toString();
            String password = editPassword.getText().toString();
            if (login.length()<5||password.length()<5){
                Snackbar.make(linLayout, "Поля должны содержать не менее 5 символов", Snackbar.LENGTH_SHORT).show();
            }
            else{
                registerUser(login, password);
            }
        }
        else{
            Snackbar.make(linLayout, "Нет подключения", Snackbar.LENGTH_SHORT).show();
        }


    }

    @AfterViews
    void ready(){

        if (loginButton.isPressed()){
            loginButtonCicked();
        }
    }

    @Background
    public void registerUser(String log, String pas){
        RestService restService = new RestService();
        UserRegistrationModel userRegistrationModel = restService.register(log, pas);
        Log.i(TAG, "status: " + userRegistrationModel.getStatus() + " " + ", id: " + userRegistrationModel.getId());
        if (!userRegistrationModel.getStatus().equalsIgnoreCase("success")){
            Snackbar.make(linLayout, "Пользователь с таким логином уже существует", Snackbar.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, MainActivity_.class);
            intent.putExtra("key", "value");
            this.startActivity(intent);
        }
    }
}
