package com.yuliatallus.moneytracker.ui.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.activeandroid.query.Select;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.adapters.MySpinAdapter;
import com.yuliatallus.moneytracker.database.Categories;
import com.yuliatallus.moneytracker.database.Expenses;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.CreateExpenseModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.activity_add_expense)
public class AddExpenseActivity extends AppCompatActivity {

    private static final String TAG = AddExpenseActivity.class.getSimpleName();

    Categories categoryToExpense;

    @ViewById(R.id.act_add_exp_layout)
    LinearLayout linLayout;

    @ViewById(R.id.spinner)
    Spinner spinner;

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;

    @ViewById(R.id.sum_edit_text)
    EditText sumEditText;

    @ViewById(R.id.note_edit_text)
    EditText noteEditText;

    @OptionsItem(R.id.home)
    void back(){
        onBackPressed();
        finish();
    }



    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.add_expense);
        SpinnerAdapter adapter = new MySpinAdapter(this, getDataList());
        spinner.setAdapter(adapter);
    }

    @Click(R.id.fab)
    public void buttonClicked(){
        String sumToExpense = sumEditText.getText().toString();
        String noteToExpense = noteEditText.getText().toString();
        String dateToExpense;
        if (sumToExpense.equals("")||
                noteToExpense.equals("")){
            Snackbar.make(linLayout, R.string.fill_fields, Snackbar.LENGTH_SHORT).show();
        }else{
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    categoryToExpense = (Categories)spinner.getSelectedItem();
//                    categoryToExpense = new  Select().from(Categories.class)
//                            .where("name = ?", spinner.getItemAtPosition(position).toString())
//                            .orderBy("RANDOM()")
//                            .executeSingle();
//                    Log.e(TAG, "CATEGORY TESTING!!!" + spinner.getItemAtPosition(position).toString());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Date date = new Date();
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
            dateToExpense = DATE_FORMAT.format(date);

            Log.d("CATEGORY",categoryToExpense.name + " " + categoryToExpense.getId());


            Expenses newExpense = new Expenses(sumToExpense, noteToExpense, dateToExpense, categoryToExpense);
            newExpense.save();

//            RestService restService = new RestService();
//            CreateExpenseModel createExpenseModel = restService.createExpense(newExpense.price,
//                    newExpense.name,
//                    newExpense.category.getId().toString(),
//                    newExpense.date );
//
//            Log.d(TAG, createExpenseModel.getStatus()+ "!!!");

            back();
        }


    }

    private List<Categories> getDataList()
    {
        return new Select()
                .from(Categories.class)
                .execute();
    }
}
