package com.yuliatallus.moneytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpensesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.expenses_fragment, container, false);
        ListView expensesListView = (ListView)mainView.findViewById(R.id.list_view);
        List<Expense> adapterData = getDataList();
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(getActivity(), adapterData);
        expensesListView.setAdapter(expensesAdapter);
        getActivity().setTitle(R.string.nav_drawer_expenses);
        return mainView;
    }

    private List<Expense> getDataList(){
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("Phone","900", new Date()));
        data.add(new Expense("Food","5000", new Date()));
        data.add(new Expense("Flat","3000", new Date()));
        data.add(new Expense("Clothes","9900", new Date()));
        data.add(new Expense("Entertainment","500", new Date()));
        data.add(new Expense("Education","10000", new Date()));
        data.add(new Expense("PC","800", new Date()));
        return  data;
    }
}
