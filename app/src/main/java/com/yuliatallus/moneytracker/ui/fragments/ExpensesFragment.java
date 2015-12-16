package com.yuliatallus.moneytracker.ui.fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.activeandroid.query.Select;
import com.yuliatallus.moneytracker.ui.activities.AddExpenseActivity_;
import com.yuliatallus.moneytracker.Expense;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.adapters.ExpensesAdapter;
import com.yuliatallus.moneytracker.database.Expenses;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EFragment(R.layout.expenses_fragment)
public class ExpensesFragment extends Fragment {

    @ViewById(R.id.context_recyclerview)
    RecyclerView expensesRecyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;



    @Click(R.id.fab)
    void fabClicked() {
        if (getView() != null && floatingActionButton.isPressed()) {
            Intent intent = new Intent(getActivity(), AddExpenseActivity_.class);
            intent.putExtra("key", "value");
            getActivity().startActivity(intent);
        }
    }

    @AfterViews
    void ready() {
        List<Expense> adapterData = getDataList();
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(adapterData);
        expensesRecyclerView.setAdapter(expensesAdapter);

        Expenses expenses = new Expenses("123","Cinema", "Fun", "12.13.15");
        expenses.save();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expensesRecyclerView.setLayoutManager(linearLayoutManager);
        fabClicked();
        getActivity().setTitle(getString(R.string.nav_drawer_expenses));
    }

    private List<Expense> getDataList(){
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("Phone","900", new Date()));
        data.add(new Expense("Food","5000", new Date()));
        data.add(new Expense("Flat", "3000", new Date()));
        data.add(new Expense("Clothes", "9900", new Date()));
        data.add(new Expense("Entertainment", "500", new Date()));
        data.add(new Expense("Education", "10000", new Date()));
        data.add(new Expense("PC", "800", new Date()));
        return  data;
    }

    public Expenses getExpense(){
        return new Select()
                .from(Expenses.class)
                .executeSingle();
    }
}
