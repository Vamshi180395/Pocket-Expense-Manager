package com.example.PocketExpenseManager;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

 EditText userid,password;
    FromLoginFragment iexapp;
    Button btnlogin,btnexit;
    TextView gotoregister;
    SignInButton googlesigninbutton;
    ImageView sendemailforforgotpassword;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            iexapp = (FromLoginFragment) activity;

        }
        catch (ClassCastException e){

        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userid= (EditText) getActivity().findViewById(R.id.userid);
        password= (EditText) getActivity().findViewById(R.id.password);
        gotoregister= (TextView) getActivity().findViewById(R.id.txtgotoregister);
        gotoregister.setMovementMethod(LinkMovementMethod.getInstance());
        btnlogin=(Button) getActivity().findViewById(R.id.btnlogin);
        btnexit=(Button) getActivity().findViewById(R.id.btnexit);
        googlesigninbutton=(SignInButton) getActivity().findViewById(R.id.googlesignin);
        sendemailforforgotpassword=(ImageView) getActivity().findViewById(R.id.forgotpasswordimage);
        gotoregister.setText(Html.fromHtml("<a href=\"\">Not yet a member? -> Register Today !!!</a> "));
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userid.getText().toString().length()!=0 && password.getText().toString().length()!=0) {
                    iexapp.goToHomePage(userid.getText().toString(),password.getText().toString());
                }
                else{
                    Toast.makeText(getActivity(),"Please ensure userid and password are entered to proceed further.",Toast.LENGTH_SHORT).show();

                }
            }
        });
        googlesigninbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iexapp.signInWithGoogle();
            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               iexapp. exitApp();
            }
        });
        sendemailforforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iexapp.sendEmailForForgotPassword();
            }
        });

gotoregister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        iexapp.goToRegisterFragment();
    }
});
    }

    public interface FromLoginFragment{
        void goToHomePage(String email,String password);
        void exitApp();
        void goToRegisterFragment();
        void sendEmailForForgotPassword();
void signInWithGoogle();
    }

}
