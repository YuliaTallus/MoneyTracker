package com.yuliatallus.moneytracker.ui.activities;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.rest.RestClient;
import com.yuliatallus.moneytracker.rest.model.GoogleTokenStatusModel;
import com.yuliatallus.moneytracker.util.ConstantBox;
import com.yuliatallus.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity{

    private static final String TAG = LoginActivity.class.getSimpleName();

    private String gToken;
    private RestClient restClient;

    @ViewById(R.id.activity_splash)
    LinearLayout linLayout;

    @AfterViews
    void ready(){
        if (NetworkStatusChecker.isNetworkAvailable(this)){
            restClient = new RestClient();
            gToken = MoneyTrackerApplication.getGoogleKey(this);
            if (gToken.equalsIgnoreCase("2")){
                launchActivity();
            }
            else {
                checkTokenValid();
            }
        }
        else {
            Snackbar.make(linLayout, R.string.no_connection_text, Snackbar.LENGTH_SHORT).show();
        }

    }

    @Background(delay = 1500)
    void launchActivity(){
        if (MoneyTrackerApplication.getAuthKey().equals("")){
            startActivity(new Intent(this, LoginActivity_.class));
        }
        else{
            startActivity(new Intent(this, MainActivity_.class));
        }
    }

    @Background
    void checkTokenValid(){
        restClient.getCheckGoogleTokenApi().tokenStatus(gToken, new Callback<GoogleTokenStatusModel>() {

            @Override
            public void success(GoogleTokenStatusModel googleTokenStatusModel, Response response) {
                Log.e(TAG, googleTokenStatusModel.getStatus());
                if (googleTokenStatusModel.getStatus().equalsIgnoreCase("error")){
                    doubleTokenExc();
                }
                else{
                    Intent regIntent = new Intent(SplashActivity.this, MainActivity_.class);
                    startActivity(regIntent);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void doubleTokenExc() {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK){
            getToken(data);
        }
    }

    @Background
    public void getToken(Intent data){
        final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String token = null;
        try {
            token = GoogleAuthUtil.getToken(SplashActivity.this, accountName, ConstantBox.SCOPES);
        }catch(UserRecoverableAuthException userAuthEx){
            startActivityForResult(userAuthEx.getIntent(), 10);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (GoogleAuthException fatalAuthEx) {
            fatalAuthEx.printStackTrace();
            Log.e(TAG, "Fatal Exception " + fatalAuthEx.getLocalizedMessage());
        }

        MoneyTrackerApplication.setGoogleToken(this, token);

        Log.e(ConstantBox.LOG_TAG_TOKEN, " GOOGLE_TOKEN + " + MoneyTrackerApplication.getGoogleKey(this));

        if (!MoneyTrackerApplication.getGoogleKey(this).equalsIgnoreCase("2")){
            Intent regIntent = new Intent(SplashActivity.this, MainActivity_.class);
            startActivity(regIntent);
            finish();
        }
    }
}
