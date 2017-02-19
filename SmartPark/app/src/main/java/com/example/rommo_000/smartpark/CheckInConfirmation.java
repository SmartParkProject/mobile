package com.example.rommo_000.smartpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheckInConfirmation extends AppCompatActivity {
    TextView spotConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_confirmation);


        String recieved = getIntent().getExtras().getString("parkingSpot");
        spotConfirmation = (TextView) findViewById(R.id.Confirm);
        spotConfirmation.setText("Check Into Spot Number " + recieved + " Confirmed!");
    }

    public void returnToMain(View view)
    {
        Intent intent = new Intent(this, SScreen.class);
        startActivity(intent);
    }
}
