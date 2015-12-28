package com.yuliatallus.moneytracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.database.Categories;

import java.util.List;

public class MySpinAdapter extends ArrayAdapter<Categories> implements SpinnerAdapter{

    List<Categories> categories;

    public MySpinAdapter(Context context,List<Categories> objects) {
        super(context, 0, objects);
        this.categories = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Categories category = getItem(position);

        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_text, parent,false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.spinner_text_view);

        name.setText(category.name);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Categories category = getItem(position);

        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_text, parent,false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.spinner_text_view);

        name.setText(category.name);

        return convertView;
    }
}
