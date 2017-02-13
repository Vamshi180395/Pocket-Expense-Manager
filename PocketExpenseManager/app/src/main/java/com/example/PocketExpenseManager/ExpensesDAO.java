package com.example.PocketExpenseManager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rama Vamshi Krishna on 11/2/2016.
 */
public class ExpensesDAO {
    private SQLiteDatabase db;
    public ExpensesDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Expenses expenses) {
        ContentValues values = new ContentValues();
        values.put(ExpensesTable.COLUMN_USERNAME, expenses.getUsername());
        values.put(ExpensesTable.COLUMN_EXPENSENAME, expenses.getExpensename());
        values.put(ExpensesTable.COLUMN_EXPENSECATEGORY, expenses.getExpensecategory());
        values.put(ExpensesTable.COLUMN_EXPENSEDATE, expenses.getDate());
        values.put(ExpensesTable.COLUMN_EXPENSEAMOUNT, expenses.getAmount());
        values.put(ExpensesTable.COLUMN_EXPENSECURRENCY, expenses.getExpensecurrency());
        values.put(ExpensesTable.COLUMN_EXPENSERECEIPTIMAGE, expenses.getRecptimg());


        return db.insert(ExpensesTable.TABLE_NAME, null, values);
    }

    public boolean update(Expenses expenses) {
        ContentValues values = new ContentValues();
        values.put(ExpensesTable.COLUMN_USERNAME, expenses.getUsername());
        values.put(ExpensesTable.COLUMN_EXPENSENAME, expenses.getExpensename());
        values.put(ExpensesTable.COLUMN_EXPENSECATEGORY, expenses.getExpensecategory());
        values.put(ExpensesTable.COLUMN_EXPENSEDATE, expenses.getDate());
        values.put(ExpensesTable.COLUMN_EXPENSEAMOUNT, expenses.getAmount());
        values.put(ExpensesTable.COLUMN_EXPENSECURRENCY, expenses.getExpensecurrency());
        values.put(ExpensesTable.COLUMN_EXPENSERECEIPTIMAGE, expenses.getRecptimg());
        return db.update(ExpensesTable.TABLE_NAME, values, ExpensesTable.COLUMN_USERNAME + "=?" + " and " + ExpensesTable.COLUMN_ID + "=?", new String[]{expenses.getUsername(),expenses.get_id()+""}) > 0;
    }

    public boolean delete(Expenses expenses) {
        return db.delete(ExpensesTable.TABLE_NAME, ExpensesTable.COLUMN_USERNAME + "=?" + " and " + ExpensesTable.COLUMN_ID + "=?", new String[]{expenses.getUsername(),expenses.get_id()+""}) > 0;
    }


    public Expenses get(String useremail,long id) {
        Expenses expenses = null;
        Cursor c = db.query(true, ExpensesTable.TABLE_NAME, new String[]{ExpensesTable.COLUMN_ID,ExpensesTable.COLUMN_USERNAME, ExpensesTable.COLUMN_EXPENSENAME, ExpensesTable.COLUMN_EXPENSECATEGORY,ExpensesTable.COLUMN_EXPENSEDATE, ExpensesTable.COLUMN_EXPENSEAMOUNT,ExpensesTable.COLUMN_EXPENSECURRENCY, ExpensesTable.COLUMN_EXPENSERECEIPTIMAGE}, ExpensesTable.COLUMN_USERNAME + "=?"+" and " + ExpensesTable.COLUMN_ID + "=?", new String[]{useremail,id+""}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {

            expenses = buildNOteFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }

        return expenses;
    }

    public List<Expenses> getALL() {
        List<Expenses> allexpenses = new ArrayList<Expenses>();
        Cursor c = db.query(ExpensesTable.TABLE_NAME, new String[]{ExpensesTable.COLUMN_ID,ExpensesTable.COLUMN_USERNAME, ExpensesTable.COLUMN_EXPENSENAME, ExpensesTable.COLUMN_EXPENSECATEGORY,ExpensesTable.COLUMN_EXPENSEDATE, ExpensesTable.COLUMN_EXPENSEAMOUNT,ExpensesTable.COLUMN_EXPENSECURRENCY, ExpensesTable.COLUMN_EXPENSERECEIPTIMAGE}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                Expenses expenses = buildNOteFromCursor(c);
                if (expenses != null) {
                    allexpenses.add(expenses);
                }
            } while (c.moveToNext());

            if (!c.isClosed()) {
                c.close();
            }
        }

        return allexpenses;
    }

    private Expenses buildNOteFromCursor(Cursor c) {
        Expenses expenses = null;
        if (c != null) {
            expenses = new Expenses();
            expenses.set_id(c.getLong(0));
            expenses.setUsername(c.getString(1));
            expenses.setExpensename(c.getString(2));
            expenses.setExpensecategory(c.getString(3));
            expenses.setDate(c.getString(4));
            expenses.setAmount(c.getString(5));
            expenses.setExpensecurrency(c.getString(6));
            expenses.setRecptimg(c.getString(7));

        }
        return expenses;
    }
}


