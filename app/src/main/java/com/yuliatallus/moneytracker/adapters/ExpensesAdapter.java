package com.yuliatallus.moneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.database.Expenses;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.CardViewHolder> {
    List<Expenses> expenses;

    public ExpensesAdapter(List<Expenses> expenses){
        this.expenses = expenses;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expenses_item, parent,false);
        return new CardViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expenses expense = expenses.get(position);

        holder.name_text.setText(expense.name);
        holder.sum_text.setText(expense.price);
        holder.date_text.setText(expense.date);
        // ADD CATEGORY FIELD!!!!!!!!!!!!!
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }


    public class CardViewHolder extends RecyclerView.ViewHolder{
        protected  TextView name_text;
        protected TextView sum_text;
        protected TextView date_text;

        public CardViewHolder(View convertView) {
            super(convertView);
            name_text = (TextView) convertView.findViewById(R.id.name_text);
            sum_text = (TextView) convertView.findViewById(R.id.sum_text);
            date_text = (TextView)convertView.findViewById(R.id.date_text);
        }
    }
}
