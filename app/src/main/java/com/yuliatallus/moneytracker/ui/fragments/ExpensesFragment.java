package com.yuliatallus.moneytracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.activeandroid.query.Select;
import com.yuliatallus.moneytracker.adapters.ExpensesAdapter;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.UserRegistrationModel;
import com.yuliatallus.moneytracker.ui.activities.AddExpenseActivity_;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.database.Expenses;
import com.yuliatallus.moneytracker.ui.activities.MainActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.expenses_fragment)
public class ExpensesFragment extends Fragment {

    @ViewById(R.id.context_recyclerview)
    RecyclerView expensesRecyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;



    @Click(R.id.fab)
    void fabClicked() {
            startActivity(new Intent(getActivity(), AddExpenseActivity_.class));
    }

    @AfterViews
    void ready() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expensesRecyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle(getString(R.string.nav_drawer_expenses));

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private  void loadData(){
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Expenses>>() {
            @Override
            public Loader<List<Expenses>> onCreateLoader(int id, Bundle args) {
                final android.support.v4.content.AsyncTaskLoader<List<Expenses>> loader = new AsyncTaskLoader<List<Expenses>>(getActivity()) {
                    @Override
                    public List<Expenses> loadInBackground() {
                        return getDataList();
                    }
                };

                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Expenses>> loader, List<Expenses> data) {
                expensesRecyclerView.setAdapter(new ExpensesAdapter(data));
            }

            @Override
            public void onLoaderReset(Loader<List<Expenses>> loader) {

            }
        });
    }

    private List<Expenses> getDataList(){
        return  new Select()
                .from(Expenses.class)
                .execute();
    }



}
