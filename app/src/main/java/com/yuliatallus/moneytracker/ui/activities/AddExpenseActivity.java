package com.yuliatallus.moneytracker.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.activeandroid.query.Select;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.adapters.MySpinAdapter;
import com.yuliatallus.moneytracker.database.Categories;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_add_expense)
public class AddExpenseActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;

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
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
    }

    private List<Categories> getDataList()
    {
        return new Select()
                .from(Categories.class)
                .execute();
    }
}
