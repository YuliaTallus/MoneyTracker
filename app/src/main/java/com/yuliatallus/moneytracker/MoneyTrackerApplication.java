package com.yuliatallus.moneytracker;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class MoneyTrackerApplication extends Application{

    private static SharedPreferences preferences;
    private static final String TOKEN_KEY = "token_key";
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static void setAuthToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public static String getAuthKey(){
        return preferences.getString(TOKEN_KEY,"");
    }

}
