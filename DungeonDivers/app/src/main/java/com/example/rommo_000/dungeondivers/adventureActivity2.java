package com.example.rommo_000.dungeondivers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

public class adventureActivity2 extends AppCompatActivity{

    ArrayList<String> playerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure2);

        Intent i = getIntent();
        playerInfo = (ArrayList<String>) getIntent().getSerializableExtra("playerInfo");

    }


}
