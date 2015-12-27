package com.yuliatallus.moneytracker.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.yuliatallus.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity{

    @ViewById(R.id.edit_login)
    EditText editLogin;

    @ViewById(R.id.edit_password)
    EditText editPassword;

    @ViewById(R.id.login_button)
    Button loginButton;

    @Click(R.id.login_button)
    public void loginButtonCicked(){
        Intent intent = new Intent(this, MainActivity_.class);
        intent.putExtra("key", "value");
        this.startActivity(intent);
    }

    @AfterViews
    void ready(){
        if (loginButton.isPressed()){
            loginButtonCicked();
        }

    }
}
