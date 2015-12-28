package com.yuliatallus.moneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.database.Categories;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CardViewHolder> {
    List<Categories> categories;

    public CategoriesAdapter(List<Categories> categories) {
        this.categories = categories;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item, parent,false);
        return new CardViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Categories category = categories.get(position);

        holder.name_text.setText(category.name);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        protected TextView name_text;

        public CardViewHolder(View convertView){
            super(convertView);
            name_text = (TextView) convertView.findViewById(R.id.name_text);
        }
    }
}
