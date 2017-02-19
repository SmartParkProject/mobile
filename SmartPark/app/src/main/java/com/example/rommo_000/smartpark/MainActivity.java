package com.example.rommo_000.smartpark;


import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;
import java.io.UnsupportedEncodingException;


import com.android.volley.*;

import org.json.JSONObject;
import org.json.*;
import org.json.JSONObject;

import com.android.volley.toolbox.JsonObjectRequest;


public class MainActivity extends AppCompatActivity {


    TextView myText;
    TextView textView;


    Networking obj;
    String phoneId = obj.uID;
    String url = obj.urlMain + "parking/";
    String url2 = obj.urlMain + "payment/checkout";
    String trial;
    NfcAdapter nfcAdapter;
    //String Unique_Id =  android.telephony.TelephonyManager.getDeviceId();
    String spot;
    int spotNum;

    String ParkingSpot;
    String ParkingTime;
    int cameFrom;
    Button butt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myText = (TextView) findViewById(R.id.myText);
        textView = (TextView) findViewById(R.id.textView);
        butt = (Button) findViewById(R.id.pmtButton);

        if(getIntent().getExtras().getInt("cameFrom") != 0)
        {
            ParkingSpot = getIntent().getExtras().getString("parkingSpot");
            ParkingTime = getIntent().getExtras().getString("parkingTime");
            if(ParkingSpot != null)
            {

                myText.setText("You are checked into spot " + ParkingSpot);
                textView.setText(ParkingTime);
            }
            else
            {
                myText.setText("Welcome To SmartPark!");
                textView.setText("Swipe To Check In");
                butt.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            myText.setText("Welcome To SmartPark!");
            textView.setText("Swipe To Check In");
            butt.setVisibility(View.INVISIBLE);
        }

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null || !nfcAdapter.isEnabled())
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!isFinishing()){
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("NFC Not Enabled!")
                                .setMessage("Please enable NFC on your phone so that SmartPark can function correctly.\nThanks and have a great day!")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Whatever...
                                    }
                                }).create().show();
                    }
                }
            });
        }
       
    }


    //*************** on NFC swipe
    @Override
    protected void onNewIntent(Intent intent)
    {



//****************************Getting spot number;
        Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if(parcelables != null && parcelables.length > 0)
        {
            readTextFromMessage((NdefMessage)parcelables[0]);
        }
        else
        {
            Toast.makeText(this,"No NDEF LABELS FOUND", Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(this, "Device: " /*+ Unique_Id*/ +  " checked in at: " + currentLocalTime + " Into spot: " + spot, Toast.LENGTH_LONG).show();



//****************************posting via json

        //Grabbing the token
        final String MY_PREFS_NAME = "MyPrefsFile"; //This should probably be changed to a global variable somewhere.
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            //no token stored, prompt user for login.
            return;
        }
//Data for the request.
        JSONObject data = new JSONObject();
        try {
            data.put("token", token);
            data.put("spot", spotNum);
            data.put("reserve_length", 12);
        }catch(JSONException e){
            e.printStackTrace();
        }
//The actual request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                Intent i = new Intent(getApplicationContext(), CheckInConfirmation.class);
                i.putExtra("parkingSpot", spot);

                startActivity(i);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Something was wrong with the request
                try {
                    //Formatting the response
                    String content_type = error.networkResponse.headers.get("Content-Type"); //Maybe use to make sure we received a json response.
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject jsonObject = new JSONObject(responseBody); //This should contain the json response data
                    //myText.setText("Parking Spot " + spot + " Unavailible");

                    //use jsonObject.toString() for output
                } catch (JSONException e) {
                    //Handle a malformed json response
                } catch (UnsupportedEncodingException e){

                }
            }
        });

        Networking.getInstance(this).addToRequestQueue(jsObjRequest);





//Toast.makeText(this, "Spot number: " + spot + " phoneId " + phoneId, Toast.LENGTH_LONG).show();

        super.onNewIntent(intent);
    }
