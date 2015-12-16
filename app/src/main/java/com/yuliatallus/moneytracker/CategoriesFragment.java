package com.yuliatallus.moneytracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoriesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.categories_fragment, container, false);
        RecyclerView categoriesRecyclerView = (RecyclerView)mainView.findViewById(R.id.context_recyclerview);
        List<Category> adapterData = getDataList();
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(adapterData);
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton floatingActionButton = (FloatingActionButton) mainView.findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mainView,R.string.nice_text, Snackbar.LENGTH_SHORT).show();
            }
        });

        getActivity().setTitle(R.string.nav_drawer_categories);
        return mainView;
    }

    private List<Category> getDataList(){
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
