package com.example.PocketExpenseManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;

public class EditExpense extends AppCompatActivity {
    ArrayList expenses = new ArrayList();
    ArrayList alldatabaseexpenses = new ArrayList();
    CharSequence[] expensenames;
    String selectedexpense="";
    EditText txtboxname,txtboxamount,txtboxdate;
    Spinner spcategory;
    int editedindex;
    TextView txtboxexpensecurrency;
    private TextView tvDisplayDate;
    private DatePicker dpResult;
    private int year;
    private int month;
    private int day;
    ArrayAdapter<CharSequence> adapter;
    ImageView imageview;
    static final int DATE_DIALOG_ID = 999;
    final static int RESULT_LOAD_IMAGE=1;
    Uri imageuri;
    DatabaseDataManager dbmanager;
    Expense selectedexpenseforediting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        txtboxname=(EditText) findViewById(R.id.txtboxexpensename);
        txtboxamount=(EditText) findViewById(R.id.txtboxamountf);
        txtboxdate=(EditText) findViewById(R.id.txtboxdate);
        tvDisplayDate = (TextView) findViewById(R.id.txtboxdate);
        imageview = (ImageView) findViewById(R.id.imageView6);
        txtboxexpensecurrency=(TextView) findViewById(R.id.expensecurrency);
        imageuri=Uri.parse("android.resource://com.example.hw2/drawable/ebipg");
        txtboxexpensecurrency.setBackgroundColor(Color.parseColor("#F0F8FF"));
spcategory=(Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcategory.setAdapter(adapter);

        if (getIntent().getExtras() != null) {
            expenses = getIntent().getExtras().getParcelableArrayList(AppActivity.EXPENSES_ARRAY);
            alldatabaseexpenses= getIntent().getExtras().getParcelableArrayList("AllDataBaseExpensesList");
            selectedexpenseforediting= (Expense)getIntent().getExtras().getSerializable("SelectedExpenseForEditing");
            txtboxname.setText(selectedexpenseforediting.expensename.toString());
            int spinnerPosition = adapter.getPosition(selectedexpenseforediting.expensecategory);
            spcategory.setSelection(spinnerPosition);
            txtboxamount.setText(selectedexpenseforediting.amount);
            txtboxexpensecurrency.setText(selectedexpenseforediting.expensecurrency);
            txtboxdate.setText(selectedexpenseforediting.date.toString());
            imageview.setImageURI(Uri.parse(selectedexpenseforediting.recptimg));
            imageuri=(Uri.parse(selectedexpenseforediting.recptimg));
            expensenames=new CharSequence[(expenses.size())];
            for (int k = 0; k < expenses.size(); k++) {
                Expense expense = (Expense) expenses.get(k);
                expensenames[k]=expense.expensename;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pick An Expense");
            builder.setItems(expensenames, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    selectedexpense= expensenames[i].toString();
                    for (int m = 0; m < expenses.size(); m++) {
                        Expense expense = (Expense) expenses.get(m);
                        if (selectedexpense.equals(expense.expensename.toString())){
                            txtboxname.setText(expense.expensename.toString());
                            int spinnerPosition = adapter.getPosition(expense.expensecategory);
                            spcategory.setSelection(spinnerPosition);
                            txtboxamount.setText(expense.amount);
                            txtboxexpensecurrency.setText(expense.expensecurrency);
                            txtboxdate.setText(expense.date.toString());
                            imageview.setImageURI(Uri.parse(expense.recptimg));
                            imageuri=(Uri.parse(expense.recptimg));
                            editedindex=m;


                        }

                    }
                }
            });

        final AlertDialog alert=builder.create();


        }

        txtboxexpensecurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<Currency> availableCurrenciesList;
                Set<Currency> availableCurrenciesSet = Currency.getAvailableCurrencies();
                availableCurrenciesList = new ArrayList<Currency>(availableCurrenciesSet);
                final ArrayList<String> currencynames=new ArrayList<String>();
                for(int m=0;m<availableCurrenciesList.size();m++){
                    currencynames.add(availableCurrenciesList.get(m).getDisplayName()+" - "+availableCurrenciesList.get(m).getSymbol(Locale.getDefault()).toString());
                }
                final CharSequence[] currencylist = currencynames.toArray(new CharSequence[currencynames.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditExpense.this);
                builder.setTitle("Select a currency");
                builder.setItems(currencylist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int l=0;l<availableCurrenciesList.size();l++){
                            if(currencylist[i].toString().equals(availableCurrenciesList.get(l).getDisplayName().toString()+" - "+availableCurrenciesList.get(l).getSymbol(Locale.getDefault()).toString())){
                                Currency selectedcurrency=availableCurrenciesList.get(l);
                                String expensecurrency = selectedcurrency.getSymbol(Locale.getDefault());
                                txtboxexpensecurrency.setText(selectedcurrency.getSymbol(Locale.getDefault()));
                            }
                        }
                    }
                });
                final AlertDialog alert=builder.create();
                alert.show();
            }
        });

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

    public void setDate(View view) {

        showDialog(DATE_DIALOG_ID);
    }


    public void saveNewValues(View view) {
            for(int k=0;k<expenses.size();k++){
                if(expenses.get(k).equals(selectedexpenseforediting)){
                   editedindex=k;
                }
            }
            Expense expense = selectedexpenseforediting;
            for(int i=0; i<alldatabaseexpenses.size();i++){
                Expenses expenses=(Expenses) alldatabaseexpenses.get(i);
                if(expenses.getExpensename().equals(expense.expensename) && expenses.getExpensecategory().equals(expense.expensecategory) && expenses.getDate().equals(expense.date) &&expenses.getAmount().equals(expense.amount) && expenses.getExpensecurrency().equals(expense.expensecurrency) && expenses.getRecptimg().equals(expense.recptimg)){
                    expenses.setExpensename(txtboxname.getText().toString());
                    expenses.setExpensecategory(spcategory.getSelectedItem().toString());
                    expenses.setDate(txtboxdate.getText().toString());
                    expenses.setAmount(txtboxamount.getText().toString());
                    expenses.setExpensecurrency(txtboxexpensecurrency.getText().toString());
                    expenses.setRecptimg(imageuri.toString());
                    dbmanager=new DatabaseDataManager(this);
                    dbmanager.updateExpenses(expenses);
                }
            }
            expense.expensename = txtboxname.getText().toString();
            expense.expensecategory = spcategory.getSelectedItem().toString();
            expense.amount = (txtboxamount.getText().toString());
        expense.expensecurrency=(txtboxexpensecurrency.getText().toString());
            expense.date = txtboxdate.getText().toString();
            expense.recptimg = imageuri.toString();

            if (expense.expensename.length() == 0 || expense.expensecategory.length() == 0 || expense.date.length() == 0 || expense.amount.length() == 0) {


                Toast.makeText(this, "Please enter all values to proceed further", Toast.LENGTH_SHORT).show();

            } else if (spcategory.getSelectedItem().toString().equals("Select a category")) {
                Toast.makeText(this, "Please select a valid expense category to proceed further.", Toast.LENGTH_SHORT).show();

            } else {

                expenses.set(editedindex, expense);
                Intent i = new Intent();
                i.putExtra(AppActivity.VALUE_KEY, expenses);
                setResult(RESULT_OK, i);
                finish();
            }

    }

    public void cancelActivity(View view) {
        finish();
    }

    public void setImage(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // File pictureDirectory=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // String picturedirectorypath=pictureDirectory.getPath();
        // Uri data=Uri.parse(picturedirectorypath);
        // i.setDataAndType(data,"image/*");
        startActivityForResult(i, RESULT_LOAD_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            imageuri = data.getData();
            imageview.setImageURI(imageuri);

        }


    }
}




