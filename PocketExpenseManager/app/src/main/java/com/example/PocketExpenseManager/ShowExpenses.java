package com.example.PocketExpenseManager;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowExpenses extends AppCompatActivity {
    ArrayList expenses = new ArrayList();
    TextView tv1,tv2,tv3,tv4;
    int i=0;
    ImageView imageview,iv1,iv2,iv3,iv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses);
        getSupportActionBar().setTitle("My Expenses");
        tv1=(TextView) findViewById(R.id.tvname);
        tv2=(TextView) findViewById(R.id.tvcategory);
        tv3=(TextView) findViewById(R.id.tvamount);
        tv4=(TextView) findViewById(R.id.tvdate);
        iv1=(ImageView) findViewById(R.id.imageView5);
        iv2=(ImageView) findViewById(R.id.imageView2);
        iv3=(ImageView) findViewById(R.id.imageView3);
        iv4=(ImageView) findViewById(R.id.imageView4);
        imageview = (ImageView) findViewById(R.id.imageView6);

        if (getIntent().getExtras() != null) {
            expenses = getIntent().getExtras().getParcelableArrayList(AppActivity.EXPENSES_ARRAY);
            if (expenses.size()==0)
            {
                Toast.makeText(ShowExpenses.this, "There are no expenses to be displayed.", Toast.LENGTH_SHORT).show();
            }
            else{
                showExpenseDetails(i);
            }

        }

    }
    public void showExpenseDetails(int index){
        Expense expense= (Expense) expenses.get(index);
        tv1.setText(expense.expensename.toString());
        tv2.setText(expense.expensecategory);
       tv3.setText(expense.amount+" "+expense.expensecurrency);
        tv4.setText(expense.date.toString());
        imageview.setImageURI(Uri.parse(expense.recptimg));
    }
    public void showForward(View view){
        if (expenses.size()==0)
        {
            Toast.makeText(ShowExpenses.this, "Sorry there are no expenses to be displayed.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (i == expenses.size() - 1 || i > expenses.size() - 1) {
                Toast.makeText(this, "End of the list reached.", Toast.LENGTH_SHORT).show();

            } else {
                i = i + 1;
                showExpenseDetails(i);
            }
        }

    }
    public void showBackward(View view){
        if (expenses.size()==0)
        {
            Toast.makeText(ShowExpenses.this, "Sorry there are no expenses to be displayed.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (i == 0 || i < 0) {
                Toast.makeText(this, "This is the first expense in the  list.", Toast.LENGTH_SHORT).show();

            } else {
                i = i - 1;
                showExpenseDetails(i);
            }
        }

    }
    public void showLast(View view){
        if (expenses.size()==0)
        {
            Toast.makeText(ShowExpenses.this, "Sorry there are no expenses to be displayed.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (i == expenses.size() - 1 || i > expenses.size() - 1) {
                Toast.makeText(this, "End of the list reached.", Toast.LENGTH_SHORT).show();

            } else {
                i = expenses.size() - 1;
                showExpenseDetails(i);
            }
        }
    }
    public void showFirst(View view){
        if (expenses.size()==0)
        {
            Toast.makeText(ShowExpenses.this, "Sorry there are no expenses to be displayed.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (i == 0 || i < 0) {
                Toast.makeText(this, "This is the first expense in the  list.", Toast.LENGTH_SHORT).show();

            } else {
                i = 0;
                showExpenseDetails(i);
            }
        }
    }
    public void finish(View view) {

        finish();
    }
}
