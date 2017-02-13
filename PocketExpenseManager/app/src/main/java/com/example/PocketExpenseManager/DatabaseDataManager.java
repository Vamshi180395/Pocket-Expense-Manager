package com.example.PocketExpenseManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Rama Vamshi Krishna on 10/14/2016.
 */
public class DatabaseDataManager {
    private Context mcontext;
    private DatabaseOpenHelper dbopenhelper;
    private SQLiteDatabase db;
    private UserDAO userdao;
    ExpensesDAO expensesdao;

    public DatabaseDataManager(Context mcontext) {
        this.mcontext = mcontext;
        dbopenhelper = new DatabaseOpenHelper(this.mcontext);
        db = dbopenhelper.getWritableDatabase();
        userdao = new UserDAO(db);
        expensesdao=new ExpensesDAO(db);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public long saveUser(User user) {
        return this.userdao.save(user);

    }

    public boolean updateUser(User user) {
        return this.userdao.update(user);
    }
    public boolean deleteUser(User user) {
        return this.userdao.delete(user);
    }
    public User getUser(String useremail)
    {
        return this.userdao.get(useremail);
    }
    public List<User> getAllUsers(){
        return this.userdao.getALL();
    }



    public long saveExpenses(Expenses expenses) {
        return this.expensesdao.save(expenses);
    }

    public boolean updateExpenses(Expenses expenses) {
        return this.expensesdao.update(expenses);
    }
    public boolean deleteExpenses(Expenses expenses) {
        return this.expensesdao.delete(expenses);
    }
    public Expenses getExpenses(String useremail,long id)
    {
        return this.expensesdao.get(useremail,id);
    }
    public List<Expenses> getAllExpenses(){
        return this.expensesdao.getALL();
    }


}