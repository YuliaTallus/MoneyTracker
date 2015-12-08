package com.yuliatallus.moneytracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CategoriesAdapter extends ArrayAdapter<Category>{
    List<Category> categories;

    public CategoriesAdapter(Context context, List<Category> categories) {
        super(context,0, categories);
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = getItem(position);

        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.categories_item, parent,false);
        }

        convertView.setBackgroundColor(Color.parseColor("#FF7B68EE"));

        TextView name = (TextView) convertView.findViewById(R.id.name_text);

        name.setText(category.title);

        return convertView;
    }
}
