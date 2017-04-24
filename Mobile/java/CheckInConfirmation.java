package com.example.rommo_000.smartpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CheckInConfirmation extends AppCompatActivity {
    TextView spotConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_confirmation);


        int recieved = getIntent().getExtras().getInt("parkingSpot");
        int lotId = getIntent().getExtras().getInt("LotID");
        String tmp;
        switch (lotId)
        {
            case(0): tmp = "lot 0"; break;
            case(1): tmp = "lot 1"; break;
            case(2): tmp = "lot 2"; break;
            case(3): tmp = "lot 3"; break;
            case(4): tmp = "lot 4"; break;
            case(5): tmp = "lot 5"; break;
            default: tmp = "NULL"; break;
        }
        spotConfirmation = (TextView) findViewById(R.id.Confirm);
        spotConfirmation.setText("Check Into Spot Number " + recieved + " Confirmed!" + "\n" + tmp);
        spotConfirmation.setGravity(Gravity.CENTER);
    }

    public void returnToMain(View view)
    {
        Intent intent = new Intent(this, SScreen.class);
        startActivity(intent);
    }
}
