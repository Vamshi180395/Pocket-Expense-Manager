package com.example.PocketExpenseManager;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AppActivity extends AppCompatActivity {
    ArrayList expenses=new ArrayList();
    static final int REQ_ADD=1;
    static final int REQ_EDIT=2;
    static final int REQ_DELETE=3;
    static final int REQ_SHOW=4;
    static final int REQ_CHANGEPASSWORD=5;
    final static String VALUE_KEY="expense";
List<Expenses> allexpenseslist;
    ArrayList<Expense> userexpenses=new ArrayList<Expense>();
    final static String EXPENSES_ARRAY="expenses";
    TextView welcomemsg;
    DatabaseDataManager dbmanager;
    ListView mylistview;
    TextView addimage;
    SharedPreferences mPrefs;
    String UserGoogleDisplayName;
User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        getSupportActionBar().setTitle("Home "+getEmijoByUnicode(0x2764));
        welcomemsg=(TextView) findViewById(R.id.welcomemessage);
        addimage=(TextView) findViewById(R.id.tvfloat);
        mylistview=(ListView) findViewById(R.id.mylistview);
        if (getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable("User");
            if(user!=null) {
                welcomemsg.setText("Hi " + user.getName().substring(0, 1).toUpperCase() + user.getName().substring(1, user.getName().length()).toLowerCase() + " " + getEmijoByUnicode(0x1F60A));
                setListView();
            }

        }

      mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Intent in=new Intent(AppActivity.this,EditExpense.class);
              in.putExtra(EXPENSES_ARRAY,expenses);
              ArrayList<Expenses> iallexpenseslist=new ArrayList<Expenses>();
              iallexpenseslist.addAll(allexpenseslist);
              in.putExtra("AllDataBaseExpensesList", iallexpenseslist);
              Expense selectedexpeseforediting=(Expense) expenses.get(i);
              in.putExtra("SelectedExpenseForEditing", selectedexpeseforediting);
              startActivityForResult(in,REQ_EDIT);

          }
      });
        mylistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayList<Expenses> iallexpenseslist=new ArrayList<Expenses>();
                iallexpenseslist.addAll(allexpenseslist);
                final Expense selectedexpesefordeleting=(Expense) expenses.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(AppActivity.this);
                builder.setTitle("Alert!!!");
                builder.setMessage("Are you sure about deleting the expense?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                        for(int i=0; i<iallexpenseslist.size();i++){
                            Expenses expenses=(Expenses) iallexpenseslist.get(i);
                            if(expenses.getExpensename().equals(selectedexpesefordeleting.expensename) && expenses.getExpensecategory().equals(selectedexpesefordeleting.expensecategory) && expenses.getDate().equals(selectedexpesefordeleting.date) &&expenses.getAmount().equals(selectedexpesefordeleting.amount) &&expenses.getRecptimg().equals(selectedexpesefordeleting.recptimg)){
                                dbmanager=new DatabaseDataManager(AppActivity.this);
                                dbmanager.deleteExpenses(expenses);
                            }
                        }
                        expenses.remove(selectedexpesefordeleting);
                        setListView();

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                final AlertDialog alert1 = builder.create();
                alert1.show();
                return true;
            }
        });
    }
    public void addExpense(View view) {
        Intent i=new Intent(AppActivity.this,AddExpense.class);
        i.putExtra("User",user);
        startActivityForResult(i,REQ_ADD);
    }

    public void finish(View view) {

        finish();
    }
    public String getEmijoByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQ_ADD && resultCode==RESULT_OK){
            Expense expense=(Expense) data.getExtras().getSerializable(VALUE_KEY);
            expenses.add(expense);
            Expenses texpenses=new Expenses(user.getEmail(),expense.expensename,expense.expensecategory,expense.date,expense.amount,expense.expensecurrency,expense.recptimg);
            dbmanager=new DatabaseDataManager(this);
            dbmanager.saveExpenses(texpenses);
            setListView();


        }
        else if (requestCode==REQ_EDIT && resultCode==RESULT_OK){
            expenses=(ArrayList) data.getExtras().getParcelableArrayList(VALUE_KEY);
            setListView();
        }
        else if (requestCode==REQ_DELETE && resultCode==RESULT_OK){
            expenses=(ArrayList) data.getExtras().getParcelableArrayList(VALUE_KEY);
            setListView();
        }

    }

    private void setListView() {
        userexpenses.clear();
        dbmanager=new DatabaseDataManager(this);
        allexpenseslist=dbmanager.getAllExpenses();
        if(allexpenseslist.size()!=0){
            for(int i=0;i<allexpenseslist.size();i++){
                if(allexpenseslist.get(i).getUsername().equals(user.getEmail())){
                    userexpenses.add(new Expense(allexpenseslist.get(i).getExpensename(),allexpenseslist.get(i).getExpensecategory(),allexpenseslist.get(i).getDate(),allexpenseslist.get(i).getAmount(),allexpenseslist.get(i).getExpensecurrency(),allexpenseslist.get(i).getRecptimg()));
                }
            }
            expenses=userexpenses;
            if(expenses!=null && expenses.size()!=0){
                mylistview.setVisibility(View.VISIBLE);
                addimage.setText(" ");
                MyAdapter adapter = new MyAdapter(AppActivity.this, R.layout.row_layout, expenses,user.getEmail());
                mylistview.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

            }
            else{
                mylistview.setVisibility(View.GONE);
                addimage.setVisibility(View.VISIBLE);
                TranslateAnimation animation = new TranslateAnimation(0f, 1000.0f, 0f, 1000.0f);
                animation = new TranslateAnimation(0f, 900.0f, 0f, 900.0f);  //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation.setDuration(3000);  // animation duration
                animation.setRepeatCount(1000);  // animation repeat count
                animation.setRepeatMode(2);
                addimage.setText(getEmijoByUnicode(0x1F4B1));
                addimage.setTextSize(70);
                addimage.startAnimation(animation);

            }
        }
else{
            mylistview.setVisibility(View.GONE);
            addimage.setVisibility(View.VISIBLE);
                TranslateAnimation animation = new TranslateAnimation(0f, 1000.0f, 0f, 1000.0f);
        animation = new TranslateAnimation(0f, 900.0f, 0f, 900.0f);  //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation.setDuration(3000);  // animation duration
                animation.setRepeatCount(1000);  // animation repeat count
                animation.setRepeatMode(2);
                addimage.setText(getEmijoByUnicode(0x1F4B1));
            addimage.setTextSize(70);
                addimage.startAnimation(animation);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Sign_Out) {
            Intent i=new Intent(AppActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else if(id == R.id.show_My_Expenses){
            Intent i=new Intent(AppActivity.this,ShowExpenses.class);
            i.putExtra(EXPENSES_ARRAY,expenses);
            startActivity(i);
        }

        else if(id == R.id.Change_Password){
            Intent i=new Intent(AppActivity.this,ChangePassword.class);
           i.putExtra("User",user);
           startActivityForResult(i,REQ_CHANGEPASSWORD);

        }
        else if(id == R.id.Show_Summary){
            Intent i=new Intent(AppActivity.this,Summary.class);
            i.putExtra(EXPENSES_ARRAY,expenses);
            startActivity(i);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppActivity.this);
        builder.setTitle("Alert!!!");
        builder.setMessage("Are you sure, you want to Exit?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int k) {
                Intent i=new Intent(AppActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        final AlertDialog alert1 = builder.create();
        alert1.show();
    }

}

