package com.yuliatallus.moneytracker.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.adapters.CategoriesAdapter;
import com.yuliatallus.moneytracker.database.Categories;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.CreateCategoryModel;
import com.yuliatallus.moneytracker.ui.activities.LoginActivity_;
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


    private static final String TAG = CategoriesFragment.class.getSimpleName();
    private CategoriesAdapter adapter;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;


    @ViewById(R.id.context_recyclerview)
    RecyclerView categoriesRecyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;

    @ViewById(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    @Click(R.id.fab)
    void fabClicked() {
        if (getView() != null) {

            LayoutInflater li = LayoutInflater.from(getContext());
            View addCatView = li.inflate(R.layout.add_category_layout, null);
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
            mDialogBuilder.setView(addCatView);
            final EditText userInput = (EditText) addCatView.findViewById(R.id.input_text);
            mDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton(ConstantBox.OK,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Categories newCat = new Categories(userInput.getText().toString());
                                    newCat.save();
                                    addCategory(newCat);
                                    refreshCategoriesFragment();
                                }
                            })
                    .setNegativeButton(ConstantBox.CANCEL,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = mDialogBuilder.create();
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            alertDialog.show();
        }
    }

    @AfterViews
    void ready() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle(getString(R.string.nav_drawer_categories));
        loadData("");
        swipeRefreshLayout.setColorSchemeResources(R.color.color_accent, R.color.color_primary_dark, R.color.cardview_shadow_end_color);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData("");
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchView searchView = (SearchView) menuItem.getActionView();
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
    public void delayedQuery(String filter) {
        loadData(filter);
    }


    private void loadData(final String filter) {
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
                adapter = new CategoriesAdapter(getContext(), data, new CategoriesAdapter.ClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        if (actionMode != null) {
                            toggleSelection(position);
                        }
                    }

                    @Override
                    public boolean onItemLongClicked(int position) {
                        if (actionMode == null) {
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            actionMode = activity.startSupportActionMode(actionModeCallback);
                        }
                        toggleSelection(position);
                        return true;
                    }
                });
                categoriesRecyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {

            }
        });
    }

    @Background
    void addCategory(Categories category) {
        RestService restService = new RestService();
        CreateCategoryModel createCategoryModel = restService.createCat(getContext(), category.name);
        switch (createCategoryModel.getStatus()) {

            case ConstantBox.SUCCESS:
                Log.d(TAG, "Status: " + createCategoryModel.getStatus() +
                        ", Title: " + createCategoryModel.getData().getTitle() +
                        ", Id: " + createCategoryModel.getData().getId());
                break;

            case ConstantBox.UNAUTHORIZED:
                startActivity(new Intent(getContext(), LoginActivity_.class));
                break;

            case ConstantBox.ERROR:
                Log.d(TAG, "Ошибка при добавлении категории");
                break;
        }

    }

    private void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemsCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contexual_action_bar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_remove:
                    adapter.removeItems(adapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;

        }
    }

    void refreshCategoriesFragment() {
        Fragment fragment;
        fragment = getFragmentManager().findFragmentById(R.id.main_container);
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.detach(fragment);
        fragTransaction.attach(fragment);
        fragTransaction.commit();
    }


}