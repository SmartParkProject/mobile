package com.example.rommo_000.smartpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {

    TextView res;

//  Values to get
    Boolean resMade = true;
    String yRes = "Spot 5 Reserved: 5:00pm 10/11/16";
    String nRes = "Would you like to make a reservation?";
    EditText sw;
    EditText dw;
    EditText tw;
    Button resButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        res = (TextView) findViewById(R.id.ResDeets);
        sw = (EditText) findViewById(R.id.SpotWanted);
        dw = (EditText) findViewById(R.id.DateWanted);
        tw = (EditText) findViewById(R.id.TimeWanted);
        resButton = (Button) findViewById(R.id.mkResButton);




        if(resMade)
        {
            res.setText(yRes);
            sw.setVisibility(View.GONE);
            dw.setVisibility(View.GONE);
            tw.setVisibility(View.GONE);
            resButton.setText("Return Home");
            //resButton.setVisibility(View.INVISIBLE);
            resButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), SScreen.class);
                    startActivity(i);
                }
            });
        }
        else
        {
            res.setText(nRes);
            resButton.setText("Make Reservation");
            resButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), ReservationConfirmation.class);
                    startActivity(i);
                }
            });
        }



    }
}
