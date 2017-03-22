package com.example.rommo_000.dungeondivers;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class loadingScreen extends AppCompatActivity {

    FileInputStream inputStream;

    Player player;
    ArrayList<String> copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        ImageView showLogo = (ImageView) findViewById(R.id.openingLogo);
        Animation DDlogo = AnimationUtils.loadAnimation(this, R.anim.dd_logo_anim);
        showLogo.startAnimation(DDlogo);
        player = new Player();
        copy = new ArrayList<>(player.loadData());


        Thread myThread = new Thread(){
            @Override
            public void run()
            {
                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), navigationScreen.class);
                    intent.putExtra("charInfo", copy);
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
