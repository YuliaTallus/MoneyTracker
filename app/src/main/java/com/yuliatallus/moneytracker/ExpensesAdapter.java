package com.yuliatallus.moneytracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ExpensesAdapter extends ArrayAdapter<Expense>{
    List<Expense> expenses;

    public ExpensesAdapter(Context context, List<Expense> expenses) {
        super(context,0, expenses);
        this.expenses = expenses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense expense = getItem(position);

        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.expenses_item, parent,false);
        }
        if(position%2==0){
            convertView.setBackgroundColor(Color.parseColor("#FF7B68EE"));
        }
        else{
            convertView.setBackgroundColor(Color.parseColor("#9999FF"));
        }


        TextView name = (TextView) convertView.findViewById(R.id.name_text);
        TextView sum = (TextView) convertView.findViewById(R.id.sum_text);
        TextView date = (TextView)convertView.findViewById(R.id.date_text);

        name.setText(expense.getTitle());
        sum.setText(expense.getSumString());
        date.setText(expense.getDateString());

        return convertView;
    }
}
