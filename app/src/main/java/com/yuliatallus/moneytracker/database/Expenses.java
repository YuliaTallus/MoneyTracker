package com.yuliatallus.moneytracker.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.yuliatallus.moneytracker.util.ConstantBox;

import java.util.List;

@Table(name = ConstantBox.expenses)
public class Expenses extends Model{

    @Column(name = ConstantBox.price)
    public String price;

    @Column(name = ConstantBox.name)
    public String name;

    @Column(name = ConstantBox.date)
    public String date;

    @Column(name = ConstantBox.category)
    public Categories category;

    public Expenses(){
        super();
    }

    public Expenses(String price, String name, String date, Categories category){
        super();
        this.price = price;
        this.name = name;
        this.date = date;
        this.category = category;
    }

    public List<Expenses> expenses(){
        return getMany(Expenses.class, ConstantBox.category);
    }
}
