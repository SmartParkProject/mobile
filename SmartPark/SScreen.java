package com.example.rommo_000.smartpark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.lang.String;

public class SScreen extends AppCompatActivity {

    Networking obj;
    String phoneId = obj.uID;
    String url2 = obj.urlMain + "parking/status";
    final Random rnd = new Random();
    String spotCheckedInto;
    String timeCheckedInto;
    int activity = 1;
    Account_Verification accObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sscreen);


        ImageView img = (ImageView) findViewById(R.id.imageView);
        int imgToDisplay = rnd.nextInt(6);
        //img.setImageResource(R.drawable.parkinggarage4);



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

        final String MY_PREFS_NAME = "MyPrefsFile"; //This should probably be changed to a global variable somewhere.
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (true || token == null) {
            //no token stored, so prompt user for login.
            Intent i = new Intent(getApplicationContext(), Account.class);
            startActivity(i);
            return;
        }

        //Data for the request.
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

                    //accObj.setSpotTime(spotCheckedInto,timeCheckedInto);



                } catch (JSONException e) {
                    e.printStackTrace();


                }catch(ParseException e){
                    e.printStackTrace();
                    // textView.setText("Error");
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                //openSpots.setText("Error Communicating With Server");
            }
        });
        Networking.getInstance(this).addToRequestQueue(jsObjRequest);


        //accObj.setSpotTime(spotCheckedInto,timeCheckedInto);



        Thread myThread = new Thread(){
            @Override
        public void run()
            {
                try {
                    sleep(1000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("parkingSpot", spotCheckedInto);
                    intent.putExtra("parkingTime", timeCheckedInto);
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

/********************************8
 *
 * private static int SPLASH_TIME_OUT = 3000;
 new Handler().postDelayed(new Runnable()
 {
 @Override
 public void run()
 {
 Intent intent = new Intent(SScreen.this, MainActivity.class);
 startActivity(intent);
 finish();
 }
 }, SPLASH_TIME_OUT);


 img.setImageDrawable
 (
 getResources().getDrawable(getResourceID(str, "drawable",getApplicationContext()))
 );

 protected final static int getResourceID(final String resName, final String resType, final Context ctx)
 {
 final int ResourceID =
 ctx.getResources().getIdentifier(resName, resType,
 ctx.getApplicationInfo().packageName);
 if (ResourceID == 0)
 {
 throw new IllegalArgumentException
 (
 "No resource string found with name " + resName
 );
 }
 else
 {
 return ResourceID;
 }
 }


 String imagetoshow = "parkinggarage" + imgToDisplay;

 int id = getResources().getIdentifier(imagetoshow, "drawable", getPackageName());
 img.setImageResource(id);


 *********************************/