package com.yuliatallus.moneytracker.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.database.Categories;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.Data;
import com.yuliatallus.moneytracker.rest.model.SyncCategoryModel;
import com.yuliatallus.moneytracker.util.ConstantBox;

import java.util.ArrayList;
import java.util.List;


public class TrackerSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG  = TrackerSyncAdapter.class.getSimpleName();

    public TrackerSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.e(TAG, "Syncing!!!!!!!");

//        synchronizeCategories();
    }

    public static void syncImmediately(Context context){
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context){

        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        if (null == accountManager.getPassword(newAccount)){
            if (!accountManager.addAccountExplicitly(newAccount, "", null)){
                return null;
            }

            onAccountCreated(newAccount, context);
        }

        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context){
        final int SYNC_INTERVAL = 60*60*24;
        final int SYNC_FLEXTIME = SYNC_INTERVAL/3;

        TrackerSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
        ContentResolver.addPeriodicSync(newAccount, context.getString(R.string.content_authority), Bundle.EMPTY, SYNC_INTERVAL);

        syncImmediately(context);
    }

    public  static void configurePeriodicSync(Context context, int syncInterval, int flexTime){
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                SyncRequest request = new SyncRequest.Builder().
                        syncPeriodic(syncInterval, flexTime).
                        setSyncAdapter(account, authority).setExtras(new Bundle()).build();
                ContentResolver.requestSync(request);
        }
        else {
            ContentResolver.addPeriodicSync(account, authority, new Bundle(), syncInterval);
        }
    }

    public static void initializeSyncAdapter(Context context){
        getSyncAccount(context);
    }


    void synchronizeCategories(){
        RestService restService = new RestService();
        SyncCategoryModel syncCategoryModel = restService.syncCategory(getDataForSync());

        switch (syncCategoryModel.getStatus()){
            case ConstantBox.SUCCESS:
                Log.d(TAG, syncCategoryModel.getStatus() + "  "  + syncCategoryModel.getData());
                break;
            case ConstantBox.ERROR:
                Log.d(TAG, syncCategoryModel.getStatus());
                break;
        }
    }

    public String getDataForSync(){
        List<Categories> listCat =getDataList();
        List<String> listStr = new ArrayList<>();
        Data data = new Data();
        Gson gson = new Gson();

        for (Categories category: listCat){
            data.setTitle(category.toString());
            data.setId(category.hashCode());
            listStr.add(gson.toJson(data));
        }

        return listStr.toString();
    }

    private List<Categories> getDataList()
    {
        return new Select()
                .from(Categories.class)
                .execute();
    }
}
