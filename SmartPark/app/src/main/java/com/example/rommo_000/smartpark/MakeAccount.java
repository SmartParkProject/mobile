package com.example.rommo_000.smartpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MakeAccount extends AppCompatActivity {

    String username;
    String password;
    String passwordConfrimation;
    //Button button;
    MakeAccount self = this;
    Networking obj;
    String url = obj.urlMain + "account/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);


        final TextView usernameWrapper = (TextView) findViewById(R.id.username);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        final TextInputLayout passwordConfrimationWrapper = (TextInputLayout) findViewById(R.id.passwordConfirmationWrapper);
        Button button = (Button) findViewById(R.id.SubmitButton);

        usernameWrapper.setHint("Enter Username");
        passwordWrapper.setHint("Enter Password");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameWrapper.getText().toString();
                password = passwordWrapper.getEditText().getText().toString();
                passwordConfrimation = passwordConfrimationWrapper.getEditText().getText().toString();



                    JSONObject data = new JSONObject();
                    try {
                        data.put("username", username);
                        data.put("password", password);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    //JSON POST request
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            //Successfully logged in.


                            Intent i = new Intent(getApplicationContext(), SScreen.class);
                            startActivity(i);
                            //*****[TAKE USER TO HOME SCREEN]
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //usernameWrapper.setError("Invalid Username");
                            passwordWrapper.setError("Invalid Username or Password!");
                            //*****[DISPLAY ERROR SCREEN]
                        }
                    });
                    Networking.getInstance(self).addToRequestQueue(jsObjRequest);

            }
        });



    }
}
/****************************************************
 if(password != passwordConfrimation)
 {
 //Toast.makeText(this, "Password Confirmation", Toast.LENGTH_LONG).show();
 passwordWrapper.setError("Passwords Do Not Match");
 passwordConfrimationWrapper.setError("Passwords Do Not Match");
 }
 else*/