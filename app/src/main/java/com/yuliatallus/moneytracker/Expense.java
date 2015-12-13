package com.yuliatallus.moneytracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Expense {
    private String title;
    private int sum;
    private Date date;

    public Expense(String title, String sumStr, Date date){
        this.title = title;
        this.sum = Integer.parseInt(sumStr);
        this.date = date;
    }

    public String getTitle(){
        return this.title;
    }

    public int getSum(){
        return this.sum;
    }

    public String getSumString() {
        return Integer.toString(sum);
    }

    public Date getDate(){
        return this.date;
    }

    public String getDateString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        return dateFormat.format(date);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setSum(int sum){
        this.sum = sum;
    }

    public void setDate(Date date){
        this.date = date;
    }
}
