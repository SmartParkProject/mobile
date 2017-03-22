package com.example.rommo_000.dungeondivers;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rommo_000.dungeondivers.charCreation;

public class MainActivity extends AppCompatActivity {
    ImageView charPicture;
    charCreation newChar;
    TextView charInfo;
    EditText nameView;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newChar = new charCreation(0,0,0);
        nameView = (EditText) findViewById(R.id.nameInput);

        charInfo = (TextView) findViewById(R.id.charType);
        charInfo.setText(newChar.getCharInfo());
        //playing the music
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.summer_sunday);
      //  mediaPlayer.start();
      //setting up the animated characters
        charPicture = (ImageView) findViewById(R.id.characterImage);

        charPicture.setBackgroundResource(R.drawable.whm);
        AnimationDrawable frameAnimation = (AnimationDrawable) charPicture.getBackground();
        frameAnimation.start();
    }


    public void cycleRaceFWD(View view)
    {
        if(newChar.raceType == 2)
        {
            newChar.raceType = 0;
        }
        else
        {
            newChar.raceType++;
        }
        newChar.setRace(newChar.raceType);
        charInfo.setText(newChar.getCharInfo());
        String img = newChar.returnPicInfo();

        charPicture.setBackgroundResource(getResources().getIdentifier(img, "drawable", getPackageName()));
        AnimationDrawable frameAnimation = (AnimationDrawable) charPicture.getBackground();
        frameAnimation.start();
    }
    public void cycleRaceBCK(View view)
    {
        if(newChar.raceType == 0)
        {
            newChar.raceType = 2;
        }
        else
        {
            newChar.raceType--;
        }
        newChar.setRace(newChar.raceType);
        charInfo.setText(newChar.getCharInfo());
        String img = newChar.returnPicInfo();

        charPicture.setBackgroundResource(getResources().getIdentifier(img, "drawable", getPackageName()));
        AnimationDrawable frameAnimation = (AnimationDrawable) charPicture.getBackground();
        frameAnimation.start();
    }
    public void changeGender(View view)
    {
        if(newChar.genderType == 0)
        {
            newChar.genderType = 1;
        }
        else
        {
            newChar.genderType = 0;
        }
        newChar.setGender();
        charInfo.setText(newChar.getCharInfo());
        String img = newChar.returnPicInfo();
        charPicture.setBackgroundResource(getResources().getIdentifier(img, "drawable", getPackageName()));
        AnimationDrawable frameAnimation = (AnimationDrawable) charPicture.getBackground();
        frameAnimation.start();
    }
    public void cycleClassFWD(View view)
    {
        if(newChar.classType == 2)
        {
            newChar.classType = 0;
        }
        else
        {
            newChar.classType++;
        }
        newChar.setClass();
        charInfo.setText(newChar.getCharInfo());
        String img = newChar.returnPicInfo();
        charPicture.setBackgroundResource(getResources().getIdentifier(img, "drawable", getPackageName()));
        AnimationDrawable frameAnimation = (AnimationDrawable) charPicture.getBackground();
        frameAnimation.start();
    }
    public void cycleClassBCK(View view)
    {
        if(newChar.classType == 0)
        {
            newChar.classType = 2;
        }
        else
        {
            newChar.classType--;
        }
        newChar.setClass();
        charInfo.setText(newChar.getCharInfo());
        String img = newChar.returnPicInfo();
        charPicture.setBackgroundResource(getResources().getIdentifier(img, "drawable", getPackageName()));
        AnimationDrawable frameAnimation = (AnimationDrawable) charPicture.getBackground();
        frameAnimation.start();
    }

    public void goToCreate(View view)
    {
        Intent intent = new Intent(this, statPointAllocation.class);
        String nameEntered = nameView.getText().toString();
        if(nameEntered.matches(""))
        {
            nameEntered = "Thrakazog";
        }
        intent.putExtra("name", nameEntered);
        intent.putExtra("class", newChar.classType);
        intent.putExtra("race", newChar.raceType);
        intent.putExtra("gender", newChar.genderType);
        startActivity(intent);
    }
}
