package com.example.rommo_000.smartpark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.rommo_000.smartpark.Networking;
import com.android.volley.*;

import org.json.JSONObject;
import org.json.*;

public class Availibility extends AppCompatActivity {

    TextView openSpots;
    String numOpen;
    Networking obj;
    String url = obj.urlMain + "parking/available/";
    String trial;
    Boolean success;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availibility);
        openSpots = (TextView) findViewById(R.id.SpotsAvail);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                //myText.setText("Response: " + response.toString());
                try {
                    numOpen = response.getString("count");
                    openSpots.setText("There are " + numOpen + " Open Spots");

                } catch (JSONException e) {
                    e.printStackTrace();


                }

        }
    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            // TODO Auto-generated method stub
            openSpots.setText("Error Communicating With Server");
        }
        });
    Networking.getInstance(this).addToRequestQueue(jsObjRequest);









    }



}
