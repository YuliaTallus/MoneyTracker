package com.yuliatallus.moneytracker.ui.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.yuliatallus.moneytracker.Category;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.adapters.CategoriesAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.categories_fragment)
public class CategoriesFragment extends Fragment {

    @ViewById(R.id.context_recyclerview)
    RecyclerView categoriesRecyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;



    @Click(R.id.fab)
    void fabClicked() {
        if (getView() != null && floatingActionButton.isPressed()) {
            Snackbar.make(getView(), R.string.nice_text, Snackbar.LENGTH_SHORT).show();
        }
    }

    @AfterViews
    void ready() {
        List<Category> adapterData = getDataList();
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(adapterData);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);
        fabClicked();
        getActivity().setTitle(getString(R.string.nav_drawer_categories));
    }

    List<Category> getDataList(){
        List<Category> data = new ArrayList<>();
        data.add(new Category("Phone"));
        data.add(new Category("Food"));
        data.add(new Category("Entertainment"));
        data.add(new Category("Clothes"));
        data.add(new Category("Books"));
        data.add(new Category("Education"));
        return  data;
    }

}