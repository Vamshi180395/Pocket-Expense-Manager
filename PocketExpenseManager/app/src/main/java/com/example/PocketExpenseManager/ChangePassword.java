package com.example.PocketExpenseManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
TextView oldpassword,newpassword,confirmnewpassword;
    Button btnchangepassword,btncncel;
    User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        findViewByIDs();
        if (getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable("User");
        }
        btnchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if(oldpassword.getText().toString().length()==0 || newpassword.getText().toString().length()==0 || confirmnewpassword.getText().toString().length()==0) {
    Toast.makeText(ChangePassword.this, "Please ensure all the values are entered to proceed further.", Toast.LENGTH_SHORT).show();
}
else if(!newpassword.getText().toString().equals(confirmnewpassword.getText().toString())){
    Toast.makeText(ChangePassword.this, "Please ensure both the new passwords are same to proceed further.", Toast.LENGTH_SHORT).show();
}
else{
    if(user!=null) {
        if (oldpassword.getText().toString().equals(user.getPassword().toString())) {
            user.setPassword(newpassword.getText().toString());
            DatabaseDataManager dbmanager;
            dbmanager = new DatabaseDataManager(ChangePassword.this);
            dbmanager.updateUser(user);
            Toast.makeText(ChangePassword.this, "Password changed successfully!!!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(ChangePassword.this, "Sorry the password you have entered is incorrect. Please enter a valid one.", Toast.LENGTH_SHORT).show();
        }
    }
    else{
        Toast.makeText(ChangePassword.this, "Failed to change your password. Please try again after sometime", Toast.LENGTH_SHORT).show();
    }
}
            }
        });
btncncel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});
    }

    private void findViewByIDs() {
        oldpassword= (EditText) findViewById(R.id.txtboxoldpassword);
        newpassword= (EditText) findViewById(R.id.txtboxnewpassword);
        confirmnewpassword= (EditText)findViewById(R.id.txtboxconfirmnewpassword);
        btnchangepassword=(Button)findViewById(R.id.btnchangepassword);
        btncncel=(Button)findViewById(R.id.btncancel);
    }


}
