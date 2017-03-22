package com.example.rommo_000.dungeondivers;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//import static com.example.rommo_000.dungeondivers.Player.saveData;

public class navigationScreen extends AppCompatActivity {

    ArrayList<String> playerInfo;
    Button createNewCharacter;
    Button deleteCurrentCharacter;
    Button adventureBtn;
    Player player;
    ImageView charView;
    TextView greetingView;
    TextView charInfoNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_screen);
        createNewCharacter = (Button) findViewById(R.id.createcharButton);
        deleteCurrentCharacter = (Button) findViewById(R.id.deleteCharacter);
        adventureBtn = (Button) findViewById(R.id.adventureButton);
        charView = (ImageView) findViewById(R.id.charNavigationView);
        greetingView = (TextView) findViewById(R.id.greeting);
        charInfoNavigationView = (TextView) findViewById(R.id.charInfoNavigation);


        playerInfo = (ArrayList<String>) getIntent().getSerializableExtra("charInfo");

        if(playerInfo.size() > 1)
        {
            createNewCharacter.setVisibility(View.INVISIBLE);
            player = new Player(playerInfo);
            charView.setBackgroundResource(getResources().getIdentifier(playerInfo.get(9), "drawable", getPackageName()));
            AnimationDrawable frameAnimation = (AnimationDrawable) charView.getBackground();
            frameAnimation.start();
            greetingView.setText("Welcome Back " + playerInfo.get(8) + " Begin Your Adventure");
            charInfoNavigationView.setText("Level " + playerInfo.get(7) + "\nExperience " + playerInfo.get(6)
                                            + "\n" + player.getClassRaceGender());

        }
        else
        {
            deleteCurrentCharacter.setVisibility(View.INVISIBLE);
            adventureBtn.setVisibility(View.INVISIBLE);
            player = new Player();
            charView.setBackgroundResource(R.drawable.campfire);
            AnimationDrawable frameAnimation = (AnimationDrawable) charView.getBackground();
            frameAnimation.start();
            greetingView.setText("Welcome Stranger, Make A New Character!");
            charInfoNavigationView.setText("This Campfire Is Awfully Lonely...Make A New Character!");
        }


    }
    public void goToCreation(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goToAdventureActivity(View view)
    {
        Intent intent = new Intent(this, adventureActivity2.class);
        intent.putExtra("playerInfo", playerInfo);
        startActivity(intent);
    }
    public void deletePlayer(View view)
    {
        player.saveData("");
        createNewCharacter.setVisibility(View.VISIBLE);
        deleteCurrentCharacter.setVisibility(View.INVISIBLE);
        adventureBtn.setVisibility(View.INVISIBLE);
        charView.setBackgroundResource(R.drawable.campfire);
        AnimationDrawable frameAnimation = (AnimationDrawable) charView.getBackground();
        frameAnimation.start();
        greetingView.setText("Welcome Stranger, Make A New Character!");
        charInfoNavigationView.setText("This Campfire Is Awfully Lonely...Make A New Character!");

    }

}
