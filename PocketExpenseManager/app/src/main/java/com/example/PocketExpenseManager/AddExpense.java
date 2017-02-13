package com.example.PocketExpenseManager;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
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
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;

public class AddExpense extends AppCompatActivity {
    EditText txtboxname, txtboxamt, txtboxdate;
    Spinner spinner;
    Uri imageuri;
    String recpimg;
    String expensecurrency;
    private TextView tvDisplayDate;
    ImageView imageView ;
    private int year;
    private int month;
    private int day;
    ImageView preview;
    static final int DATE_DIALOG_ID = 999;
    String expensename, expensecategory, date;
    Uri outputFileUri;
    static final int RESULT_LOAD_IMAGE=1;
    User user=new User();
    String useremail;
    TextView currecysymbol;
DatabaseDataManager dbmanager;
    String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        txtboxname = (EditText) findViewById(R.id.txtboxexpensename);
        txtboxamt=(EditText) this.findViewById(R.id.txtboxamountf);
        txtboxdate = (EditText) findViewById(R.id.txtboxdate);
        imageView = (ImageView) findViewById(R.id.imageView6);
        spinner = (Spinner) findViewById(R.id.spinner);
        currecysymbol=(TextView)findViewById(R.id.currency);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setCurrentDateOnView();
        imageuri=Uri.parse("android.resource://com.example.hw2/drawable/ebipg");
        if (getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable("User");
            useremail=user.getEmail().toString();
        }
        currecysymbol.setText("$");
        currecysymbol.setBackgroundColor(Color.parseColor("#F0F8FF"));
        currecysymbol.setOnClickListener(new View.OnClickListener() {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddExpense.this);
                builder.setTitle("Select a currency");
                builder.setItems(currencylist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int l=0;l<availableCurrenciesList.size();l++){
                            if(currencylist[i].toString().equals(availableCurrenciesList.get(l).getDisplayName().toString()+" - "+availableCurrenciesList.get(l).getSymbol(Locale.getDefault()).toString())){
                                Currency selectedcurrency=availableCurrenciesList.get(l);
                               expensecurrency = selectedcurrency.getSymbol(Locale.getDefault());
                                currecysymbol.setText(selectedcurrency.getSymbol(Locale.getDefault()));
                            }
                        }
                    }
                });
                final AlertDialog alert=builder.create();
                alert.show();
            }
        });

    }

    public void setCurrentDateOnView() {

        tvDisplayDate = (TextView) findViewById(R.id.txtboxdate);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        tvDisplayDate.setText(new StringBuilder()

                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);
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

    public void addExpense(View view) {
        expensename = txtboxname.getText().toString();
       expensecategory=spinner.getSelectedItem().toString();
        amount = (txtboxamt.getText().toString());
        date = txtboxdate.getText().toString();
        recpimg= imageuri.toString();
        if (expensename.length() == 0 || expensecategory.length() == 0 || date.length() == 0 || amount.length()==0) {
            setResult(RESULT_CANCELED);
            Toast.makeText(this, "Please enter all the values to create an expense", Toast.LENGTH_SHORT).show();
        }
        else  if(spinner.getSelectedItem().toString().equals("Select a category"))
        {
            Toast.makeText(this, "Please select a valid expense category to proceed further.", Toast.LENGTH_SHORT).show();

        }
        else {
            Expense expense = new Expense(expensename, expensecategory, date, amount, currecysymbol.getText().toString(),recpimg);
            Intent in = new Intent();
            in.putExtra(AppActivity.VALUE_KEY, expense);
            setResult(RESULT_OK, in);
            finish();
        }


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
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null) {
             imageuri = data.getData();
            imageView.setImageURI(imageuri);
        }

    }
}

