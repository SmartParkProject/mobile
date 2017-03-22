package com.example.rommo_000.dungeondivers;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class CharacterCreationScreen extends AppCompatActivity {

    ImageView charAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation_screen);
        charAnimation = (ImageView)findViewById(R.id.charAnim);
        charAnimation.setBackgroundResource(R.drawable.whm);
        AnimationDrawable frameAnimation = (AnimationDrawable) charAnimation.getBackground();
        frameAnimation.start();
    }
}
