package com.example.PocketExpenseManager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rama Vamshi Krishna on 11/2/2016.
 */
public class UserDAO {
    private SQLiteDatabase db;

    public UserDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(User user) {
        ContentValues values = new ContentValues();
        values.put(UsersTable.COLUMN_USERNAME, user.getName());
        values.put(UsersTable.COLUMN_USEREMAIL, user.getEmail());
        values.put(UsersTable.COLUMN_USERPASSWORD, user.getPassword());


        return db.insert(UsersTable.TABLE_NAME, null, values);
    }

    public boolean update(User user) {
        ContentValues values = new ContentValues();
        values.put(UsersTable.COLUMN_USERNAME, user.getName());
        values.put(UsersTable.COLUMN_USEREMAIL, user.getEmail());
        values.put(UsersTable.COLUMN_USERPASSWORD, user.getPassword());
        return db.update(UsersTable.TABLE_NAME, values, UsersTable.COLUMN_USEREMAIL + "=?", new String[]{user.getEmail()}) > 0;
    }

    public boolean delete(User user) {
        return db.delete(UsersTable.TABLE_NAME, UsersTable.COLUMN_USEREMAIL + "=?", new String[]{user.getEmail()}) > 0;
    }


    public User get(String useremail) {
        User user = null;
        Cursor c = db.query(true, UsersTable.TABLE_NAME, new String[]{UsersTable.COLUMN_USERNAME, UsersTable.COLUMN_USEREMAIL, UsersTable.COLUMN_USERPASSWORD}, UsersTable.COLUMN_USEREMAIL + "=?", new String[]{useremail}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {

            user = buildNOteFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }

        return user;
    }

    public List<User> getALL() {
        List<User> users = new ArrayList<User>();
        Cursor c = db.query(UsersTable.TABLE_NAME, new String[]{UsersTable.COLUMN_USERNAME, UsersTable.COLUMN_USEREMAIL, UsersTable.COLUMN_USERPASSWORD}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                User user = buildNOteFromCursor(c);
                if (user != null) {
                    users.add(user);
                }
            } while (c.moveToNext());

            if (!c.isClosed()) {
                c.close();
            }
        }

        return users;
    }

    private User buildNOteFromCursor(Cursor c) {
        User user = null;
        if (c != null) {
            user = new User();
            user.setName(c.getString(0));
            user.setEmail(c.getString(1));
            user.setPassword(c.getString(2));

        }
        return user;
    }
}


