package com.example.PocketExpenseManager;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rama Vamshi Krishna on 11/2/2016.
 */
public class ExpensesTable {
    static final String TABLE_NAME="ExpensesTable";
    static final String COLUMN_ID="_id";
    static final String COLUMN_USERNAME="username";
    static final String COLUMN_EXPENSENAME="expensename";
    static final String COLUMN_EXPENSECATEGORY="expensecategory";
    static final String COLUMN_EXPENSEDATE="date";
    static final String COLUMN_EXPENSEAMOUNT="amount";
    static final String COLUMN_EXPENSECURRENCY="expensecurrency";
    static final String COLUMN_EXPENSERECEIPTIMAGE="receiptimg";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+TABLE_NAME+ " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_USERNAME + " text not null, ");
        sb.append(COLUMN_EXPENSENAME + " text not null, ");
        sb.append(COLUMN_EXPENSECATEGORY + " text not null, ");
        sb.append(COLUMN_EXPENSEDATE + " text not null, ");
        sb.append(COLUMN_EXPENSEAMOUNT + " text not null, ");
        sb.append(COLUMN_EXPENSECURRENCY + " text not null, ");
        sb.append(COLUMN_EXPENSERECEIPTIMAGE + " text not null );");

        try {
            db.execSQL(sb.toString());
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }


    }

    static public void onUpgrade(SQLiteDatabase db, int oldversion,int newversion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        ExpensesTable.onCreate(db);

    }



}
