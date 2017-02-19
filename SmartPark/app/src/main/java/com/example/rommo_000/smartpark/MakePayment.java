package com.example.rommo_000.smartpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MakePayment extends AppCompatActivity {
    TextView timeTextView;
    String oldTime;
    String currentTime;
    Date currentTime1;
    int difference;
    long convertedTime;
    Button butt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        butt = (Button) findViewById(R.id.returnHomeButton);
        currentTime = sdf.format(c.getTime());


        oldTime = getIntent().getExtras().getString("ParkingTime");


        timeTextView = (TextView) findViewById(R.id.MakePayment);
        timeTextView.setText("You checked in at " + oldTime +".\nYou Checked out at " + currentTime);
        //timeTextView.setText(oldTime);
    }

    public void goBack(View view)
    {
        Intent intent = new Intent(this, SScreen.class);
        startActivity(intent);
    }
}


/************************************************
 // Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC-5:00"));
 // Date currentLocalTime = calendar.getTime();
 //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
 //c String localTime = currentLocalTime.format(currentLocalTime);
 // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
 //difference = oldTime.compareTo(currentTime);
 //currentTime1 = sdf.parse(currentTime);
 + "\nYou were paked for " + difference
 ***********************************************/