package com.example.rommo_000.smartpark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

//import org.apache.http.HttpStatus;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.lang.String;

import static java.security.AccessController.getContext;

public class SScreen extends AppCompatActivity {

    Networking obj;
    String phoneId = obj.uID;
    String url2 = obj.urlMain + "parking/status";
    final Random rnd = new Random();
    String spotCheckedInto;
    String timeCheckedInto;
    String numSpotsAvailible;
    String lotId;
    int activity = 1;
    Account_Verification accObj;
    Boolean status = true;
    int lotCheckedInto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sscreen);

        ImageView img = (ImageView) findViewById(R.id.imageView);
        int imgToDisplay = rnd.nextInt(6);

        switch (imgToDisplay)
        {
            case 0: img.setImageResource(R.drawable.parkinggarage1);
                break;
            case 1: img.setImageResource(R.drawable.parkinggarage2);
                break;
            case 2: img.setImageResource(R.drawable.parkinggarage3);
                break;
            case 3: img.setImageResource(R.drawable.parkinggarage4);
                break;
            case 4: img.setImageResource(R.drawable.parkinggarage5);
                break;
            case 5: img.setImageResource(R.drawable.parkinggarage6);
                break;
            default: img.setImageResource(R.drawable.splogo);
                break;
        }
        final String MY_PREFS_NAME = "MyPrefsFile";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            Intent i = new Intent(getApplicationContext(), Account.class);
            startActivity(i);
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("token", token);
        }catch(JSONException e){
            e.printStackTrace();
        }



        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url2, data, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject resp = response.getJSONObject("result");
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

                    spotCheckedInto = resp.getString("spot");
                    Date date = formatter.parse(resp.getString("reserve_time").replaceAll(".000Z$", "+0000"));
                    timeCheckedInto = date.toString();
                    lotCheckedInto = resp.getInt("LotId");
                    Log.e("spotCheckedInto", "SSCREEN SpotCheckedInto " + spotCheckedInto);
                    Log.e("lotCheckedInto", "SSCREEN lotCheckedInto " + lotCheckedInto);

                } catch (JSONException e) {
                    e.printStackTrace();


                }catch(ParseException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // TODO Auto-generated method stub
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    Intent i = new Intent(getApplicationContext(), Account.class);
                    startActivity(i);
                    return;
                }else{

                }
            }

        });
        Networking.getInstance(this).addToRequestQueue(jsObjRequest);

        Thread myThread = new Thread(){
            @Override
            public void run()
            {
                try {
                    sleep(1000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("parkingSpot", spotCheckedInto);
                    intent.putExtra("parkingTime", timeCheckedInto);
                    intent.putExtra("LotId", lotCheckedInto);
                    intent.putExtra("cameFrom", activity);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        myThread.start();




    }


}

