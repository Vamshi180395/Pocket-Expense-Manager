package com.example.PocketExpenseManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoginFragment.FromLoginFragment,RegisterFragment.FromRegisterFragment,GoogleApiClient.OnConnectionFailedListener,ForgotPasswordFragment.FromForgotPasswordFragment{
DatabaseDataManager dbmanager;
    GoogleApiClient mgoogleclientapi;
    public static final int RC_SIGN_IN=9001;
    public static final String TAG="SignInActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("Pocket Expense Manager");
        getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment(), "loginapp").commit();
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mgoogleclientapi=new GoogleApiClient.Builder(this).enableAutoManage(this, MainActivity.this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
    }


    @Override
    public void goToHomePage(String email,String password) {
        dbmanager=new DatabaseDataManager(this);
        User user=new User();
       user = dbmanager.getUser(email);
        if(user!=null && user.password.equals(password)){
            Intent i=new Intent(MainActivity.this,AppActivity.class);
            i.putExtra("User",user);
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(MainActivity.this, "The UserId or Password you have entered is incorrect. Please enter valid details to proceed further.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void exitApp() {
finish();
    }

    @Override
    public void goToRegisterFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new RegisterFragment(), "registerapp").addToBackStack(null).commit();
    }

    @Override
    public void sendEmailForForgotPassword() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ForgotPasswordFragment(), "forgotpasswordapp").addToBackStack(null).commit();

    }

    @Override
    public void signInWithGoogle() {
            Intent signinintent=Auth.GoogleSignInApi.getSignInIntent(mgoogleclientapi);
            startActivityForResult(signinintent,RC_SIGN_IN);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==RC_SIGN_IN){
                GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSIgnInResult(result);
            }
        }

    private void handleSIgnInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            User user=new User(account.getDisplayName().toString(),account.getEmail().toString(),account.getId().toString());
            dbmanager=new DatabaseDataManager(this);
            dbmanager.saveUser(user);
            Intent i=new Intent(MainActivity.this,AppActivity.class);
            i.putExtra("User",user);
            startActivity(i);
            finish();
        }
        else{

        }
    }

    @Override
    public void registerAsUser(String name,String email,String password) {
        User user=new User(name,email,password);
        dbmanager=new DatabaseDataManager(this);
        dbmanager.saveUser(user);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new LoginFragment(), "loginapp").addToBackStack(null).commit();
        Toast.makeText(MainActivity.this, "Registration Succesfull!!! Please login with your credentials to proceed further.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void goToLogin() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new LoginFragment(), "loginapp").addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.d("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void gotologinFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new LoginFragment(), "loginapp").addToBackStack(null).commit();
    }
}
