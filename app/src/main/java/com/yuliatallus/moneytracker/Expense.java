package com.yuliatallus.moneytracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Expense {
    public String title;
    public int sum;
    public Date date;
    //public GregorianCalendar date;

    public Expense(String title, String sumStr, Date date){
        this.title = title;
        this.sum = Integer.parseInt(sumStr);
        this.date = date;
    }

    public String getSumString(){
        return Integer.toString(sum);
    }

    public String getDateString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }
}
