package com.example.PocketExpenseManager;

import java.io.Serializable;

/**
 * Created by Rama Vamshi Krishna on 9/8/2016.
 */
public class Expense implements Serializable {

    String expensename,expensecategory,date;
    String amount,expensecurrency;
    String recptimg;


    public Expense(String expensename, String expensecategory, String date, String amount, String expensecurrency,String recptimg) {
        super();
        this.expensename = expensename;
        this.expensecategory = expensecategory;
        this.date = date;
        this.amount = amount;
        this.expensecurrency=expensecurrency;
        this.recptimg = recptimg;
    }
}
