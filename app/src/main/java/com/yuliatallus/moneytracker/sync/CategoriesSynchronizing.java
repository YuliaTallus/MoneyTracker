package com.yuliatallus.moneytracker.sync;


import android.util.Log;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.yuliatallus.moneytracker.database.Categories;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.Data;
import com.yuliatallus.moneytracker.rest.model.SyncCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesSynchronizing {
    private static final String TAG  = CategoriesSynchronizing.class.getSimpleName();

    public static void synchronizeCategories(){

        RestService restService = new RestService();
        SyncCategoryModel syncCategoryModel = restService.syncCategory(getDataForSync());
        Log.d(TAG, syncCategoryModel.getStatus());

    }

    public static String getDataForSync(){
        List<Categories> listCat =getDataList();
        List<String> listStr = new ArrayList<>();
        Data data = new Data();
        Gson gson = new Gson();

        for (Categories category: listCat){
            data.setTitle(category.name);
            data.setId(0);
            listStr.add(gson.toJson(data));
        }

        Log.d("result string", listStr.toString());
        return listStr.toString();
    }

    private static List<Categories> getDataList()
    {
        return new Select()
                .from(Categories.class)
                .execute();
    }
}