//****************Method for reading text from nfc
    private void readTextFromMessage(NdefMessage ndefMessage)
    {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();
        if(ndefRecords != null && ndefRecords.length > 0)
        {
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);

           // spot.setText(tagContent);
            spot = tagContent;
            spotNum = Integer.parseInt(spot);
        }
        else
        {
            Toast.makeText(this, "No Records", Toast.LENGTH_LONG).show();
        }
    }
//get text from tag
    private String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String tagContent = null;
        try
        {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1, payload.length - languageSize - 1, textEncoding);
        }
        catch (UnsupportedEncodingException e)
        {
            //e.printStackTrace();
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,0);
        IntentFilter[] intentFilter = new IntentFilter[] {};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);

        super.onResume();
    }

    @Override
    protected void onPause()
    {

        nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    //******************END NFC

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //****************** Button Methods
    public void gotoPayment(View view) {
        final String MY_PREFS_NAME = "MyPrefsFile"; //This should probably be changed to a global variable somewhere.
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            //Ideally prompt the user to log in here.
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
                //myText.setText("Response: " + response.toString());
                int checkOut = 0;
                try {

                    checkOut = 1;

                } catch (Exception e) {
                    e.printStackTrace();
                    checkOut = 0;


                }
                if(checkOut == 1)
                {
                    Intent i = new Intent(getApplicationContext(), MakePayment.class);
                    i.putExtra("ParkingTime", ParkingTime);
                    startActivity(i);
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




    }
    public void gotoAccount(View view)
    {
        Intent intent = new Intent(this, Account.class);
        startActivity(intent);
    }
    public void gotoAvailibility(View view)
    {
        Intent intent = new Intent(this, Availibility.class);
        startActivity(intent);
    }
    public void gotoReport(View view)
    {
        Intent intent = new Intent(this, reportParker.class);
        startActivity(intent);
    }

}

/*******************************The request of the database

 JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
@Override
public void onResponse(JSONObject response) {
//myText.setText("Response: " + response.toString());
try {
trial = "Response: " + response.toString()+"\n"+response.get("reserve_time");
} catch (JSONException e) {
e.printStackTrace();
}
//*try{
trial = "Response: " + response.toString()+"\n"+response.get("reserve_time");
// myText.setText("Response: " + response.toString()+"\n"+response.get("reserve_time"));
}catch(JSONException e){
e.printStackTrace();
}*
}
}, new Response.ErrorListener() {

@Override
public void onErrorResponse(VolleyError error) {
// TODO Auto-generated method stub

}
});
 Networking.getInstance(this).addToRequestQueue(jsObjRequest);

 Toast.makeText(this, trial, Toast.LENGTH_LONG).show();







 nfcAdapter = NfcAdapter.getDefaultAdapter(this);
 if(nfcAdapter != null && nfcAdapter.isEnabled())
 {
 // Toast.makeText(this, "Yes", Toast.LENGTH_LONG).show();
 }
 else
 {
 Toast.makeText(this, "No", Toast.LENGTH_LONG).show();
 finish();
 }
 // spot = (TextView) findViewById(R.id.Spot_Id);
 // device_Identifier = num.get_dev_id(context);

 Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC-5:00"));
 //Date currentLocalTime = calendar.getTime();
 //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
 //String localTime = date.format(currentLocalTime);



 *******************************
 import com.android.volley.NetworkResponse;
 import com.android.volley.ParseError;
 import com.android.volley.Response;
 import com.android.volley.Response.ErrorListener;
 import com.android.volley.Response.Listener;
 ********************************
 import java.text.DateFormat;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.TimeZone;
 import 	java.nio.charset.Charset;
 import android.provider.Settings;
 import android.provider.Settings.System;
 import android.provider.Settings;
 import android.support.design.widget.FloatingActionButton;
 import android.support.design.widget.Snackbar;
 import com.example.rommo_000.smartpark.Networking;
 import android.provider.Settings;
 import android.provider.Settings.System;
 import android.app.VoiceInteractor;
 import android.content.Context;
 import android.widget.ImageView;
 import android.app.DownloadManager;
 *****************************************************************************/

