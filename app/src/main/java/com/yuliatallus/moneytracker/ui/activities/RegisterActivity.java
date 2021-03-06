package com.yuliatallus.moneytracker.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.UserLoginModel;
import com.yuliatallus.moneytracker.rest.model.UserRegistrationModel;
import com.yuliatallus.moneytracker.util.ConstantBox;
import com.yuliatallus.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.register_activity)
public class RegisterActivity extends AppCompatActivity{

    public static final String TAG = "RegisterActivity";

    @ViewById(R.id.register_activity)
    LinearLayout linLayout;

    @ViewById(R.id.edit_login)
    EditText editLogin;

    @ViewById(R.id.edit_password)
    EditText editPassword;

    @ViewById(R.id.register_button)
    Button registerButton;

    @Click(R.id.register_button)
    public void registerButtonCicked(){
        if (NetworkStatusChecker.isNetworkAvailable(this)){
            String login = editLogin.getText().toString();
            String password = editPassword.getText().toString();
            if (login.length()<5||password.length()<5){
                Snackbar.make(linLayout, R.string.five_symbol_text, Snackbar.LENGTH_SHORT).show();
            }
            else{
                registerUser(login, password);
            }
        }
        else{
            Snackbar.make(linLayout, R.string.no_connection_text, Snackbar.LENGTH_SHORT).show();
        }


    }

    @Background
    public void registerUser(String log, String pas){
        RestService restService = new RestService();
        UserRegistrationModel userRegistrationModel = restService.register(log, pas);

        switch (userRegistrationModel.getStatus()){

            case ConstantBox.SUCCESS:
                UserLoginModel userLoginModel = restService.login(log, pas);
                MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());
                Log.d(TAG, "Status: " + userLoginModel.getStatus() + ", token: " + MoneyTrackerApplication.getAuthKey());
                startActivity(new Intent(this, MainActivity_.class));
                break;
            case ConstantBox.LOGIN_IS_BUSY:
                Snackbar.make(linLayout, R.string.login_is_busy, Snackbar.LENGTH_SHORT).show();
                break;
        }
    }
}
