package com.example.PocketExpenseManager;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rama Vamshi Krishna on 11/1/2016.
 */
public class UsersTable {
    static final String TABLE_NAME="Users";
    static final String COLUMN_USERNAME="name";
    static final String COLUMN_USEREMAIL="email";
    static final String COLUMN_USERPASSWORD="password";


    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+TABLE_NAME+ " (");
        sb.append(COLUMN_USERNAME + " text not null, ");
        sb.append(COLUMN_USEREMAIL + " text primary key, ");
        sb.append(COLUMN_USERPASSWORD + " text not null );");
        try {
            db.execSQL(sb.toString());
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldversion,int newversion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        UsersTable.onCreate(db);

    }
}
