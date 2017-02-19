package com.example.rommo_000.smartpark;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MakeAccount extends AppCompatActivity {

    String email;
    String password;
    String passwordConfrimation;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);


        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        final TextInputLayout passwordConfrimationWrapper = (TextInputLayout) findViewById(R.id.passwordConfirmationWrapper);
        button = (Button) findViewById(R.id.SubmitButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = usernameWrapper.getEditText().getText().toString();
                password = passwordWrapper.getEditText().getText().toString();
                passwordConfrimation = passwordConfrimationWrapper.getEditText().getText().toString();

                if(password != passwordConfrimation)
                {
                    //Toast.makeText(this, "Password Confirmation", Toast.LENGTH_LONG).show();
                    passwordWrapper.setError("Passwords Do Not Match");
                    passwordConfrimationWrapper.setError("Passwords Do Not Match");
                }
                else
                {
                    //Toast.makeText(this, "Great Job Buddy", Toast.LENGTH_LONG).show();
                }
            }
        });
        //email =


    }
}
