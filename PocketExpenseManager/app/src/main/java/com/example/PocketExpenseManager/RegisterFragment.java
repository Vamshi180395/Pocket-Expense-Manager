package com.example.PocketExpenseManager;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
Button btnregister,btncancel;
    EditText name,email,password,confirmpassword;
    FromRegisterFragment iexapp;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            iexapp = (FromRegisterFragment) activity;

        }
        catch (ClassCastException e){

        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name= (EditText) getActivity().findViewById(R.id.txtboxname);
        email= (EditText) getActivity().findViewById(R.id.txtboxemail);
        password= (EditText) getActivity().findViewById(R.id.txtboxpassword);
        confirmpassword= (EditText) getActivity().findViewById(R.id.txtboxconfirmpassword);
        btnregister=(Button) getActivity().findViewById(R.id.btnregister);
        btncancel=(Button) getActivity().findViewById(R.id.btncancel);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().length()!=0 && email.getText().toString().length()!=0 && password.getText().toString().length()!=0 && confirmpassword.getText().toString().length()!=0){
                    if(password.getText().toString().equals(confirmpassword.getText().toString())){
                        iexapp.registerAsUser(name.getText().toString(),email.getText().toString(),password.getText().toString());
                    }
                    else {
                        Toast.makeText(getActivity(),"Please ensure both the entered passwords are similar to proceed further.",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Please enter all the details to proceed further",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iexapp. goToLogin();
            }
        });

    }

    public interface FromRegisterFragment{
        void registerAsUser(String name,String email,String password);
        void goToLogin();

    }

}
