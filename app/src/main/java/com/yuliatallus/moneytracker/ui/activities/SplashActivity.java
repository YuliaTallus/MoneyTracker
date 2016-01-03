package com.yuliatallus.moneytracker.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity{
    String token;

    @AfterViews
    void ready(){
        token = MoneyTrackerApplication.getAuthKey();
        if (token.equals("")){
            Intent intent = new Intent(this, LoginActivity_.class);
            intent.putExtra("key", "value");
            this.startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, MainActivity_.class);
            intent.putExtra("key", "value");
            this.startActivity(intent);
        }
    }
}
