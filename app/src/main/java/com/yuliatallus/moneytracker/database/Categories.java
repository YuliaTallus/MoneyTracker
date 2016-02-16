package com.yuliatallus.moneytracker.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.yuliatallus.moneytracker.util.ConstantBox;

import java.util.List;

@Table(name = ConstantBox.CATEGORIES)
public class Categories extends Model {

    @Column(name = ConstantBox.NAME)
    public String name;

    public Categories(){
        super();
    }

    public Categories(String name){
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static List<Categories> categories(String filter){
        return new Select()
                .from(Categories.class)
                .where("name LIKE ?", new String[]{'%' + filter + '%'})
                .execute();
    }
}
