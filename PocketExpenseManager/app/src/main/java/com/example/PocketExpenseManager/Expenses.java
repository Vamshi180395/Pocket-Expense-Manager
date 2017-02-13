package com.example.PocketExpenseManager;

import java.io.Serializable;

/**
 * Created by Rama Vamshi Krishna on 11/2/2016.
 */
public class Expenses implements Serializable {
    private long _id;
    String username, expensename,expensecategory,date;
    String amount,expensecurrency;
    String recptimg;


    public Expenses(String username, String expensename, String expensecategory, String date, String amount, String expensecurrency, String recptimg) {
        super();
        this.username=username;
        this.expensename = expensename;
        this.expensecategory = expensecategory;
        this.date = date;
        this.amount = amount;
        this.expensecurrency=expensecurrency;
        this.recptimg = recptimg;
    }
    public Expenses(){

    }

    public String getExpensecurrency() {
        return expensecurrency;
    }

    public void setExpensecurrency(String expensecurrency) {
        this.expensecurrency = expensecurrency;
    }

    public String getUsername() {
        return username;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExpensename() {
        return expensename;
    }

    public void setExpensename(String expensename) {
        this.expensename = expensename;
    }

    public String getExpensecategory() {
        return expensecategory;
    }

    public void setExpensecategory(String expensecategory) {
        this.expensecategory = expensecategory;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRecptimg() {
        return recptimg;
    }

    public void setRecptimg(String recptimg) {
        this.recptimg = recptimg;
    }
}
