package com.yuliatallus.moneytracker.sync;


import android.content.Context;
import android.util.Log;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.yuliatallus.moneytracker.database.Categories;
import com.yuliatallus.moneytracker.database.Expenses;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.ExpensesModel;
import com.yuliatallus.moneytracker.rest.model.SyncExpensesModel;

import java.util.ArrayList;
import java.util.List;

public class ExpensesSynchronizing {

    private static final String TAG  = ExpensesSynchronizing.class.getSimpleName();

    public static void synchronizeExpenses(Context context){

//        Categories testCat = new Select().from(Categories.class).executeSingle();
//        Log.d("TEST CATEGORY!!!", testCat.toString()+ " " + testCat.getId());
//        Expenses newExpense = new Expenses("200", "test", "2015-02-02",testCat );
//        newExpense.save();

        RestService restService = new RestService();
        SyncExpensesModel syncExpensesModel = restService.syncExpense(context,getDataForSync());
        Log.d(TAG, syncExpensesModel.getStatus());

    }

    public static String getDataForSync(){
        List<Expenses> listExp =getDataList();
        List<String> listStr = new ArrayList<>();
        ExpensesModel expenseModel = new ExpensesModel();
        Gson gson = new Gson();

        for (Expenses expense: listExp){
            expenseModel.setId(0);
            expenseModel.setCategoryId(Integer.parseInt(expense.category.getId().toString()));
            expenseModel.setComment(expense.name);
            expenseModel.setSum(Double.parseDouble(expense.price));
            expenseModel.setTrDate(expense.date);
            listStr.add(gson.toJson(expenseModel));
        }

        Log.d("result string", listStr.toString());
        return listStr.toString();
    }

    private static List<Expenses> getDataList()
    {
        return new Select()
                .from(Expenses.class)
                .execute();
    }
}
