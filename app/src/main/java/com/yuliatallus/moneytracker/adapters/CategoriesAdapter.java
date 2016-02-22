package com.yuliatallus.moneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.database.Categories;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CategoriesAdapter extends SelectableAdapter<CategoriesAdapter.CardViewHolder> {
    List<Categories> categories;
    private  ClickListener clickListener;

    public CategoriesAdapter(List<Categories> categories, ClickListener clickListener) {
        this.clickListener = clickListener;
        this.categories = categories;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item, parent,false);
        return new CardViewHolder(convertView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Categories category = categories.get(position);

        holder.name_text.setText(category.name);
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

    }

    public void removeItem(int position){
        removeCategories(position);
        notifyItemRemoved(position);
    }

    public void removeItems(List<Integer> positions){
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        while (!positions.isEmpty()){
            if (positions.size() == 1){
                removeItem(positions.get(0));
                positions.remove(0);
            }
            else {
                int count = 1;
                while (positions.size() > count){
                    count++;
                }
                removeRange(count-1, count);

                for (int i = 0; i < count; i++){
                    positions.remove(0);
                }
            }

        }
    }

    private void removeRange(int positionStart, int itemCount){
        for (int position = 0; position < itemCount; position++){
            removeCategories(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    private void removeCategories(int position){
        if (categories.get(position)!= null){
            categories.get(position).delete();
            categories.remove(position);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        protected TextView name_text;
        protected View selectedOverlay;

        private ClickListener clickListener;

        public CardViewHolder(View convertView, ClickListener clickListener) {
            super(convertView);
            name_text = (TextView) convertView.findViewById(R.id.name_text);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            this.clickListener = clickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener!= null){
                clickListener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return clickListener!=null && clickListener.onItemLongClicked(getAdapterPosition());
        }
    }

    public interface ClickListener {
        void onItemClicked(int position);

        boolean onItemLongClicked(int position);
    }
}
