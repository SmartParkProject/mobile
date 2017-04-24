package com.example.rommo_000.smartpark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Account extends AppCompatActivity{

    Button lbutton;
    Button makeAccount;
    String username;
    String password;
    Networking obj;
    String tag_string_req = "string_req";
    String url = obj.urlMain + "account/login";
    Account self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);

        usernameWrapper.setHint("Enter Username");
        passwordWrapper.setHint("Enter Password");

        makeAccount = (Button) findViewById(R.id.MakeAccount);
        lbutton = (Button) findViewById(R.id.loginButton);

        lbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
                username = usernameWrapper.getEditText().getText().toString();
                password = passwordWrapper.getEditText().getText().toString();

                JSONObject data = new JSONObject();
                try {
                    data.put("username", username);
                    data.put("password", password);
                }catch(JSONException e){
                    e.printStackTrace();
                }

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        //Successfully logged in.
                        try{
                            String token = response.get("result").toString();
                            final String MY_PREFS_NAME = "MyPrefsFile";
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("token", token);
                            editor.apply();
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                        Intent i = new Intent(getApplicationContext(), SScreen.class);
                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        passwordWrapper.setError("Invalid username or password!");
                    }
                });
                Networking.getInstance(self).addToRequestQueue(jsObjRequest);
            }
        });
    }

    public void gotoMakeAccount(View view)
    {
        Intent intent = new Intent(this, MakeAccount.class);
        startActivity(intent);
    }

}
