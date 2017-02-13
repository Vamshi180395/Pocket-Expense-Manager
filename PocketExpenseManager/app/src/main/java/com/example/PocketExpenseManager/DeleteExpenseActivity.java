package com.example.PocketExpenseManager;

/**
 * Created by Rama Vamshi Krishna on 11/5/2016.
 */
public class DeleteExpenseActivity {
    /*package com.example.hw2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class  DeleteExpense extends AppCompatActivity {
    ArrayList expenses = new ArrayList();
    CharSequence[] expensenames;
    String selectedexpense="";
    EditText txtboxname,txtboxamount,txtboxdate;
    Spinner spcategory;
    int editedindex;
    ArrayAdapter<CharSequence> adapter;
    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_expense);
        txtboxname=(EditText) findViewById(R.id.txtboxexpensename);

        txtboxamount=(EditText) findViewById(R.id.txtboxamount3);

        txtboxdate=(EditText) findViewById(R.id.txtboxdate);

        imageview = (ImageView) findViewById(R.id.imageView6);
        spcategory=(Spinner) findViewById(R.id.spinner);
         adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcategory.setAdapter(adapter);

        if (getIntent().getExtras() != null) {
            txtboxname.setKeyListener(null);
txtboxamount.setKeyListener(null);
            txtboxdate.setKeyListener(null);
            expenses = getIntent().getExtras().getParcelableArrayList(AppActivity.EXPENSES_ARRAY);
            expensenames = new CharSequence[(expenses.size())];
            for (int k = 0; k < expenses.size(); k++) {
                Expense expense = (Expense) expenses.get(k);
                expensenames[k] = expense.expensename;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pick An Expense");
            builder.setItems(expensenames, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    selectedexpense = expensenames[i].toString();
                    for (int m = 0; m < expenses.size(); m++) {
                        Expense expense = (Expense) expenses.get(m);
                        if (selectedexpense.equals(expense.expensename.toString())) {
                            txtboxname.setText(expense.expensename.toString());
                            int spinnerPosition = adapter.getPosition(expense.expensecategory);
                            spcategory.setSelection(spinnerPosition);
                            txtboxamount.setText(expense.amount);
                            txtboxdate.setText(expense.date.toString());
                            imageview.setImageURI(Uri.parse(expense.recptimg));
                            spcategory.setEnabled(false);

                            editedindex = m;

                        }

                    }
                }
            });

            final AlertDialog alert = builder.create();
            findViewById(R.id.btnselectexpense).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (expenses.size()==0) {
                        Toast.makeText(DeleteExpense.this,"Sorry there are no expenses to be dispalyed.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        alert.show();
                    }

                }
            });

        }




    }


    public void deleteExpense(View view) {
        if (selectedexpense.length() == 0 || selectedexpense.equals("") ) {
            Toast.makeText(this, "Please select a valid expense to proceed further.", Toast.LENGTH_SHORT).show();


        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert!!!");
            builder.setMessage("Are you sure about deleting the expense?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int k) {
                    expenses.remove(editedindex);
                    Intent i = new Intent();
                    i.putExtra(AppActivity.VALUE_KEY, expenses);
                    setResult(RESULT_OK, i);
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

    public void cancelActivity(View view) {
        finish();
    }
}



*/
}
