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

public class ForgotPasswordFragment extends Fragment {

EditText emailtosendmail;
    FromForgotPasswordFragment iexapp;
    Button Sendmailbutton;
    TextView gotologin;
    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            iexapp = (FromForgotPasswordFragment) activity;

        }
        catch (ClassCastException e){

        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        emailtosendmail= (EditText) getActivity().findViewById(R.id.emailtosendmail);
        Sendmailbutton=(Button) getActivity().findViewById(R.id.sendmailbutton);
        gotologin=(TextView) getActivity().findViewById(R.id.gotologin);
        gotologin.setText(Html.fromHtml("<a href=\"\">Just remembered your password? -> Go to Login </a> "));
        Sendmailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailtosendmail.getText().toString().length()!=0) {
                    iexapp.sendEmail(emailtosendmail.getText().toString());
                }
                else{
                    Toast.makeText(getActivity(),"Please ensure the email address is entered to proceed further.",Toast.LENGTH_SHORT).show();

                }
            }
        });
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iexapp.gotologinFragment();
            }
        });
    }
    public interface FromForgotPasswordFragment{
        void sendEmail(String email);
        void gotologinFragment();
    }

}
