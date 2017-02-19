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
/**********************************************************************************
 //We can worry about validation at a later time.
 if (!username.equals(uName)) {
 usernameWrapper.setError("Not a valid email address!");
 } else if (!password.equals(pWord)) {
 passwordWrapper.setError("Not a valid password!");
 } else {
 usernameWrapper.setErrorEnabled(false);
 passwordWrapper.setErrorEnabled(false);
 *****************************************************************************^*****/
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
                        try{
                            String token = response.get("result").toString();
                            //This is our new token that we will use to access resources on the server.
                            //It can be stored in shared preferences with this code:
                            final String MY_PREFS_NAME = "MyPrefsFile";
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("token", token);
                            editor.apply();
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                        Intent i = new Intent(getApplicationContext(), SScreen.class);
                        startActivity(i);
                        //*****[TAKE USER TO HOME SCREEN]
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        passwordWrapper.setError("Invalid username or password!");
                        //*****[DISPLAY ERROR SCREEN]
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

/*************************************************
 * Intent i = new Intent(getApplicationContext(), LogIn.class);
 startActivity(i);
 *
 *
 *     String uName = "seth@wilson.com";
 String pWord = "1111";

    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "response :" + response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
        }
    }){
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", "max");
            params.put("password", "123456");
            return params;
        }
    };
*************************************************/





/******************
 private void hideKeyboard()
 {
 View view = getCurrentFocus();
 if (view != null)
 {
 ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
 hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
 }
 }




 **************************
 private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
 private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
 private Matcher matcher;


 public boolean validateEmail(String email) {
 matcher = pattern.matcher(email);
 return matcher.matches();
 }
 ********************/



