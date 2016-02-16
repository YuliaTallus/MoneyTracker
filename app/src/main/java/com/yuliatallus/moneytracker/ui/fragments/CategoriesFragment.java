package com.yuliatallus.moneytracker.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;


import com.activeandroid.query.Select;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.adapters.CategoriesAdapter;
import com.yuliatallus.moneytracker.database.Categories;
import com.yuliatallus.moneytracker.util.ConstantBox;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

@EFragment(R.layout.categories_fragment)
@OptionsMenu(R.menu.search_menu)
public class CategoriesFragment extends Fragment {

    private static final String TAG =CategoriesFragment.class.getSimpleName();

    @ViewById(R.id.context_recyclerview)
    RecyclerView categoriesRecyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;

    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;



    @Click(R.id.fab)
    void fabClicked() {
        if (getView() != null) {
            Snackbar.make(getView(), R.string.nice_text, Snackbar.LENGTH_SHORT).show();
        }
    }

    @AfterViews
    void ready() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle(getString(R.string.nav_drawer_categories));
        loadData("");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "Full Query: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "Current text: " + newText);
                BackgroundExecutor.cancelAll(ConstantBox.FILTER_ID, true);
                delayedQuery(newText);
                return false;
            }
        });
    }

    @Background(delay = 700, id = ConstantBox.FILTER_ID)
    public  void delayedQuery(String filter){
        loadData(filter);
    }



    private void loadData(final String filter)
    {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Categories>>() {
            @Override
            public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Categories>> loader = new AsyncTaskLoader<List<Categories>>(getActivity()) {
                    @Override
                    public List<Categories> loadInBackground() {
                        return Categories.categories(filter);
                    }
                };

                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Categories>> loader, List<Categories> data) {

                categoriesRecyclerView.setAdapter(new CategoriesAdapter(data));
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {

            }
        });
    }



}