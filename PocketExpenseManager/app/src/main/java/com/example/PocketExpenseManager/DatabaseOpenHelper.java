package com.example.PocketExpenseManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rama Vamshi Krishna on 10/14/2016.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME="pocketmanager.db";
    static final int DB_VERSION=15;

    public DatabaseOpenHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        UsersTable.onCreate(db);
        ExpensesTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        UsersTable.onUpgrade(db,i,i1);
        ExpensesTable.onUpgrade(db,i,i1);
    }
}
