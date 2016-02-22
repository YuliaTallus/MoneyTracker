package com.yuliatallus.moneytracker.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.yuliatallus.moneytracker.util.ConstantBox;

import java.util.List;

@Table(name = ConstantBox.EXPENSES)
public class Expenses extends Model{

    @Column(name = ConstantBox.PRICE)
    public String price;

    @Column(name = ConstantBox.NAME)
    public String name;

    @Column(name = ConstantBox.DATE)
    public String date;

    @Column(name = ConstantBox.CATEGORY)
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

    public static List<Expenses> expenses(String filter){
        return new Select()
                .from(Expenses.class)
                .where("name LIKE ?", new String[]{'%' + filter + '%'})
                .execute();
    }
}
