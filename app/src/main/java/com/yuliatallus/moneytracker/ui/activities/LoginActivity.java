package com.yuliatallus.moneytracker.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.UserLoginModel;
import com.yuliatallus.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity{

    private static final String TAG = LoginActivity.class.getSimpleName();


    @ViewById(R.id.login_activity)
    LinearLayout linLayout;

    @ViewById(R.id.edit_login)
    EditText editLogin;

    @ViewById(R.id.edit_password)
    EditText editPassword;

    @ViewById(R.id.login_button)
    Button loginButton;

    @ViewById(R.id.no_registration_text_view)
    TextView noRegText;

//    @AfterViews
//    void ready(){
//
//
//    }

    @Click(R.id.login_button)
    void loginButtonClicked(){
        if (NetworkStatusChecker.isNetworkAvailable(this)){
            String login = editLogin.getText().toString();
            String password = editPassword.getText().toString();
            login(login,password);
        }
        else{
            Snackbar.make(linLayout, R.string.no_connection_text, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.no_registration_text_view)
    void noRegTextClicked(){
        Intent intent = new Intent(this, RegisterActivity_.class);
        intent.putExtra("key", "value");
        this.startActivity(intent);
    }

    @Background
    void login(String login,String password){
        RestService restService = new RestService();
        UserLoginModel userLoginModel = restService.login(login, password);

        switch (userLoginModel.getStatus()){

            case "success":
                MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());
                Log.d(TAG, "Status: " + userLoginModel.getStatus() + ", token: " + MoneyTrackerApplication.getAuthKey());
                Intent intent = new Intent(this, MainActivity_.class);
                intent.putExtra("key", "value");
                this.startActivity(intent);
                break;

            case "Wrong password":
                Snackbar.make(linLayout, R.string.wrong_password_text, Snackbar.LENGTH_SHORT).show();
                break;

            case "Wrong login":
                Snackbar.make(linLayout, R.string.wrong_login_text, Snackbar.LENGTH_SHORT).show();
                break;

            case "Error":
                Snackbar.make(linLayout, "Случилась какая-то неведомая хрень. Мы пока не знаем, что с ней делать",
                        Snackbar.LENGTH_SHORT).show();
                break;
        }

//        CreateCategory createCategory = restService.createCategory("Test1");
//        Log.d(TAG, "Status: " + createCategory.getStatus() +
//                ", Title: " + createCategory.getData().getTitle() +
//                ", Id: " + createCategory.getData().getId());
    }
}
