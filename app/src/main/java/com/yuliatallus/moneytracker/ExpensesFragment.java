package com.yuliatallus.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpensesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.expenses_fragment, container, false);
        RecyclerView expensesRecyclerView = (RecyclerView)mainView.findViewById(R.id.context_recyclerview);
        List<Expense> adapterData = getDataList();
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(adapterData);
        expensesRecyclerView.setAdapter(expensesAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expensesRecyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton floatingActionButton = (FloatingActionButton) mainView.findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 //               Snackbar.make(mainView, R.string.nice_text,Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
                intent.putExtra("key", "value");
                getActivity().startActivity(intent);
            }
        });

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
