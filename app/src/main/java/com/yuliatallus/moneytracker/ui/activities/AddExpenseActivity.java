package com.yuliatallus.moneytracker.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

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

        final EditText sumEditText = (EditText)findViewById(R.id.sum_edit_text);
        final EditText noteEditText = (EditText)findViewById(R.id.note_edit_text);
        final EditText dateEditText = (EditText)findViewById(R.id.date_edit_text);

        if (sumEditText.getText().toString().equals("")){
            Toast.makeText(this, "The sum is empty. Fill it, Silly!", Toast.LENGTH_SHORT).show();
        }

        if (noteEditText.getText().toString().equals("")){
            Toast.makeText(this, "The note is empty. Fill it, Silly!", Toast.LENGTH_SHORT).show();
        }

        if (dateEditText.getText().toString().equals("")){
            Toast.makeText(this, "The date is empty. Fill it, Silly!", Toast.LENGTH_SHORT).show();
        }

        SpinnerAdapter adapter = new MySpinAdapter(this, getDataList());
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<Categories> getDataList()
    {
        return new Select()
                .from(Categories.class)
                .execute();
    }
}
