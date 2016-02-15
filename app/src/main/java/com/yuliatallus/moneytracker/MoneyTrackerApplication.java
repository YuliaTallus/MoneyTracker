package com.yuliatallus.moneytracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class MoneyTrackerApplication extends Application{

    private static SharedPreferences preferences;
    private static final String TOKEN_KEY = "token_key";
    private static final String GOOGLE_TOKEN_KEY = "google_token_key";


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

    public static void setGoogleToken(Context context, String token){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(GOOGLE_TOKEN_KEY, token);
        editor.commit();
    }

    public static String getGoogleKey(Context context){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(GOOGLE_TOKEN_KEY, "2");
    }

}
