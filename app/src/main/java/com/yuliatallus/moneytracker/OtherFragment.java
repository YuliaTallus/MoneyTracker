package com.yuliatallus.moneytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yushe_000 on 07.12.2015.
 */
public class OtherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.other_fragment, container, false);
        ListView expensesListView = (ListView)mainView.findViewById(R.id.list_view);
        List<Expense> adapterData = getDataList();
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(getActivity(), adapterData);
        expensesListView.setAdapter(expensesAdapter);
        getActivity().setTitle(R.string.not_expenses);
        return mainView;
    }

    private List<Expense> getDataList(){
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("Phone", "9000"));
        data.add(new Expense("Clothes", "5000"));
        return  data;
    }
}