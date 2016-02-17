package com.yuliatallus.moneytracker.ui.activities;

import android.accounts.AccountManager;
import android.content.Intent;
import android.media.session.MediaSession;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.rest.RestClient;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.GetAllCategoriesModel;
import com.yuliatallus.moneytracker.rest.model.GoogleJsonModel;
import com.yuliatallus.moneytracker.rest.model.UserLoginModel;
import com.yuliatallus.moneytracker.util.ConstantBox;
import com.yuliatallus.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

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


    @Click(R.id.login_button)
    void loginButtonClicked(){
        if (NetworkStatusChecker.isNetworkAvailable(this)){
            String login = editLogin.getText().toString();
            String password = editPassword.getText().toString();
            if (login.length()<5||password.length()<5){
                Snackbar.make(linLayout, R.string.five_symbol_text, Snackbar.LENGTH_SHORT).show();
            }
            else {
                login(login,password);
            }
        }
        else{
            Snackbar.make(linLayout, R.string.no_connection_text, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.no_registration_text_view)
    void noRegTextClicked(){
        startActivity(new Intent(this, RegisterActivity_.class));
    }

    @Click(R.id.sign_in_button)
    void btnGplusLogin(){
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, 10);

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
            token = GoogleAuthUtil.getToken(LoginActivity.this, accountName, ConstantBox.SCOPES);
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

            Intent regIntent = new Intent(LoginActivity.this, MainActivity_.class);
            startActivity(regIntent);
            finish();
        }
    }

    @Background
    void login(String login,String password){
        RestService restService = new RestService();
        UserLoginModel userLoginModel = restService.login(login, password);

        switch (userLoginModel.getStatus()){

            case ConstantBox.SUCCESS:
                MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());
                Log.d(TAG, "Status: " + userLoginModel.getStatus() + ", token: " + MoneyTrackerApplication.getAuthKey());
                startActivity(new Intent(this, MainActivity_.class));
                break;

            case ConstantBox.WRONG_PASSWORD:
                Snackbar.make(linLayout, R.string.wrong_password_text, Snackbar.LENGTH_SHORT).show();
                break;

            case ConstantBox.WRONG_LOGIN:
                Snackbar.make(linLayout, R.string.wrong_login_text, Snackbar.LENGTH_SHORT).show();
                break;

            case ConstantBox.ERROR:
                Snackbar.make(linLayout, R.string.unknown_error, Snackbar.LENGTH_SHORT).show();
                break;
        }


    }


}
