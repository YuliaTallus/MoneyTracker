package com.yuliatallus.moneytracker.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.adapters.MySpinAdapter;
import com.yuliatallus.moneytracker.database.Categories;
import com.yuliatallus.moneytracker.database.Expenses;

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

    Categories categoryToExpense;

    @ViewById(R.id.spinner)
    Spinner spinner;

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.button)
    Button button;

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
        if (button.isPressed()){
            buttonClicked();
        }

    }

    @Click(R.id.button)
    public void buttonClicked(){
        String sumToExpense = sumEditText.getText().toString();
        String noteToExpense = noteEditText.getText().toString();
        String dateToExpense;
        if (sumToExpense.equals("")||
                noteToExpense.equals("")){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }else{
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    categoryToExpense = (Categories)spinner.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Date date = new Date();
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
            dateToExpense = DATE_FORMAT.format(date);


            Expenses newExpense = new Expenses(sumToExpense, noteToExpense, dateToExpense, categoryToExpense);
            newExpense.save();
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
