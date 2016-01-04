package com.yuliatallus.moneytracker.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity{
    String token;

    @ViewById(R.id.activity_splash)
    LinearLayout linLayout;

    @AfterViews
    void ready(){
        if (NetworkStatusChecker.isNetworkAvailable(this)){
            launchActivity();
        }
        else {
            Snackbar.make(linLayout, R.string.no_connection_text, Snackbar.LENGTH_SHORT).show();
        }

    }

    @Background(delay = 1500)
    void launchActivity(){
        token = MoneyTrackerApplication.getAuthKey();
        if (token.equals("")){
            startActivity(new Intent(this, LoginActivity_.class));
        }
        else{
            startActivity(new Intent(this, MainActivity_.class));
        }

    }
}
