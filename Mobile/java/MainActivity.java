package com.example.rommo_000.smartpark;


import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


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
    int lotId;
    String spot;
    int spotNum;
    int lotFromChip;
    public static final int ACTIVITY_START_CAMERA_APP = 0;
    String ParkingSpot;
    String ParkingTime;
    int cameFrom;
    Button butt;
    ArrayList<Lot> lotInfo;
    String token;
    String url3 = obj.urlMain + "lot/1/available";
    String urlGetLotData = obj.urlMain + "lot/search";
    String numSpotsAvail;
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
            lotId = getIntent().getExtras().getInt("LotId");
            if(ParkingSpot != null)
            {
                String tmp;
                switch (lotId)
                {
                    case (0): tmp = "lot 0"; break;
                    case (1):  tmp = "lot 1"; break;
                    case (2): tmp = "lot 2"; break;
                    case (3): tmp = "lot 3"; break;
                    case (4): tmp = "lot 4"; break;
                    case (5): tmp = "lot 5"; break;
                    default: tmp = "NULL"; break;
                }
                myText.setText("You are checked into spot " + ParkingSpot + "\n"  + tmp);
                myText.setGravity(Gravity.CENTER);
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

        JSONObject data = new JSONObject();
        try {
            data.put("lat","43");
            data.put("lng", "-83");
            data.put("distance", 100);
        }catch(JSONException e){
            e.printStackTrace();
        }


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlGetLotData, data, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("result");
                    lotInfo = new ArrayList<Lot>();
                    for (int i=0; i<results.length(); i++) {
                        JSONObject item = results.getJSONObject(i);
                        Double lat = item.getDouble("lat");
                        Double lng = item.getDouble("lng");
                        String name = item.getString("name");
                        int numberAvailible = item.getInt("spots");
                        int lotId = item.getInt("id");
                        Log.e("Lot Id MAIN", "Lot ID MAIN " + lotId);
                        lotInfo.add(new Lot(lat, lng, name, 0 ,numberAvailible, lotId));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Error Getting Map Data", Toast.LENGTH_LONG).show();
            }
        });
        Networking.getInstance(this).addToRequestQueue(jsObjRequest);
       
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
//****************************posting via json
        final String MY_PREFS_NAME = "MyPrefsFile";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        token = prefs.getString("token", null);
        if (token == null) {
            return;
        }
//Data for the request.
        JSONObject data = new JSONObject();
        try {

            Log.e("spot", "JSON REQUEST spot --- " + spotNum);
            Log.e("lot",  "JSON REQUEST lot  --- " + lotFromChip);

            data.put("spot", spotNum);
            data.put("token", token);
            data.put("lot", lotFromChip);
        }catch(JSONException e){
            e.printStackTrace();
        }
//The actual request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                Intent i = new Intent(getApplicationContext(), CheckInConfirmation.class);
                i.putExtra("parkingSpot", spotNum);
                i.putExtra("LotID", lotFromChip);
                startActivity(i);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String content_type = error.networkResponse.headers.get("Content-Type");
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject jsonObject = new JSONObject(responseBody);
                } catch (JSONException e) {
                } catch (UnsupportedEncodingException e){

                }
            }
        });

        Networking.getInstance(this).addToRequestQueue(jsObjRequest);
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
            spot = tagContent;
            int tmp = Integer.parseInt(spot);
            spotNum = tmp % 100;
            lotFromChip = (tmp - spotNum) / 100;
            Log.e("spot","MAIN_ACTIVITY spotNum " + spotNum);
            Log.e("lot", "MAIN_ACTIVITY lotFromChip " + lotFromChip);

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //****************** Button Methods
    public void gotoPayment(View view) {
        final String MY_PREFS_NAME = "MyPrefsFile";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {

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
            }
        });
        Networking.getInstance(this).addToRequestQueue(jsObjRequest);
    }
    public void goToMap(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putParcelableArrayListExtra("lotInfo", lotInfo);

        startActivity(intent);
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

        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
        Toast.makeText(this, "Ensure Plate is Visible", Toast.LENGTH_LONG).show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK)
        {

            Bundle extras = data.getExtras();
            Bitmap photoCapturedImage = (Bitmap) extras.get("data");
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            photoCapturedImage.compress(Bitmap.CompressFormat.PNG, 0, bs);
            byte[] byteFormat = bs.toByteArray();
            String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
            Intent i = new Intent(getApplicationContext(), reportParker.class);
            i.putExtra("photoTaken", byteFormat);
            i.putExtra("imgString", imgString);
            startActivity(i);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}

