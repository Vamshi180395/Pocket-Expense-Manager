package com.example.PocketExpenseManager;

/**
 * Created by Rama Vamshi Krishna on 11/5/2016.
 */
public class TempEdit{}/* {extends AppCompatActivity

    {ArrayList expenses = new ArrayList();
        ArrayList alldatabaseexpenses = new ArrayList();
        CharSequence[] expensenames;
        String selectedexpense="";
        EditText txtboxname,txtboxamount,txtboxdate;
        Spinner spcategory;
        int editedindex;
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

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        txtboxname=(EditText) findViewById(R.id.txtboxexpensename);
        txtboxamount=(EditText) findViewById(R.id.txtboxamountf);
        txtboxdate=(EditText) findViewById(R.id.txtboxdate);
        tvDisplayDate = (TextView) findViewById(R.id.txtboxdate);
        imageview = (ImageView) findViewById(R.id.imageView6);
        imageuri=Uri.parse("android.resource://com.example.hw2/drawable/ebipg");
        spcategory=(Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcategory.setAdapter(adapter);

        if (getIntent().getExtras() != null) {
            expenses = getIntent().getExtras().getParcelableArrayList(AppActivity.EXPENSES_ARRAY);
            alldatabaseexpenses= getIntent().getExtras().getParcelableArrayList("AllDataBaseExpensesList");
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
                            txtboxdate.setText(expense.date.toString());
                            imageview.setImageURI(Uri.parse(expense.recptimg));
                            imageuri=(Uri.parse(expense.recptimg));
                            editedindex=m;


                        }

                    }
                }
            });

            final AlertDialog alert=builder.create();
            findViewById(R.id.btnselectexpense).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (expenses.size()==0){
                        Toast.makeText(EditExpense.this,"Sorry there are no expenses to be dispalyed.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        alert.show();
                    }

                }
            });

        }



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
        if(selectedexpense.length()==0 || selectedexpense.equals("")){
            Toast.makeText(this, "Please select a valid expense to proceed further.", Toast.LENGTH_SHORT).show();


        }
        else {
            Expense expense = (Expense) expenses.get(editedindex);
            for(int i=0; i<alldatabaseexpenses.size();i++){
                Expenses expenses=(Expenses) alldatabaseexpenses.get(i);
                if(expenses.getExpensename().equals(expense.expensename) && expenses.getExpensecategory().equals(expense.expensecategory) && expenses.getDate().equals(expense.date) &&expenses.getAmount().equals(expense.amount) &&expenses.getRecptimg().equals(expense.recptimg)){
                    expenses.setExpensename(txtboxname.getText().toString());
                    expenses.setExpensecategory(spcategory.getSelectedItem().toString());
                    expenses.setDate(txtboxdate.getText().toString());
                    expenses.setAmount(txtboxamount.getText().toString());
                    expenses.setRecptimg(imageuri.toString());
                    dbmanager=new DatabaseDataManager(this);
                    dbmanager.updateExpenses(expenses);
                }
            }
            expense.expensename = txtboxname.getText().toString();
            expense.expensecategory = spcategory.getSelectedItem().toString();
            expense.amount = (txtboxamount.getText().toString());
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



*/
